package cn.jzyunqi.ms.job.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.persistence.dao.tools.JF;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.job.common.constant.BatchHelper;
import cn.jzyunqi.ms.job.common.constant.JobMessageConstant;
import cn.jzyunqi.ms.job.common.dto.BatchDto;
import cn.jzyunqi.ms.job.common.dto.backend.BkBatchDto;
import cn.jzyunqi.ms.job.common.dto.backend.query.BkBatchQueryDto;
import cn.jzyunqi.ms.job.domain.Batch;
import cn.jzyunqi.ms.job.domain.dao.jpa.BatchDao;
import cn.jzyunqi.ms.job.domain.dao.jpa.querydsl.BatchQry;
import com.querydsl.jpa.JPQLQuery;
import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("batchService")
public class BatchServiceImpl implements BatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchServiceImpl.class);

    @Resource
    private BatchDao batchDao;

    @Resource
    private Scheduler scheduler;

    @Override
    public PageDto<BatchDto> queryByPageable(BkBatchQueryDto bkBatchQueryDto, Pageable pageable) {
        List<BatchDto> batchDtoList = new ArrayList<>();
        Page<Batch> batchDbs = batchDao.findAllJF(new JF() {
            @Override
            public <T> void prepareQry(JPQLQuery<T> qry, boolean notCountQry) {
                BatchQry.searchBatch(qry, notCountQry, bkBatchQueryDto);
            }
        }, pageable);

        for (Batch batch : batchDbs) {
            BatchDto batchDto = BeanUtilPlus.copyAs(batch, BatchDto.class);
            try {
                Trigger.TriggerState status = scheduler.getTriggerState(new TriggerKey(BatchHelper.getTriggerName(batch.getTaskClass()), batch.getTaskGroup()));
                batchDto.setStatus(status.toString());
            } catch (SchedulerException e) {
                LOGGER.warn("queryByPageable error", e);
                //无法获知状态
                batchDto.setStatus("无法获知状态");
            }
            batchDtoList.add(batchDto);
        }
        return new PageDto<>(batchDtoList, batchDbs.getTotalElements());
    }

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public void saveBatch(BkBatchDto bkBatchDto) throws BusinessException {
        if (batchDao.isBatchNameExist(bkBatchDto.getTaskName()) > 0L) {
            //任务已经存在
            throw new BusinessException(JobMessageConstant.ERROR_QUARTZ_EXIST);
        }

        //将页面数据转换成后台需要数据
        bkBatchDto.setCronExMonth(StringUtilPlus.join(bkBatchDto.getCronExMonthArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExDay(StringUtilPlus.join(bkBatchDto.getCronExDayArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExWeek(StringUtilPlus.join(bkBatchDto.getCronExWeekArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExHour(StringUtilPlus.join(bkBatchDto.getCronExHourArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExMinute(StringUtilPlus.join(bkBatchDto.getCronExMinuteArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExSecond(StringUtilPlus.join(bkBatchDto.getCronExSecondArray(), StringUtilPlus.COMMA));

        Batch batch = BeanUtilPlus.copyAs(bkBatchDto, Batch.class);
        batch.setTaskGroup(Scheduler.DEFAULT_GROUP);//暂时不处理组别
        if (!bkBatchDto.getSimpleTask()) {
            prepareCronExpression(batch);
        }
        batchDao.save(batch);

        runBatch(batch);
    }

    @Override
    public BkBatchDto getBatchById(Long batchId) {
        Batch batch = batchDao.findById(batchId).get();
        BkBatchDto batchDto = BeanUtilPlus.copyAs(batch, BkBatchDto.class);
        return batchDto;
    }

    @Override
    @Transactional
    public void updateBatch(BkBatchDto bkBatchDto) throws BusinessException {
        Batch batchDb = batchDao.findById(bkBatchDto.getId()).get();

        //将页面数据转换成后台需要数据
        bkBatchDto.setCronExMonth(StringUtilPlus.join(bkBatchDto.getCronExMonthArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExDay(StringUtilPlus.join(bkBatchDto.getCronExDayArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExWeek(StringUtilPlus.join(bkBatchDto.getCronExWeekArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExHour(StringUtilPlus.join(bkBatchDto.getCronExHourArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExMinute(StringUtilPlus.join(bkBatchDto.getCronExMinuteArray(), StringUtilPlus.COMMA));
        bkBatchDto.setCronExSecond(StringUtilPlus.join(bkBatchDto.getCronExSecondArray(), StringUtilPlus.COMMA));

        BeanUtilPlus.copyNotNullProperties(bkBatchDto, batchDb);
        batchDb.setTaskGroup(Scheduler.DEFAULT_GROUP);// 暂时不处理组别
        if (!bkBatchDto.getSimpleTask()) {
            prepareCronExpression(batchDb);
        }
        batchDao.save(batchDb);

        runBatch(batchDb);
    }

    @Override
    public void deleteBatch(Long batchId) throws BusinessException {
        Batch batch = batchDao.findById(batchId).get();
        try {
            scheduler.deleteJob(new JobKey(BatchHelper.getJobDetailName(batch.getTaskClass()), batch.getTaskGroup()));
        } catch (SchedulerException e) {
            LOGGER.warn("deleteBatch error", e);
            //任务删除失败
            throw new BusinessException(JobMessageConstant.ERROR_QUARTZ_DELETE_FAILED);
        }
        batchDao.deleteById(batchId);
    }

    @Override
    public void invokeBatch(Long batchId) throws BusinessException {
        Batch batch = batchDao.findById(batchId).get();
        try {
            scheduler.triggerJob(new JobKey(BatchHelper.getJobDetailName(batch.getTaskClass()), batch.getTaskGroup()));
        } catch (SchedulerException e) {
            LOGGER.warn("invokeBatch error", e);
            //任务调用失败
            throw new BusinessException(JobMessageConstant.ERROR_QUARTZ_INVOKE_FAILED);
        }
    }

    @Override
    public void pauseBatch(Long batchId) throws BusinessException {
        Batch batch = batchDao.findById(batchId).get();
        try {
            scheduler.pauseJob(new JobKey(BatchHelper.getJobDetailName(batch.getTaskClass()), batch.getTaskGroup()));
        } catch (SchedulerException e) {
            LOGGER.warn("pauseBatch error", e);
            //任务暂停失败
            throw new BusinessException(JobMessageConstant.ERROR_QUARTZ_PAUSE_FAILED);
        }
    }

    @Override
    public void resumeBatch(Long batchId) throws BusinessException {
        Batch batch = batchDao.findById(batchId).get();
        try {
            scheduler.resumeJob(new JobKey(BatchHelper.getJobDetailName(batch.getTaskClass()), batch.getTaskGroup()));
        } catch (SchedulerException e) {
            LOGGER.warn("resumeBatch error", e);
            //任务重新启动失败
            throw new BusinessException(JobMessageConstant.ERROR_QUARTZ_RESTART_FAILED);
        }
    }

    private void runBatch(Batch batchDb) throws BusinessException {
        Trigger trigger;
        if (batchDb.getSimpleTask()) {
            trigger = BatchHelper.getSimpleTrigger(batchDb);
        } else {
            trigger = BatchHelper.getCronTrigger(batchDb);
        }
        JobDetail jobDetail = (JobDetail) trigger.getJobDataMap().remove("jobDetail");
        try {
            scheduler.deleteJob(jobDetail.getKey());
            scheduler.addJob(jobDetail, true);
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            LOGGER.warn("runBatch error", e);
            //任务运行失败
            throw new BusinessException(JobMessageConstant.ERROR_QUARTZ_RUN_FAILED);
        }
    }

    private void prepareCronExpression(Batch batch) throws BusinessException {
        StringBuilder sb = new StringBuilder();
        switch (batch.getSecondType()) {
            case A:
                sb.append("*");
                break;
            case T:
                sb.append(batch.getCronExSecond());
                break;
            default:
                break;
        }
        sb.append(" ");
        switch (batch.getMinuteType()) {
            case A:
                sb.append("*");
                break;
            case T:
                sb.append(batch.getCronExMinute());
                break;
            default:
                break;
        }
        sb.append(" ");
        switch (batch.getHourType()) {
            case A:
                sb.append("*");
                break;
            case T:
                sb.append(batch.getCronExHour());
                break;
            default:
                break;
        }
        sb.append(" ");
        boolean dayOfMonth = true;
        switch (batch.getDayType()) {
            case A:
                sb.append("*");
                break;
            case W:
                sb.append("W");
                break;
            case O:
                sb.append(batch.getCronExDay());
                break;
            case L:
                sb.append("L");
                break;
            case B:
                sb.append("L-");
                sb.append(batch.getLastDayOffset());
                break;
            case N:
                sb.append(batch.getNearestDay());
                sb.append("W");
                break;
            default:
                dayOfMonth = false;
                sb.append("?");
                break;
        }
        sb.append(" ");
        switch (batch.getMonthType()) {
            case A:
                sb.append("*");
                break;
            case I:
                sb.append(batch.getCronExMonth());
                break;
            default:
                break;
        }
        sb.append(" ");
        if (dayOfMonth) {
            sb.append("?");
        } else {
            switch (batch.getDayType()) {
                case CW:
                    sb.append(batch.getCronExWeek());
                    break;
                case LW:
                    sb.append(batch.getLastWeek());
                    sb.append("L");
                    break;
                case DW:
                    sb.append(batch.getWeek());
                    sb.append("#");
                    sb.append(batch.getWeekNo());
                    break;
                default:
                    break;
            }
        }
        sb.append(" ");
        switch (batch.getYearType()) {
            case A:
                sb.append("*");
                break;
            case E:
                sb.append("/");
                sb.append(batch.getRepeatIntervalYear());
                break;
            case I:
                sb.append(batch.getCronExYear());
                break;
            default:
                break;
        }
        String cronExpression = sb.toString();
        if (CronExpression.isValidExpression(cronExpression)) {
            batch.setCronExpression(cronExpression);
        } else {
            throw new BusinessException(JobMessageConstant.ERROR_QUARTZ_CORN_PARSER_FAILED);
        }
    }
}

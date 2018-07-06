package cn.jzyunqi.ms.job.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.ms.job.common.dto.backend.BkBatchDto;
import cn.jzyunqi.ms.job.common.dto.backend.query.BkBatchQueryDto;
import cn.jzyunqi.ms.job.service.BatchService;
import cn.jzyunqi.ms.job.web.validator.BkBatchDtoValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author wiiyaya
 * @date 2018/5/15.
 */
@RestController
@Validated
public class BatchController extends AdminRestBaseController {

    @Resource
    private BatchService batchService;

    /**
     * 查询任务列表
     *
     * @return 任务信息
     */
    @RequestMapping(value = "/batch/page")
    public RestResultDto batchList(BkBatchQueryDto bkBatchQueryDto, Pageable pageable) {
        return RestResultDto.success(batchService.queryByPageable(bkBatchQueryDto, pageable));
    }

    /**
     * 新增任务
     *
     * @param bkBatchDto    任务信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/batch/add")
    public RestResultDto batchAdd(@Validated(BkBatchDtoValidator.Add.class) BkBatchDtoValidator bkBatchDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkBatchDto, BkBatchDtoValidator.Add.class);
        batchService.saveBatch(BeanUtilPlus.copyAs(bkBatchDto, BkBatchDto.class));

        return RestResultDto.success();
    }

    /**
     * 修改任务初始化
     *
     * @param id 任务id
     * @return 任务信息
     */
    @RequestMapping(value = "/batch/editInit")
    public RestResultDto batchEditInit(@RequestParam @NotNull Long id) throws BusinessException {
        BkBatchDto bkBatchDto = batchService.getBatchById(id);

        //将后台数据转换成页面需要数据
        //bkBatchDto.setCronExMonthArray(StringUtilPlus.split(bkBatchDto.getCronExMonth(), StringUtilPlus.COMMA));
        //bkBatchDto.setCronExDayArray(StringUtilPlus.split(bkBatchDto.getCronExDay(), StringUtilPlus.COMMA));
        //bkBatchDto.setCronExWeekArray(StringUtilPlus.split(bkBatchDto.getCronExWeek(), StringUtilPlus.COMMA));
        //bkBatchDto.setCronExHourArray(StringUtilPlus.split(bkBatchDto.getCronExHour(), StringUtilPlus.COMMA));
        //bkBatchDto.setCronExMinuteArray(StringUtilPlus.split(bkBatchDto.getCronExMinute(), StringUtilPlus.COMMA));
        //bkBatchDto.setCronExSecondArray(StringUtilPlus.split(bkBatchDto.getCronExSecond(), StringUtilPlus.COMMA));

        return RestResultDto.success(bkBatchDto);
    }

    /**
     * 修改任务
     *
     * @param bkBatchDto    任务信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/batch/edit")
    public RestResultDto batchEdit(@Validated(BkBatchDtoValidator.Edit.class) BkBatchDtoValidator bkBatchDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkBatchDto, BkBatchDtoValidator.Add.class);
        batchService.updateBatch(BeanUtilPlus.copyAs(bkBatchDto, BkBatchDto.class));

        return RestResultDto.success();
    }

    /**
     * 删除任务
     *
     * @param id 参数id
     * @return 参数信息
     */
    @RequestMapping(value = "/batch/delete")
    public RestResultDto batchDelete(@RequestParam @NotNull Long id) throws BusinessException {
        batchService.deleteBatch(id);

        return RestResultDto.success();
    }

    /**
     * 调用任务
     *
     * @param id 参数id
     * @return 参数信息
     */
    @RequestMapping(value = "/batch/invoke")
    public RestResultDto batchInvoke(@RequestParam @NotNull Long id) throws BusinessException {
        batchService.invokeBatch(id);

        return RestResultDto.success();
    }

    /**
     * 暂停任务
     *
     * @param id 参数id
     * @return 参数信息
     */
    @RequestMapping(value = "/batch/pause")
    public RestResultDto batchPause(@RequestParam @NotNull Long id) throws BusinessException {
        batchService.pauseBatch(id);

        return RestResultDto.success();
    }

    /**
     * 恢复任务
     *
     * @param id 参数id
     * @return 参数信息
     */
    @RequestMapping(value = "/batch/resume")
    public RestResultDto batchResume(@RequestParam @NotNull Long id) throws BusinessException {
        batchService.resumeBatch(id);

        return RestResultDto.success();
    }

}

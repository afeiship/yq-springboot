package cn.jzyunqi.ms.job.service;

import cn.jzyunqi.ms.job.common.constant.BatchHelper;
import cn.jzyunqi.ms.job.domain.Batch;
import cn.jzyunqi.ms.job.domain.dao.jpa.BatchDao;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("batchLoaderService")
public class BatchLoaderServiceImpl implements BatchLoaderService {

    @Resource
    private BatchDao batchDao;

    @Override
    public Trigger[] loadAllScheduleTriggers() {

        List<Batch> scheduledTaskList = batchDao.findAll();
        List<Trigger> triggers = new ArrayList<>();

        for (Batch task : scheduledTaskList) {
            if (task.getSimpleTask()) {
                triggers.add(BatchHelper.getSimpleTrigger(task));
            } else {
                triggers.add(BatchHelper.getCronTrigger(task));
            }
        }
        return triggers.toArray(new Trigger[triggers.size()]);
    }
}

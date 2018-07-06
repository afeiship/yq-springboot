package cn.jzyunqi.ms.job.service;

import org.quartz.Trigger;

/**
 * <p>类简述：</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public interface BatchLoaderService {
    /**
     * 获取所有的计划任务
     *
     * @return 计划任务触发器组
     */
    Trigger[] loadAllScheduleTriggers();
}

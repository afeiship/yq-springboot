package cn.jzyunqi.ms.job.common.constant;

import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.job.domain.Batch;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class BatchHelper {

    /**
     * 组装简单任务
     *
     * @param task 任务信息
     * @return 任务对象
     */
    public static SimpleTriggerImpl getSimpleTrigger(Batch task) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setBeanName(getTriggerName(task.getTaskClass()));
        simpleTriggerFactoryBean.setGroup(task.getTaskGroup());
        simpleTriggerFactoryBean.setJobDetail(getJobDetail(task));
        simpleTriggerFactoryBean.setMisfireInstruction(task.getMisfireType().getInstruction());
        simpleTriggerFactoryBean.afterPropertiesSet();

        SimpleTriggerImpl simpleTriggerImpl = (SimpleTriggerImpl) simpleTriggerFactoryBean.getObject();
        if (simpleTriggerImpl != null) {
            if (task.getStartDate() != null) {
                simpleTriggerImpl.setStartTime(toDate(task.getStartDate()));
            }
            if (task.getEndDate() != null) {
                simpleTriggerImpl.setEndTime(toDate(task.getEndDate()));
            }
            simpleTriggerImpl.setRepeatInterval(task.getRepeatInterval());
            simpleTriggerImpl.setRepeatCount(task.getRepeatCount());

            if (task.getHolidayRest()) {
                //暂不处理
            }
            if (task.getWorkingDay()) {
                //暂不处理
            }
        }
        return simpleTriggerImpl;
    }

    /**
     * 组装复杂任务
     *
     * @param task 任务信息
     * @return 任务对象
     */
    public static CronTriggerImpl getCronTrigger(Batch task) {
        try {
            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            cronTriggerFactoryBean.setBeanName(getTriggerName(task.getTaskClass()));
            cronTriggerFactoryBean.setJobDetail(getJobDetail(task));
            cronTriggerFactoryBean.setMisfireInstruction(task.getMisfireType().getInstruction());
            cronTriggerFactoryBean.setCronExpression(task.getCronExpression());
            cronTriggerFactoryBean.afterPropertiesSet();
            CronTriggerImpl cronTriggerImpl = (CronTriggerImpl) cronTriggerFactoryBean.getObject();
            if (cronTriggerImpl != null) {
                if (task.getStartDate() != null) {
                    cronTriggerImpl.setStartTime(toDate(task.getStartDate()));
                }
                if (task.getEndDate() != null) {
                    cronTriggerImpl.setEndTime(toDate(task.getEndDate()));
                }
                if (task.getHolidayRest()) {
                    //暂不处理
                }
            }
            return cronTriggerImpl;
        } catch (ParseException e) {
            return null;
        }
    }

    private static JobDetail getJobDetail(Batch task) {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();

        jobDetailFactoryBean.setBeanName(getJobDetailName(task.getTaskClass()));

        try {
            jobDetailFactoryBean.setJobClass(jobDetailFactoryBean.getClass().getClassLoader().loadClass(task.getTaskClass()).asSubclass(Job.class));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        jobDetailFactoryBean.setDurability(Boolean.TRUE);
        jobDetailFactoryBean.setGroup(task.getTaskGroup());

        // Map<String, Integer> map = new HashMap<>();
        // map.put("timeout", new Integer(5));
        // jobDetailFactoryBean.setJobDataAsMap(map);

        jobDetailFactoryBean.afterPropertiesSet();

        return jobDetailFactoryBean.getObject();
    }


    public static String getJobDetailName(String taskClass) {
        return getClassSimpleName(taskClass) + "Detail";
    }

    public static String getTriggerName(String taskClass) {
        return getClassSimpleName(taskClass) + "Trigger";
    }

    private static String getClassSimpleName(String taskClass) {
        String[] classSplit = StringUtilPlus.split(taskClass, StringUtilPlus.DOT);
        return StringUtilPlus.uncapitalize(classSplit[classSplit.length - 1]);
    }

    private static Date toDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
}

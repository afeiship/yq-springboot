package cn.jzyunqi.ms.job.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import cn.jzyunqi.ms.job.common.enums.DayType;
import cn.jzyunqi.ms.job.common.enums.HourType;
import cn.jzyunqi.ms.job.common.enums.MinuteType;
import cn.jzyunqi.ms.job.common.enums.MisfireType;
import cn.jzyunqi.ms.job.common.enums.MonthType;
import cn.jzyunqi.ms.job.common.enums.SecondType;
import cn.jzyunqi.ms.job.common.enums.YearType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@Setter
public class BatchDto extends BaseDto<Long, Long> {
    private static final long serialVersionUID = 4543291132098892496L;

    /**
     * 名称
     */
    private String taskName;

    /**
     * 处理该任务的类
     */
    private String taskClass;

    /**
     * 组别
     */
    private String taskGroup;

    /**
     * 开始时间
     */
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 复杂任务表达式
     */
    private String cronExpression;

    /**
     * 丢失的次数重启策略
     */
    private MisfireType misfireType;

    /**
     * 假期是否停止运行，true是，false否
     */
    private Boolean holidayRest;

    /**
     * 是否简单计划任务，true是，false否
     */
    private Boolean simpleTask;

    /**
     * 当前状态
     */
    private String status;


    //以下是简单计划任务数据
    /**
     * 简单计划任务: 仅工作日运行，true是，false否，空不适用
     */
    private Boolean workingDay;

    /**
     * 简单计划任务: 重复次数，-1 为不限定次数, 0 为不重复执行
     */
    private Integer repeatCount;

    /**
     * 简单计划任务: 重复间隔，不允许小于0，单位毫秒
     */
    private Long repeatInterval;


    //以下是复杂计划任务数据
    /**
     * 年间隔标记，每年/每隔X年/指定XXXX年
     */
    private YearType yearType;

    /**
     * 每隔X年
     */
    private String repeatIntervalYear;

    /**
     * 指定XXXX年
     */
    private String cronExYear;

    /**
     * 月间隔标记，每个月/指定月份
     */
    private MonthType monthType;

    /**
     * 指定月份1-12
     */
    private String cronExMonth;

    /**
     * 日间隔标记，每天/最后一天/选择指定星期/每个工作日/最后一天前的第X天/最后一个星期X/选择指定日期/离X号最近的工作日/第X周的星期Y
     */
    private DayType dayType;

    /**
     * 选择指定日期1-31
     */
    private String cronExDay;

    /**
     * 最后一天前的第X天
     */
    private String lastDayOffset;

    /**
     * 离X号最近的工作日
     */
    private String nearestDay;

    /**
     * 选择指定星期1-7
     */
    private String cronExWeek;

    /**
     * 最后一个星期X
     */
    private String lastWeek;

    /**
     * 第X周的星期Y
     */
    private String weekNo;

    /**
     * 第X周的星期Y
     */
    private String week;

    /**
     * 时间隔，每小时/选择指定小时
     */
    private HourType hourType;

    /**
     * 选择指定小时0-23
     */
    private String cronExHour;

    /**
     * 分间隔，每分钟/选择指定分钟
     */
    private MinuteType minuteType;

    /**
     * 选择指定分钟0-59
     */
    private String cronExMinute;

    /**
     * 秒间隔，每秒钟/选择指定秒
     */
    private SecondType secondType;

    /**
     * 选择指定秒0-59
     */
    private String cronExSecond;
}

package cn.jzyunqi.ms.job.domain;

import cn.jzyunqi.common.persistence.domain.BaseDomain;
import cn.jzyunqi.ms.job.common.enums.DayType;
import cn.jzyunqi.ms.job.common.enums.HourType;
import cn.jzyunqi.ms.job.common.enums.MinuteType;
import cn.jzyunqi.ms.job.common.enums.MisfireType;
import cn.jzyunqi.ms.job.common.enums.MonthType;
import cn.jzyunqi.ms.job.common.enums.SecondType;
import cn.jzyunqi.ms.job.common.enums.YearType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Entity
@Table(name = "qrtz_batch")
public class Batch extends BaseDomain<Long, Long> {
    private static final long serialVersionUID = -6408215358952401783L;

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

    @Column
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Column
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Column
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Column
    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    @Column
    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    @Column
    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    @Column
    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    @Column
    public Boolean getHolidayRest() {
        return holidayRest;
    }

    public void setHolidayRest(Boolean holidayRest) {
        this.holidayRest = holidayRest;
    }

    @Column
    public Boolean getSimpleTask() {
        return simpleTask;
    }

    public void setSimpleTask(Boolean simpleTask) {
        this.simpleTask = simpleTask;
    }

    @Column
    public Boolean getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(Boolean workingDay) {
        this.workingDay = workingDay;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public YearType getYearType() {
        return yearType;
    }

    public void setYearType(YearType yearType) {
        this.yearType = yearType;
    }

    @Column
    public String getRepeatIntervalYear() {
        return repeatIntervalYear;
    }

    public void setRepeatIntervalYear(String repeatIntervalYear) {
        this.repeatIntervalYear = repeatIntervalYear;
    }

    @Column
    public String getCronExYear() {
        return cronExYear;
    }

    public void setCronExYear(String cronExYear) {
        this.cronExYear = cronExYear;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public MonthType getMonthType() {
        return monthType;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    @Column
    public String getCronExMonth() {
        return cronExMonth;
    }

    public void setCronExMonth(String cronExMonth) {
        this.cronExMonth = cronExMonth;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    @Column
    public String getCronExDay() {
        return cronExDay;
    }

    public void setCronExDay(String cronExDay) {
        this.cronExDay = cronExDay;
    }

    @Column
    public String getLastDayOffset() {
        return lastDayOffset;
    }

    public void setLastDayOffset(String lastDayOffset) {
        this.lastDayOffset = lastDayOffset;
    }

    @Column
    public String getNearestDay() {
        return nearestDay;
    }

    public void setNearestDay(String nearestDay) {
        this.nearestDay = nearestDay;
    }

    @Column
    public String getCronExWeek() {
        return cronExWeek;
    }

    public void setCronExWeek(String cronExWeek) {
        this.cronExWeek = cronExWeek;
    }

    @Column
    public String getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(String lastWeek) {
        this.lastWeek = lastWeek;
    }

    @Column
    public String getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(String weekNo) {
        this.weekNo = weekNo;
    }

    @Column
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public HourType getHourType() {
        return hourType;
    }

    public void setHourType(HourType hourType) {
        this.hourType = hourType;
    }

    @Column
    public String getCronExHour() {
        return cronExHour;
    }

    public void setCronExHour(String cronExHour) {
        this.cronExHour = cronExHour;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public MinuteType getMinuteType() {
        return minuteType;
    }

    public void setMinuteType(MinuteType minuteType) {
        this.minuteType = minuteType;
    }

    @Column
    public String getCronExMinute() {
        return cronExMinute;
    }

    public void setCronExMinute(String cronExMinute) {
        this.cronExMinute = cronExMinute;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public SecondType getSecondType() {
        return secondType;
    }

    public void setSecondType(SecondType secondType) {
        this.secondType = secondType;
    }

    @Column
    public String getCronExSecond() {
        return cronExSecond;
    }

    public void setCronExSecond(String cronExSecond) {
        this.cronExSecond = cronExSecond;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public MisfireType getMisfireType() {
        return misfireType;
    }

    public void setMisfireType(MisfireType misfireType) {
        this.misfireType = misfireType;
    }
}

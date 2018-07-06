package cn.jzyunqi.ms.job.common.enums;

/**
 * <p>类简述：任务失败后重启策略</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public enum MisfireType {
    /**
     * 复杂：MISFIRE_INSTRUCTION_FIRE_ONCE_NOW
     * 立即执行一次，然后按照设定的周期运行
     * 简单：MISFIRE_INSTRUCTION_FIRE_NOW
     * 立即执行一次，仅仅用于触发一次的简单任务上，也就是重复次数为0的任务，如果重复次数大于0则等同于C
     */
    A(1, "策略A"),

    /**
     * 复杂：MISFIRE_INSTRUCTION_DO_NOTHING
     * 简单：MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT
     * 立即执行一次，并以当前时间开始执行全部次数
     */
    B(2, "策略B"),
    /**
     * 复杂：不适用
     * 简单：MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
     * 立即执行一次，并以当前时间开始执行剩余的次数
     */
    C(3, "策略C"),

    /**
     * 复杂：不适用
     * 简单：MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
     * 不执行，按照以前的计划执行
     */
    D(4, "策略D"),

    /**
     * 复杂：不适用
     * 简单：MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT
     * 不执行，按照当前时间执行重新计算剩余次数
     */
    E(5, "策略E"),;

    /**
     * 策略
     */
    private int instruction;

    /**
     * 描述
     */
    private String desc;

    MisfireType(int instruction, String desc) {
        this.instruction = instruction;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getInstruction() {
        return instruction;
    }
}

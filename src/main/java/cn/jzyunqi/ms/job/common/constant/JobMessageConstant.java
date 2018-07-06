package cn.jzyunqi.ms.job.common.constant;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class JobMessageConstant {

    /**
     * 任务已经存在
     */
    public static final String ERROR_QUARTZ_EXIST = "job_error_quartz_exist";

    /**
     * 任务删除失败
     */
    public static final String ERROR_QUARTZ_DELETE_FAILED = "job_error_quartz_delete_failed";

    /**
     * 任务调用失败
     */
    public static final String ERROR_QUARTZ_INVOKE_FAILED = "job_error_quartz_invoke_failed";

    /**
     * 任务暂停失败
     */
    public static final String ERROR_QUARTZ_PAUSE_FAILED = "job_error_quartz_pause_failed";

    /**
     * 任务重新启动失败
     */
    public static final String ERROR_QUARTZ_RESTART_FAILED = "job_error_quartz_restart_failed";

    /**
     * 任务运行失败
     */
    public static final String ERROR_QUARTZ_RUN_FAILED = "job_error_quartz_run_failed";

    /**
     * 任务周期解析错误
     */
    public static final String ERROR_QUARTZ_CORN_PARSER_FAILED = "job_error_quartz_corn_parser_failed";
}

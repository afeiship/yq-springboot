package cn.jzyunqi.ms.job.common.dto.backend;

import cn.jzyunqi.ms.job.common.dto.BatchDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@Setter
public class BkBatchDto extends BatchDto {
    private static final long serialVersionUID = -4433858288914410446L;

    private String[] cronExMinuteArray;

    private String[] cronExSecondArray;

    private String[] cronExHourArray;

    private String[] cronExWeekArray;

    private String[] cronExDayArray;

    private String[] cronExMonthArray;
}

package cn.jzyunqi.ms.job.web.validator;

import cn.jzyunqi.common.model.ValidatorDto;
import cn.jzyunqi.common.utils.DateTimeUtilPlus;
import cn.jzyunqi.ms.job.common.dto.backend.BkBatchDto;
import cn.jzyunqi.ms.job.common.enums.MisfireType;
import cn.jzyunqi.ms.job.common.enums.YearType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2018/5/4.
 */
public class BkBatchDtoValidator extends BkBatchDto implements ValidatorDto {
    private static final long serialVersionUID = 1300914714676013548L;

    public interface Edit {
    }

    public interface Add {
    }

    @NotEmpty(groups = {Add.class, Edit.class})
    @Override
    public String getTaskName() {
        return super.getTaskName();
    }

    @NotEmpty(groups = Add.class)
    @Override
    public String getTaskClass() {
        return super.getTaskClass();
    }

    @DateTimeFormat(pattern = DateTimeUtilPlus.SYSTEM_DATE_TIME_FORMAT)
    @Override
    public LocalDateTime getStartDate() {
        return super.getStartDate();
    }

    @DateTimeFormat(pattern = DateTimeUtilPlus.SYSTEM_DATE_TIME_FORMAT)
    @Override
    public LocalDateTime getEndDate() {
        return super.getEndDate();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public Boolean getSimpleTask() {
        return super.getSimpleTask();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public MisfireType getMisfireType() {
        return super.getMisfireType();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public Boolean getHolidayRest() {
        return super.getHolidayRest();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public Boolean getWorkingDay() {
        return super.getWorkingDay();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public YearType getYearType() {
        return super.getYearType();
    }

    @Override
    public void checkAndThrowErrors(Class checkType) {

    }
}

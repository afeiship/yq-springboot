package cn.jzyunqi.ms.system.web.validator;

import cn.jzyunqi.common.model.ValidatorDto;
import cn.jzyunqi.ms.system.common.dto.backend.BkParamDto;
import cn.jzyunqi.ms.system.common.enums.ParamType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wiiyaya
 * @date 2018/5/4.
 */
public class BkParamDtoValidator extends BkParamDto implements ValidatorDto {
    private static final long serialVersionUID = -7608069986105587799L;

    public interface Edit {
    }

    public interface Add {
    }

    @Override
    @NotNull(groups = {Edit.class})
    public Long getId() {
        return super.getId();
    }

    @Override
    @NotNull(groups = {Add.class})
    public ParamType getType() {
        return super.getType();
    }

    @Override
    @NotBlank(groups = {Add.class, Edit.class})
    public String getName() {
        return super.getName();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getCode() {
        return super.getCode();
    }

    @Override
    @NotBlank(groups = {Add.class, Edit.class})
    public String getValue() {
        return super.getValue();
    }

    @Override
    public void checkAndThrowErrors(Class checkType) {

    }
}

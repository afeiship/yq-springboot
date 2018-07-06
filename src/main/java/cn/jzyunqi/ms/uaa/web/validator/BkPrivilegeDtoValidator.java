package cn.jzyunqi.ms.uaa.web.validator;

import cn.jzyunqi.common.model.ValidatorDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkPrivilegeDto;

import javax.validation.constraints.NotBlank;

/**
 * @author wiiyaya
 * @date 2018/5/4.
 */
public class BkPrivilegeDtoValidator extends BkPrivilegeDto implements ValidatorDto {
    private static final long serialVersionUID = 8363579248745314026L;

    public interface Add {
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getName() {
        return super.getName();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getCode() {
        return super.getCode();
    }

    @Override
    public void checkAndThrowErrors(Class checkType) {

    }
}

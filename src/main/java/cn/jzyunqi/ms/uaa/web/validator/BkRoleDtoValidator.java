package cn.jzyunqi.ms.uaa.web.validator;

import cn.jzyunqi.common.model.ValidatorDto;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkRoleDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/4.
 */
public class BkRoleDtoValidator extends BkRoleDto implements ValidatorDto {
    private static final long serialVersionUID = -9055131347928558023L;

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
    @NotNull(groups = {Add.class, Edit.class})
    @Size(min = 1, groups = {Add.class, Edit.class})
    public List<Long> getPrivilegeIdList() {
        return super.getPrivilegeIdList();
    }

    @Override
    public void checkAndThrowErrors(Class checkType) {
        super.setPrivilegeIdList(CollectionUtilPlus.removeRepeat(super.getPrivilegeIdList()));
    }
}

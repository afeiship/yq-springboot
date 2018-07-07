package cn.jzyunqi.controller;

import cn.jzyunqi.common.model.ValidatorDto;
import cn.jzyunqi.common.utils.CollectionUtilPlus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/4.
 */
public class AdminUserDtoValidator extends AdminUserDto implements ValidatorDto {
    private static final long serialVersionUID = -4420833150884042221L;

    public interface EditProfile {
    }

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
    @NotBlank(groups = {Add.class})
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @NotBlank(groups = {Add.class, Edit.class, EditProfile.class})
    public String getNickname() {
        return super.getNickname();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    @Size(min = 1, groups = {Add.class, Edit.class})
    public List<Long> getRoleIdList() {
        return super.getRoleIdList();
    }

    @Override
    public void checkAndThrowErrors(Class checkType) {
        super.setRoleIdList(CollectionUtilPlus.removeRepeat(super.getRoleIdList()));
    }
}

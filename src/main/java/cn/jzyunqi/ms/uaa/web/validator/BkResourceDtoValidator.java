package cn.jzyunqi.ms.uaa.web.validator;

import cn.jzyunqi.common.model.ValidatorDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkResourceDto;
import cn.jzyunqi.ms.uaa.common.enums.PermitType;
import cn.jzyunqi.ms.uaa.common.enums.ResourceType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wiiyaya
 * @date 2018/5/4.
 */
public class BkResourceDtoValidator extends BkResourceDto implements ValidatorDto {
    private static final long serialVersionUID = 3827949294507975632L;

    public interface Add {
    }

    public interface Edit {
    }

    @Override
    @NotNull(groups = {Edit.class})
    public Long getId() {
        return super.getId();
    }

    @Override
    @NotEmpty(groups = {Add.class, Edit.class})
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    @NotEmpty(groups = {Add.class, Edit.class})
    public String getName() {
        return super.getName();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public ResourceType getType() {
        return super.getType();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public Boolean getRoot() {
        return super.getRoot();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public Boolean getLeaf() {
        return super.getLeaf();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public Integer getPriority() {
        return super.getPriority();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    public PermitType getPermitType() {
        return super.getPermitType();
    }

    @Override
    public void checkAndThrowErrors(Class checkType) {

    }
}

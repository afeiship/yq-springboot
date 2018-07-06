package cn.jzyunqi.ms.uaa.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkRoleDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkRoleQueryDto;
import cn.jzyunqi.ms.uaa.service.RoleService;
import cn.jzyunqi.ms.uaa.web.validator.BkRoleDtoValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author wiiyaya
 * @date 2018/5/15.
 */
@RestController
@Validated
public class RoleController extends AdminRestBaseController {

    @Resource
    private RoleService roleService;

    /**
     * 查询角色列表
     *
     * @return 角色信息
     */
    @RequestMapping(value = "/role/list")
    public RestResultDto roleList() {
        return RestResultDto.success(roleService.getAllRoles());
    }

    /**
     * 查询角色列表
     *
     * @return 角色信息
     */
    @RequestMapping(value = "/role/page")
    public RestResultDto rolePage(BkRoleQueryDto bkRoleQueryDto, Pageable pageable) {
        return RestResultDto.success(roleService.queryByPageable(bkRoleQueryDto, pageable));
    }

    /**
     * 新增角色
     *
     * @param bkRoleDto     角色信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/role/add")
    public RestResultDto roleAdd(@Validated(BkRoleDtoValidator.Add.class) BkRoleDtoValidator bkRoleDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkRoleDto, BkRoleDtoValidator.Add.class);
        roleService.saveRole(BeanUtilPlus.copyAs(bkRoleDto, BkRoleDto.class));

        return RestResultDto.success();
    }

    /**
     * 修改角色初始化
     *
     * @param id 角色id
     * @return 角色信息
     */
    @RequestMapping(value = "/role/editInit")
    public RestResultDto roleEditInit(@RequestParam @NotNull Long id) throws BusinessException {
        return RestResultDto.success(roleService.bkGetRoleById(id));
    }

    /**
     * 修改角色
     *
     * @param bkRoleDto     角色信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/role/edit")
    public RestResultDto roleEdit(@Validated(BkRoleDtoValidator.Edit.class) BkRoleDtoValidator bkRoleDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkRoleDto, BkRoleDtoValidator.Edit.class);
        roleService.updateRole(BeanUtilPlus.copyAs(bkRoleDto, BkRoleDto.class));

        return RestResultDto.success();
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return 用户信息
     */
    @RequestMapping(value = "/role/delete")
    public RestResultDto roleDelete(@RequestParam @NotNull Long id) throws BusinessException {
        roleService.deleteRole(id);

        return RestResultDto.success();
    }
}

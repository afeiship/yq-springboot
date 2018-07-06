package cn.jzyunqi.ms.system.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.model.spring.AuthorityDto;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.system.common.dto.backend.BkAdminUserDto;
import cn.jzyunqi.ms.system.common.dto.backend.query.BkAdminUserQueryDto;
import cn.jzyunqi.ms.system.service.AdminUserService;
import cn.jzyunqi.ms.system.web.validator.BkAdminUserDtoValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/15.
 */
@RestController
@Validated
public class AdminUserController extends AdminRestBaseController {

    @Resource
    private AdminUserService adminUserService;

    /**
     * 系统时间
     *
     * @return 国际化消息
     */
    @RequestMapping(value = "/common/systemTime")
    public RestResultDto systemTime() {
        return RestResultDto.success(adminUserService.getSystemTime());
    }

    /**
     * id解码
     *
     * @return 国际化消息
     */
    @RequestMapping(value = "/common/snowflakeId/decrypt")
    public RestResultDto snowflakeIdDecrypt(String id) {
        return RestResultDto.success(adminUserService.snowflakeIdDecrypt(id));
    }

    /**
     * 查询用户列表
     *
     * @param bkAdminUserQueryDto 查询条件
     * @param pageable            分页参数
     * @return 用户列表
     */
    @RequestMapping(value = "/adminUser/page")
    public RestResultDto adminUserPage(BkAdminUserQueryDto bkAdminUserQueryDto, Pageable pageable) {
        return RestResultDto.success(adminUserService.queryAdminUserPage(bkAdminUserQueryDto, pageable));
    }

    /**
     * 新增用户
     *
     * @param bkAdminUserDto 用户信息
     * @param bindingResult  校验信息
     * @return 成功
     */
    @RequestMapping(value = "/adminUser/add")
    public RestResultDto adminUserAdd(@Validated(BkAdminUserDtoValidator.Add.class) BkAdminUserDtoValidator bkAdminUserDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkAdminUserDto, BkAdminUserDtoValidator.Add.class);
        adminUserService.addAdminUser(BeanUtilPlus.copyAs(bkAdminUserDto, BkAdminUserDto.class));

        return RestResultDto.success();
    }

    /**
     * 修改用户初始化
     *
     * @param id 用户id
     * @return 用户信息
     */
    @RequestMapping(value = "/adminUser/editInit")
    public RestResultDto adminUserEditInit(@RequestParam @NotNull Long id) throws BusinessException {
        return RestResultDto.success(adminUserService.bkGetUserById(id));
    }

    /**
     * 修改用户
     *
     * @param bkAdminUserDto 用户信息
     * @param bindingResult  校验信息
     * @return 成功
     */
    @RequestMapping(value = "/adminUser/edit")
    public RestResultDto adminUserEdit(@Validated(BkAdminUserDtoValidator.Edit.class) BkAdminUserDtoValidator bkAdminUserDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkAdminUserDto, BkAdminUserDtoValidator.Edit.class);
        adminUserService.editAdminUser(BeanUtilPlus.copyAs(bkAdminUserDto, BkAdminUserDto.class));

        return RestResultDto.success();
    }

    /**
     * 获取管理员信息
     *
     * @return 用户信息
     */
    @RequestMapping(value = "/self/profile/loginInfo")
    public RestResultDto selfProfile() throws BusinessException {
        LoginUserDto loginUserDto = CurrentUserUtils.currentUser();

        BkAdminUserDto bkAdminUserDto = adminUserService.getUserProfile(loginUserDto.getId());
        bkAdminUserDto.setUsername(loginUserDto.getLoginUsername());

        List<String> privilegeCodeList = new ArrayList<>();
        for (AuthorityDto authorityDto : loginUserDto.getAuthorities()) {
            privilegeCodeList.add(authorityDto.getAuthority());
        }
        bkAdminUserDto.setPrivilegeCodeList(privilegeCodeList);
        return RestResultDto.success(bkAdminUserDto);
    }

    /**
     * 修改个人信息初始化
     *
     * @return 个人信息
     */
    @RequestMapping(value = "/self/profile/editInit")
    public RestResultDto selfProfileEditInit() throws BusinessException {
        Long userId = CurrentUserUtils.currentUserId();
        return RestResultDto.success(adminUserService.getUserProfile(userId));
    }

    /**
     * 修改个人信息
     *
     * @param bkAdminUserDto 个人信息
     * @param bindingResult  校验信息
     * @return 成功
     */
    @RequestMapping(value = "/self/profile/edit")
    public RestResultDto selfProfileEdit(@Validated(BkAdminUserDtoValidator.EditProfile.class) BkAdminUserDtoValidator bkAdminUserDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkAdminUserDto, BkAdminUserDtoValidator.EditProfile.class);

        Long userId = CurrentUserUtils.currentUserId();
        bkAdminUserDto.setId(userId);
        adminUserService.updateUserProfile(BeanUtilPlus.copyAs(bkAdminUserDto, BkAdminUserDto.class));

        return RestResultDto.success();
    }
}

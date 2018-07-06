package cn.jzyunqi.ms.system.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.system.service.AdminUserService;
import cn.jzyunqi.ms.uaa.service.ResourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/15.
 */
@RestController
@Validated
public class AdminUserLoginController extends AdminRestBaseController {

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private ResourceService resourceService;

    /**
     * 管理员登录
     *
     * @param username 手机号码
     * @param password 密码
     * @return token
     */
    @RequestMapping(value = "/adminUser/login")
    public RestResultDto login(@RequestParam @NotBlank String username, @RequestParam @NotBlank String password) throws BusinessException {
        return RestResultDto.success(adminUserService.login(username, password));
    }

    /**
     * 管理员登出
     *
     * @return 用户信息
     */
    @RequestMapping(value = "/adminUser/logout")
    public RestResultDto logout() throws BusinessException {
        String currentUserName = CurrentUserUtils.currentUser().getUsername();
        adminUserService.logout(currentUserName);
        return RestResultDto.success();
    }

    /**
     * 查询主菜单
     *
     * @return 主菜单列表
     */
    @RequestMapping(value = "/self/mainMenu")
    public RestResultDto selfMainMenu() throws BusinessException {
        List<Long> privilegeIdList = CurrentUserUtils.currentUser().getAuthorityIds();
        if (CollectionUtilPlus.isEmpty(privilegeIdList)) {
            return RestResultDto.success(new ArrayList<>());
        }
        return RestResultDto.success(resourceService.getUserAuthoritiesMainMenu(privilegeIdList));
    }

    /**
     * 查询子菜单
     *
     * @return 主菜单列表
     */
    @RequestMapping(value = "/self/subMenu")
    public RestResultDto selfSubMenu(@RequestParam @NotNull Long mainMenuId) throws BusinessException {
        List<Long> privilegeIdList = CurrentUserUtils.currentUser().getAuthorityIds();
        if (CollectionUtilPlus.isEmpty(privilegeIdList)) {
            return RestResultDto.success(new ArrayList<>());
        }
        return RestResultDto.success(resourceService.getUserAuthoritiesChildMenu(privilegeIdList, mainMenuId));
    }
}

package cn.jzyunqi.ms.uaa.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wiiyaya
 * @date 2018/5/15.
 */
@RestController
@Validated
public class UserAuthController extends AdminRestBaseController {

    @Resource
    private UserAuthService userAuthService;

    /**
     * 启用用户
     *
     * @param id 用户id
     * @return 成功
     */
    @RequestMapping(value = "/user/enable")
    public RestResultDto userEnable(@RequestParam @NotNull Long id) throws BusinessException {
        userAuthService.enableUser(id);
        return RestResultDto.success();
    }

    /**
     * 禁用用户
     *
     * @param id 用户id
     * @return 成功
     */
    @RequestMapping(value = "/user/disable")
    public RestResultDto userDisable(@RequestParam @NotNull Long id) throws BusinessException {
        userAuthService.disableUserById(id);
        return RestResultDto.success();
    }

    /**
     * 重置其他用户密码
     *
     * @param id               用户id
     * @param newPassword      新密码
     * @param currUserPassword 当前用户密码
     * @return 成功
     */
    @RequestMapping(value = "/user/password/reset")
    public RestResultDto userPasswordReset(@RequestParam @NotNull Long id, @RequestParam @NotEmpty String newPassword, @RequestParam @NotEmpty String currUserPassword) throws BusinessException {
        Long currentUserId = CurrentUserUtils.currentUserId();
        userAuthService.resetPassword(id, currentUserId, newPassword, currUserPassword);
        return RestResultDto.success();
    }

    /**
     * 修改个人密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 成功
     */
    @RequestMapping(value = "/self/password/reset")
    public RestResultDto selfPasswordReset(@RequestParam @NotEmpty String oldPassword, @RequestParam @NotEmpty String newPassword) throws BusinessException {
        Long currentUserId = CurrentUserUtils.currentUserId();
        userAuthService.changeMyPassword(currentUserId, oldPassword, newPassword);
        return RestResultDto.success();
    }
}

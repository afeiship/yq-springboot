package cn.jzyunqi.ms.uaa.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.ms.uaa.common.dto.UserDto;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;

/**
 * @author wiiyaya
 * @date 2018/3/18.
 */
public interface UserAuthService {

    /**
     * 登录
     *
     * @param authType  账号类型
     * @param username  用户名
     * @param password  密码
     * @param adminUser 是否后台用户
     * @return token
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    String oauth2Login(AuthType authType, String username, String password, boolean adminUser) throws BusinessException;

    /**
     * 查询当前用户密码是否过期
     *
     * @param userId 用户id
     * @return true 过期了，false 没有过期
     */
    boolean isPasswordExpired(Long userId);

    /**
     * 使用指定用户名密码创建用户
     *
     * @param authType 账户类型
     * @param username 用户名
     * @param password 密码
     */
    UserDto createUser(AuthType authType, String username, String password) throws BusinessException;

    /**
     * 查找用户名
     *
     * @param authType 账户类型
     * @param userId   用户id
     * @return 用户名
     */
    String findUsernameByUserId(AuthType authType, Long userId);

    /**
     * 查找用id
     *
     * @param authType 账户类型
     * @param username 用户名
     * @return 用户id
     */
    Long findIdByUsername(AuthType authType, String username);

    /**
     * 启用用户
     *
     * @param userId 用户id
     */
    void enableUser(Long userId) throws BusinessException;

    /**
     * 停用用户
     *
     * @param userId 用户id
     */
    void disableUserById(Long userId) throws BusinessException;

    /**
     * 重设用户密码
     *
     * @param userId              待修改密码用户id
     * @param currentUserId       当前用户ID
     * @param newPassword         新密码
     * @param currentUserPassword 当前用户密码
     */
    void resetPassword(Long userId, Long currentUserId, String newPassword, String currentUserPassword) throws BusinessException;

    /**
     * 修改用户自己的密码
     *
     * @param currentUserId 用户ID
     * @param oldPassword   旧密码
     * @param newPassword   新密码
     */
    void changeMyPassword(Long currentUserId, String oldPassword, String newPassword) throws BusinessException;

    /**
     * 查询用户是否启用
     *
     * @param userId 用户id
     * @return true 是/false 否
     */
    Boolean isUserEnabled(Long userId);

    /**
     * 删除指定用户名token
     *
     * @param username 用户名
     */
    void removeMemberToken(String username);

    /**
     * 指定用户账号是否设置了密码
     *
     * @param authType 账户类型
     * @param userId   用户id
     * @return true 设置了
     */
    boolean hasPassword(AuthType authType, Long userId);

    /**
     * 用户是否创建了指定类型的账号
     *
     * @param authType 账户类型
     * @param userId   用户id
     * @return true 创建了
     */
    boolean hasAuthType(AuthType authType, Long userId);

    /**
     * 创建一个新的账户
     *
     * @param authType 账户类型
     * @param userId   用户id
     * @param username 用户名
     * @param password 登录密码
     * @return 用户id，有可能为自己的id，也有可能为其他用户id
     */
    Long createAuth(AuthType authType, Long userId, String username, String password) throws BusinessException;

    /**
     * 合并账号(一定是微信账号合并到手机账号)
     *
     * @param sourceMemberId 合并来源用户id
     * @param destMemberId   目标用户id
     */
    void mergeAuth(Long sourceMemberId, Long destMemberId) throws BusinessException;

    /**
     * 重设密码
     *
     * @param authType 账户类型
     * @param username 用户名
     * @param password 密码
     */
    void resetPassword(AuthType authType, String username, String password) throws BusinessException;

    /**
     * 重设用户名
     *
     * @param authType 账户类型
     * @param userId   用户id
     * @param username 用户名
     */
    void resetUsername(AuthType authType, Long userId, String username) throws BusinessException;
}

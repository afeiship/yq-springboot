package cn.jzyunqi.ms.system.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.model.SnowflakeId;
import cn.jzyunqi.ms.system.common.dto.backend.BkAdminUserDto;
import cn.jzyunqi.ms.system.common.dto.backend.query.BkAdminUserQueryDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface AdminUserService {

    /**
     * 获取所有的后台用户
     *
     * @param bkAdminUserQueryDto 后台用户查询条件
     * @param pageable            分页参数
     * @return 分页数据
     */
    PageDto<BkAdminUserDto> queryAdminUserPage(BkAdminUserQueryDto bkAdminUserQueryDto, Pageable pageable);

    /**
     * 保存页面传输过来的用户和角色数据
     *
     * @param bkAdminUserDto 需保存用户信息
     */
    void addAdminUser(BkAdminUserDto bkAdminUserDto) throws BusinessException;

    /**
     * 根据用户id获取用户信息，包含了拥有的角色id信息
     *
     * @param userId 用户id
     * @return 用户详细信息
     */
    BkAdminUserDto bkGetUserById(Long userId) throws BusinessException;

    /**
     * 更新页面传过来的用户和角色数据
     *
     * @param bkAdminUserDto 需要更新的用户数据
     */
    void editAdminUser(BkAdminUserDto bkAdminUserDto) throws BusinessException;

    /**
     * 获取用户个人信息
     *
     * @param userId 用户ID
     * @return 用户个人信息
     */
    BkAdminUserDto getUserProfile(Long userId) throws BusinessException;

    /**
     * 更新用户个人信息
     *
     * @param bkAdminUserDto 用户数据
     */
    void updateUserProfile(BkAdminUserDto bkAdminUserDto) throws BusinessException;

    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录token
     */
    BkAdminUserDto login(String username, String password) throws BusinessException;

    /**
     * 管理员登出
     *
     * @param username 用户名
     */
    void logout(String username);

    /**
     * 获取系统时间
     *
     * @return 系统时间
     */
    LocalDateTime getSystemTime();

    /**
     * id解码
     *
     * @param snowflakeId 雪花id
     * @return 解码
     */
    SnowflakeId snowflakeIdDecrypt(String snowflakeId);
}

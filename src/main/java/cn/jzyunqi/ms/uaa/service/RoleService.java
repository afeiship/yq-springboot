package cn.jzyunqi.ms.uaa.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.ms.uaa.common.dto.RoleDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkRoleDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkRoleQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface RoleService {

    /**
     * 获取所有的角色列表
     *
     * @param bkRoleQueryDto 角色查询条件
     * @param pageable       分页参数
     * @return 角色列表
     */
    PageDto<BkRoleDto> queryByPageable(BkRoleQueryDto bkRoleQueryDto, Pageable pageable);

    /**
     * 保存页面传输过来的角色和权限数据
     *
     * @param bkRoleDto 角色信息
     * @return 角色id
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    Long saveRole(BkRoleDto bkRoleDto) throws BusinessException;

    /**
     * 获取角色基本信息
     *
     * @param roleId 角色id
     * @return 角色
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    BkRoleDto bkGetRoleById(Long roleId) throws BusinessException;

    /**
     * 给指定用户新增指定角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     */
    void addUserRoles(Long userId, List<Long> roleIdList);

    /**
     * 更新角色
     *
     * @param bkRoleDto 角色信息
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    void updateRole(BkRoleDto bkRoleDto) throws BusinessException;

    /**
     * 更新指定用户的角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     */
    void updateUserRoles(Long userId, List<Long> roleIdList);

    /**
     * 删除角色
     *
     * @param roleId 角色id
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    void deleteRole(Long roleId) throws BusinessException;

    /**
     * 获取用户所有的角色id（关系）
     *
     * @param userId 用户id
     * @return 用户角色关系id
     */
    List<Long> getUserRoleIds(Long userId);

    /**
     * 查找角色名称
     *
     * @param userId 用户ud
     * @return 角色名称
     */
    List<String> findNamesByUserId(Long userId);

    /**
     * 查找全部角色
     *
     * @return 角色列表
     */
    List<RoleDto> getAllRoles();
}

package cn.jzyunqi.ms.uaa.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.model.TreeDto;
import cn.jzyunqi.ms.uaa.common.dto.ResourceDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkResourceDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkResourceQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface ResourceService {

    /**
     * 获取不需要授权的资源数据(系统使用)
     *
     * @return 资源数组
     */
    String[] getNoNeedAuthResources();

    /**
     * 获取需要授权的资源数据(系统使用)
     *
     * @return 资源-权限关系映射
     */
    Map<String, String[]> getNeedAuthResources();

    /**
     * 获取授权用户可访问的主目录
     *
     * @param privilegeIds 权限ID列表
     * @return 主目录列表
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    List<ResourceDto> getUserAuthoritiesMainMenu(List<Long> privilegeIds) throws BusinessException;

    /**
     * 获取授权用户可访问的子目录
     *
     * @param privilegeIds 权限ID列表
     * @param parentId     父目录id
     * @return 子目录列表
     */
    List<ResourceDto> getUserAuthoritiesChildMenu(List<Long> privilegeIds, Long parentId) throws BusinessException;

    /**
     * 分页获取所有的资源数据
     *
     * @param bkResourceQueryDto 查询条件
     * @param pageRequest        分页参数
     * @return 资源分页数据
     */
    PageDto<BkResourceDto> queryByPageable(BkResourceQueryDto bkResourceQueryDto, Pageable pageRequest);

    /**
     * 新增资源
     *
     * @param bkResourceDto 资源信息
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    void saveResource(BkResourceDto bkResourceDto) throws BusinessException;

    /**
     * 更新资源
     *
     * @param bkResourceDto 资源信息
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    void updateResource(BkResourceDto bkResourceDto) throws BusinessException;

    /**
     * 获取资源详细信息
     *
     * @param resourceId 资源id
     * @return 资源信息
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    BkResourceDto getResourceById(Long resourceId) throws BusinessException;

    /**
     * 获取主目录树结构展示
     *
     * @return 树结构
     */
    List<TreeDto> getMainResourceTree();

    /**
     * 获取子目录树结构展示
     *
     * @param parentId 父目录id
     * @return 树结构
     */
    List<TreeDto> getChildResourceTree(Long parentId);

    /**
     * 修改资源权限
     *
     * @param resourceId   资源id
     * @param privilegeIds 权限id列表
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    void updateResourceAuth(Long resourceId, List<Long> privilegeIds) throws BusinessException;
}

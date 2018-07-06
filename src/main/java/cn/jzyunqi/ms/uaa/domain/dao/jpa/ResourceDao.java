package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.Resource;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface ResourceDao extends BaseDao<Resource, Long> {

    /**
     * 查找所有不需要授权的资源
     *
     * @return List<String> 不需要授权的资源URL列表
     */
    @Query("select r.url from Resource r where r.permitType = 'N' and r.url is not null")
    List<String> findNoNeedAuthResources();

    /**
     * 查找所有需要授权的资源
     *
     * @return List<Resource> 需要授权的资源实体列表
     */
    @Query("select r from Resource r where r.permitType = 'A'")
    List<Resource> findNeedAuthResources();

    /**
     * 查找所有的菜单
     *
     * @return List<Resource> 所有的菜单
     */
    @Query("select r from Resource r where r.type = 'M' order by r.parentId asc, r.priority asc")
    List<Resource> findAllMenu();

    /**
     * 从菜单id中获取主菜单id
     *
     * @param resourceIds 菜单id
     * @return 主菜单id
     */
    @Query("select r from Resource r where r.id in (?1) and r.root = true and r.type = 'M' order by r.priority asc")
    List<Resource> findMainMenus(Iterable<Long> resourceIds);

    /**
     * 从指定的菜单id中筛选出指定主菜单的子菜单
     *
     * @param resourceIds 子级菜单id
     * @param parentId    主菜单id
     * @return 子级菜单id
     */
    @Query("select r from Resource r where r.id in (?1) and r.parentId = ?2 and r.type = 'M' order by r.priority asc")
    List<Resource> findChildMenus(List<Long> resourceIds, Long parentId);

    /**
     * 查找根资源，按钮不允许作为根资源存在
     *
     * @return List<Long> 根资源集合
     */
    @Query("select r from Resource r where r.root = true and r.type <> 'B' order by r.priority asc")
    List<Resource> findMainResource();

    /**
     * 根据权限id和给定的父菜单id查找该权限可访问的所有子菜单
     *
     * @param parentId 父菜单id
     * @return List<Long> 子菜单id集合
     */
    @Query("select r from Resource r where r.parentId = ?1 order by r.priority asc")
    List<Resource> findChildResource(Long parentId);

    /**
     * 查找指定资源路径的数量，可用来判断是否存在
     *
     * @param url 资源路径
     * @return 数量
     */
    @Query("select count(r.id) from Resource r where r.url= ?1 and r.url <> '#'")
    Long countUrl(String url);

    /**
     * 根据url和ID统计不是此ID的url数量
     *
     * @param url 资源url
     * @param id  资源id
     * @return 数量
     */
    @Query("select count(r.id) from Resource r where r.url= ?1 and r.url <> '#' and r.id <> ?2")
    Long countUrlWithoutId(String url, Long id);

    /**
     * 获取内容（仅超链接和按钮）
     *
     * @param contentIds 内容id
     * @return 内容列表
     */
    @Query("select c from Resource c where c.id in(?1) and c.type in ('B', 'H')")
    List<Resource> findContents(Iterable<Long> contentIds);

    /**
     * 获取资源不包含的内容（仅超链接和按钮）
     *
     * @param contentIds 内容id
     * @return 不包含的内容列表
     */
    @Query("select c from Resource c where c.type in ('B', 'H') and c.id not in (?1)")
    List<Resource> findContentNotIn(List<Long> contentIds);

    /**
     * 查找全部内容（仅超链接和按钮）
     *
     * @return 所有的内容
     */
    @Query("select c from Resource c where c.type in ('B', 'H')")
    List<Resource> findAllContent();

}

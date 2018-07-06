package cn.jzyunqi.ms.system.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.system.common.enums.ParamType;
import cn.jzyunqi.ms.system.domain.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface ParamDao extends BaseDao<Param, Long> {
    /**
     * 根据code查询value
     *
     * @param code code
     * @return value
     */
    @Query("select p.value from Param p where p.code = ?1")
    String findNameByKey(String code);

    /**
     * 根据key查询
     *
     * @param key key
     * @return 参数对象
     */
    @Query("select p from Param p where p.code = ?1")
    Param findParamByKey(String key);

    /**
     * 根据参数类型查询参数范围
     *
     * @param paramType 参数列席
     * @return 参数列表
     */
    @Query("select p from Param p where p.type = ?1 order by p.priority asc")
    List<Param> findByType(ParamType paramType);

    /**
     * 根据参数类型和父key查询参数列表
     *
     * @param paramType 参数列席
     * @param parentKey 父key
     * @return 参数列表
     */
    @Query("select p from Param p where p.type = ?1 and p.parentCode = ?2 order by p.priority asc")
    List<Param> findByTypeAndParentKey(ParamType paramType, String parentKey);

    /**
     * 判断这个参数类型是否存在
     *
     * @param key key
     * @return true 存在， false 不存在
     */
    @Query("select count(p)>0 from Param p where p.code = ?1")
    boolean isCodeExists(String key);

    /**
     * 根据类型和extend01查询
     *
     * @param paramType 类型
     * @param extend01  extend01
     * @return 参数对象
     */
    @Query("select p from Param p where p.type = ?1 and p.extend01 = ?2")
    Param findByTypeAndExtend01(ParamType paramType, String extend01);

    /**
     * 根据类型和父key更新其他参数为非默认值
     *
     * @param paramType 类型
     * @param parentKey 父key
     * @param key       key
     */
    @Query("update Param p set p.isDefault = false where p.type = ?1 and p.parentCode = ?2 and p.code <> ?3")
    @Modifying
    void updateUnDefaultByTypeAndParentKey(ParamType paramType, String parentKey, String key);

    /**
     * 根据类型更新其他参数为非默认值
     *
     * @param paramType 类型
     * @param key       key
     */
    @Query("update Param p set p.isDefault = false where p.type = ?1 and p.code <> ?2")
    @Modifying
    void updateUnDefaultByType(ParamType paramType, String key);
}

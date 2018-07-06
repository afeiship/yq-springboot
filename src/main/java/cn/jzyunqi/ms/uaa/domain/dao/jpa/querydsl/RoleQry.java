package cn.jzyunqi.ms.uaa.domain.dao.jpa.querydsl;

import cn.jzyunqi.common.support.SqlFilter;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkRoleQueryDto;
import cn.jzyunqi.ms.uaa.domain.QRole;
import com.querydsl.jpa.JPQLQuery;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class RoleQry {

    /**
     * 角色表
     */
    private static final QRole ROLE = QRole.role;

    /**
     * 组装查询条件
     *
     * @param schQry         JPQLQuery
     * @param notCountQry    是否是查询数量 true：是， false：否
     * @param bkRoleQueryDto 查询条件
     */
    public static <T> void searchRole(JPQLQuery<T> schQry, boolean notCountQry, BkRoleQueryDto bkRoleQueryDto) {
        if (StringUtilPlus.isNotEmpty(bkRoleQueryDto.getName())) {
            String inputName = SqlFilter.filterForLike(bkRoleQueryDto.getName().toUpperCase());
            schQry.where(ROLE.name.upper().like(inputName, SqlFilter.DEFALT_ESCAPE_CHAR));
        }
        if (bkRoleQueryDto.getCode() != null) {
            schQry.where(ROLE.code.eq(bkRoleQueryDto.getCode()));
        }
        if (notCountQry) {
            schQry.orderBy(ROLE.id.asc());
        }
    }

}

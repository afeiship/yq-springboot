package cn.jzyunqi.ms.uaa.domain.dao.jpa.querydsl;

import cn.jzyunqi.common.support.SqlFilter;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkResourceQueryDto;
import cn.jzyunqi.ms.uaa.common.enums.ResourceType;
import cn.jzyunqi.ms.uaa.domain.QResource;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class ResourceQry {

    /**
     * 资源表
     */
    private static final QResource RESOURCE = QResource.resource;

    /**
     * 组装查询条件
     *
     * @param schQry             JPQLQuery
     * @param notCountQry        是否是查询数量 true：是， false：否
     * @param bkResourceQueryDto 查询条件
     */
    public static <T> void searchResource(JPQLQuery<T> schQry, boolean notCountQry, BkResourceQueryDto bkResourceQueryDto) {
        if (StringUtilPlus.isNotEmpty(bkResourceQueryDto.getName())) {
            String inputName = SqlFilter.filterForLike(bkResourceQueryDto.getName().toUpperCase());
            schQry.where(RESOURCE.name.upper().like(inputName, SqlFilter.DEFALT_ESCAPE_CHAR));
        }
        if (bkResourceQueryDto.getType() != null) {
            schQry.where(RESOURCE.type.eq(bkResourceQueryDto.getType()));
        }
        if (bkResourceQueryDto.getMsClientId() != null) {
            schQry.where(RESOURCE.msClientId.eq(bkResourceQueryDto.getMsClientId()));
        }
        if (bkResourceQueryDto.getParentId() != null) {
            schQry.where(RESOURCE.parentId.eq(bkResourceQueryDto.getParentId()));
        }
        if (bkResourceQueryDto.getPermitType() != null) {
            schQry.where(RESOURCE.permitType.eq(bkResourceQueryDto.getPermitType()));
        }
        if (notCountQry) {
            schQry.orderBy(Expressions.cases()
                            .when(RESOURCE.type.eq(ResourceType.M)).then(Expressions.ONE)
                            .when(RESOURCE.type.eq(ResourceType.H)).then(Expressions.TWO)
                            .otherwise(Expressions.THREE)
                            .asc()
                    , RESOURCE.parentId.asc()
                    , RESOURCE.priority.asc()
                    , RESOURCE.id.asc());
        }
    }
}

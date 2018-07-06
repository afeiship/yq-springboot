package cn.jzyunqi.ms.system.domain.dao.jpa.querydsl;

import cn.jzyunqi.common.support.SqlFilter;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.system.common.dto.backend.query.ParamQueryDto;
import cn.jzyunqi.ms.system.domain.QParam;
import com.querydsl.jpa.JPQLQuery;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class ParamQry {

    /**
     * 参数表
     */
    private static final QParam PARAM = QParam.param;

    /**
     * 组装查询条件
     *
     * @param schQry        JPQLQuery
     * @param notCountQry   是否是查询数量 true：是， false：否
     * @param paramQueryDto 查询条件
     */

    public static <T> void searchParam(JPQLQuery<T> schQry, boolean notCountQry, ParamQueryDto paramQueryDto) {
        if (paramQueryDto.getType() != null) {
            schQry.where(PARAM.type.eq(paramQueryDto.getType()));
        }
        if (StringUtilPlus.isNotEmpty(paramQueryDto.getCode())) {
            String inputKey = SqlFilter.filterForLike(paramQueryDto.getCode().toUpperCase());
            schQry.where(PARAM.code.upper().like(inputKey, SqlFilter.DEFALT_ESCAPE_CHAR));
        }
        if (StringUtilPlus.isNotEmpty(paramQueryDto.getValue())) {
            schQry.where(PARAM.value.eq(paramQueryDto.getValue()));
        }
        if (StringUtilPlus.isNotEmpty(paramQueryDto.getParentCode())) {
            String parentKey = SqlFilter.filterForLike(paramQueryDto.getParentCode().toUpperCase());
            schQry.where(PARAM.parentCode.upper().like(parentKey, SqlFilter.DEFALT_ESCAPE_CHAR));
        }
        if (StringUtilPlus.isNotEmpty(paramQueryDto.getRemark())) {
            String inputRemark = SqlFilter.filterForLike(paramQueryDto.getRemark().toUpperCase());
            schQry.where(PARAM.remark.upper().like(inputRemark, SqlFilter.DEFALT_ESCAPE_CHAR));
        }
        if (notCountQry) {
            schQry.orderBy(PARAM.type.asc(), PARAM.priority.asc());
        }
    }

}

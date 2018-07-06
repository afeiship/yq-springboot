package cn.jzyunqi.ms.job.domain.dao.jpa.querydsl;

import cn.jzyunqi.ms.job.common.dto.backend.query.BkBatchQueryDto;
import com.querydsl.jpa.JPQLQuery;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class BatchQry {

    /**
     * 组装查询条件
     *
     * @param schQry          JPQLQuery
     * @param notCountQry     是否是查询数量 true：是， false：否
     * @param bkBatchQueryDto 查询条件
     */
    public static <T> void searchBatch(JPQLQuery<T> schQry, boolean notCountQry, BkBatchQueryDto bkBatchQueryDto) {

    }
}

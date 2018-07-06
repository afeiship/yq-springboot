package cn.jzyunqi.ms.job.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.job.domain.Batch;
import org.springframework.data.jpa.repository.Query;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface BatchDao extends BaseDao<Batch, Long> {

    /**
     * 查找是否存在同名任务
     *
     * @param taskName 任务名称
     * @return true 存在， false 不存在
     */
    @Query("select count(b) from Batch b where b.taskName=?1")
    Long isBatchNameExist(String taskName);
}

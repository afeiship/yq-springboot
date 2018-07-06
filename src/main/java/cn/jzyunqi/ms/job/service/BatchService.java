package cn.jzyunqi.ms.job.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.ms.job.common.dto.BatchDto;
import cn.jzyunqi.ms.job.common.dto.backend.BkBatchDto;
import cn.jzyunqi.ms.job.common.dto.backend.query.BkBatchQueryDto;
import org.springframework.data.domain.Pageable;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface BatchService {

    /**
     * 查询所有批处理任务
     *
     * @param bkBatchQueryDto 查询条件
     * @param pageable        分页条件
     * @return 批处理列表
     */
    PageDto<BatchDto> queryByPageable(BkBatchQueryDto bkBatchQueryDto, Pageable pageable);

    /**
     * 保存批处理任务
     *
     * @param bkBatchDto 批处理信息
     */
    void saveBatch(BkBatchDto bkBatchDto) throws BusinessException;

    /**
     * 获取批处理信息
     *
     * @param batchId 批处理id
     * @return 批处理
     */
    BkBatchDto getBatchById(Long batchId);

    /**
     * 更新批处理信息
     *
     * @param bkBatchDto 批处理信息
     */
    void updateBatch(BkBatchDto bkBatchDto) throws BusinessException;

    /**
     * 删除批处理
     *
     * @param batchId 批处理id
     */
    void deleteBatch(Long batchId) throws BusinessException;

    /**
     * 立即调用批处理
     *
     * @param batchId 批处理id
     */
    void invokeBatch(Long batchId) throws BusinessException;

    /**
     * 暂停批处理
     *
     * @param batchId 批处理id
     */
    void pauseBatch(Long batchId) throws BusinessException;

    /**
     * 恢复批处理
     *
     * @param batchId 批处理id
     */
    void resumeBatch(Long batchId) throws BusinessException;

}

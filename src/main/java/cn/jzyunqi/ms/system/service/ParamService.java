package cn.jzyunqi.ms.system.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.ms.system.common.dto.ParamDto;
import cn.jzyunqi.ms.system.common.dto.ParamRedisDto;
import cn.jzyunqi.ms.system.common.dto.backend.BkParamDto;
import cn.jzyunqi.ms.system.common.dto.backend.query.ParamQueryDto;
import cn.jzyunqi.ms.system.common.enums.ParamType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface ParamService {

    /**
     * 根据key查询实体，并做缓存
     *
     * @param code code
     * @return 参数实体
     */
    ParamRedisDto getParamByCode(String code);

    /**
     * 查询参数类型列表
     *
     * @param paramType 查询条件
     */
    List<ParamDto> getParamListByType(ParamType paramType);

    /**
     * 根据参数类型和父key查询参数列表
     *
     * @param paramType 参数列席
     * @param parentKey 父key
     * @param withAll   是否包含全部选项
     * @param withBlank 是否包含请选择选择
     * @return 参数列表
     */
    List<ParamDto> getParamListByTypeAndParentKey(ParamType paramType, String parentKey, boolean withAll, boolean withBlank);

    /**
     * 获取参数列表
     *
     * @param paramQueryDto 参数查询dto
     * @param pageable      分页对象
     * @return 参数分页列表
     */
    PageDto<ParamDto> findParamPage(ParamQueryDto paramQueryDto, Pageable pageable);

    /**
     * 保存参数
     *
     * @param bkParamDto 参数dto
     * @throws cn.jzyunqi.common.exception.BusinessException 自定义异常
     */
    void saveParam(BkParamDto bkParamDto) throws BusinessException;

    /**
     * 根据ID查询参数对象
     *
     * @param paramId 参数id
     * @return 参数实体
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    ParamDto getParamById(Long paramId) throws BusinessException;

    /**
     * 更新参数对象
     *
     * @param bkParamDto 参数信息
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    void updateParam(BkParamDto bkParamDto) throws BusinessException;

    /**
     * 根据类型和extend01查询key
     *
     * @param paramType 类型
     * @param extend01  extend01
     * @return key
     */
    String getKeyByTypeAndExtend01(ParamType paramType, String extend01);
}

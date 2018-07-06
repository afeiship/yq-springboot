package cn.jzyunqi.ms.system.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.helper.RedisHelper;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.persistence.dao.tools.JF;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.system.common.constant.SystemCache;
import cn.jzyunqi.ms.system.common.constant.SystemMessageConstant;
import cn.jzyunqi.ms.system.common.dto.ParamDto;
import cn.jzyunqi.ms.system.common.dto.ParamRedisDto;
import cn.jzyunqi.ms.system.common.dto.backend.BkParamDto;
import cn.jzyunqi.ms.system.common.dto.backend.query.ParamQueryDto;
import cn.jzyunqi.ms.system.common.enums.ParamType;
import cn.jzyunqi.ms.system.domain.Param;
import cn.jzyunqi.ms.system.domain.dao.jpa.ParamDao;
import cn.jzyunqi.ms.system.domain.dao.jpa.querydsl.ParamQry;
import cn.jzyunqi.ms.system.service.ParamService;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("paramService")
public class ParamServiceImpl implements ParamService {

    @Resource
    private ParamDao paramDao;

    @Resource
    private RedisHelper redisHelper;

    @Override
    public ParamRedisDto getParamByCode(String code) {
        ParamRedisDto paramRedisDto = (ParamRedisDto) redisHelper.vGet(SystemCache.SYS_PARAM_V, code);

        if (paramRedisDto == null) {
            Param param = paramDao.findParamByKey(code);

            paramRedisDto = new ParamRedisDto();
            paramRedisDto.setCode(param.getCode());
            paramRedisDto.setName(param.getName());
            paramRedisDto.setValue(param.getValue());
            paramRedisDto.setTime(LocalDateTime.now());

            redisHelper.vPut(SystemCache.SYS_PARAM_V, code, paramRedisDto);
        }
        return paramRedisDto;
    }

    @Override
    public List<ParamDto> getParamListByType(ParamType paramType) {
        List<Param> paramList = paramDao.findByType(paramType);
        List<ParamDto> paramDtoList = new ArrayList<>();
        for (Param param : paramList) {
            ParamDto paramDto = new ParamDto();
            paramDto.setCode(param.getCode());
            paramDto.setName(param.getName());
            paramDtoList.add(paramDto);
        }

        return paramDtoList;
    }

    @Override
    public List<ParamDto> getParamListByTypeAndParentKey(ParamType paramType, String parentKey, boolean withAll, boolean withBlank) {
        List<Param> paramList = paramDao.findByTypeAndParentKey(paramType, parentKey);
        List<ParamDto> paramDtoList = new ArrayList<>();

        for (Param param : paramList) {
            ParamDto paramDto = new ParamDto();
            paramDto.setCode(param.getCode());
            paramDto.setName(param.getName());
            paramDto.setExtend01(param.getExtend01());
            paramDto.setIsDefault(param.getIsDefault());
            paramDtoList.add(paramDto);
        }

        return paramDtoList;
    }

    @Override
    public PageDto<ParamDto> findParamPage(ParamQueryDto paramQueryDto, Pageable pageable) {
        List<ParamDto> paramDtoList = new ArrayList<>();
        Page<Param> paramPage = paramDao.findAllJF(new JF() {
            @Override
            public <T> void prepareQry(JPQLQuery<T> qry, boolean notCountQry) {
                ParamQry.searchParam(qry, notCountQry, paramQueryDto);
            }
        }, pageable);
        for (Param param : paramPage) {
            ParamDto paramDto = new ParamDto();
            paramDto.setId(param.getId());
            paramDto.setType(param.getType());
            paramDto.setCode(param.getCode());
            paramDto.setName(param.getName());
            paramDto.setParentCode(param.getParentCode());
            paramDto.setValue(param.getValue());
            paramDto.setRemark(param.getRemark());
            paramDto.setExtend01(param.getExtend01());
            paramDto.setExtend02(param.getExtend02());
            paramDto.setPriority(param.getPriority());
            paramDto.setIsDefault(param.getIsDefault());

            paramDtoList.add(paramDto);
        }
        return new PageDto<>(paramDtoList, paramPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void saveParam(BkParamDto bkParamDto) throws BusinessException {
        //判断参数是否已经存在
        if (paramDao.isCodeExists(bkParamDto.getCode())) {
            //参数关键字已经存在
            throw new BusinessException(SystemMessageConstant.ERROR_PARAM_CODE_EXIST);
        }
        //判断父关键字是否存在
        if (StringUtilPlus.isNotEmpty(bkParamDto.getParentCode())) {
            boolean parentKeyExists = paramDao.isCodeExists(bkParamDto.getParentCode());
            if (!parentKeyExists) {
                throw new BusinessException(SystemMessageConstant.ERROR_PARAM_PARENT_NOT_EXIST);
            }
        }
        //判断是否设置默认值
        if (bkParamDto.getIsDefault() == null) {
            bkParamDto.setIsDefault(Boolean.FALSE);
        }
        if (bkParamDto.getIsDefault()) {
            if (StringUtilPlus.isNotEmpty(bkParamDto.getParentCode())) { //有父对象
                paramDao.updateUnDefaultByTypeAndParentKey(bkParamDto.getType(), bkParamDto.getParentCode(), bkParamDto.getCode());
            } else {
                paramDao.updateUnDefaultByType(bkParamDto.getType(), bkParamDto.getCode());
            }
        }
        Param param = new Param();
        param.setType(bkParamDto.getType());
        param.setCode(bkParamDto.getCode());
        param.setName(bkParamDto.getName());
        param.setParentCode(bkParamDto.getParentCode());
        param.setValue(bkParamDto.getValue());
        param.setRemark(bkParamDto.getRemark());
        param.setExtend01(bkParamDto.getExtend01());
        param.setExtend02(bkParamDto.getExtend02());
        param.setPriority(bkParamDto.getPriority());
        param.setIsDefault(bkParamDto.getIsDefault());

        paramDao.save(param);
    }

    @Override
    public ParamDto getParamById(Long paramId) throws BusinessException {
        Optional<Param> optionalParam = paramDao.findById(paramId);
        //若为空直接抛异常
        if (!optionalParam.isPresent()) {
            //找不到指定参数
            throw new BusinessException(SystemMessageConstant.ERROR_PARAM_NOT_FOUND);
        }
        Param param = optionalParam.get();
        ParamDto paramDto = new ParamDto();
        paramDto.setId(param.getId());
        paramDto.setType(param.getType());
        paramDto.setCode(param.getCode());
        paramDto.setName(param.getName());
        paramDto.setValue(param.getValue());
        paramDto.setParentCode(param.getParentCode());
        paramDto.setIsDefault(param.getIsDefault());
        paramDto.setPriority(param.getPriority());
        paramDto.setExtend01(param.getExtend01());
        paramDto.setExtend02(param.getExtend02());
        paramDto.setRemark(param.getRemark());

        return paramDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateParam(BkParamDto bkParamDto) throws BusinessException {
        Optional<Param> optionalParam = paramDao.findById(bkParamDto.getId());
        //若为空直接抛异常
        if (!optionalParam.isPresent()) {
            //找不到指定参数
            throw new BusinessException(SystemMessageConstant.ERROR_PARAM_NOT_FOUND);
        }
        Param param = optionalParam.get();

        //判断父关键字是否存在
        if (StringUtilPlus.isNotEmpty(bkParamDto.getParentCode())) {
            boolean parentKeyExists = paramDao.isCodeExists(bkParamDto.getParentCode());
            if (!parentKeyExists) {
                throw new BusinessException(SystemMessageConstant.ERROR_PARAM_PARENT_NOT_EXIST);
            }
        }
        //判断是否设置默认值
        if (bkParamDto.getIsDefault() == null) {
            bkParamDto.setIsDefault(Boolean.FALSE);
        }
        if (bkParamDto.getIsDefault()) {
            if (StringUtilPlus.isNotEmpty(bkParamDto.getParentCode())) { //有父对象
                paramDao.updateUnDefaultByTypeAndParentKey(param.getType(), bkParamDto.getParentCode(), param.getCode());
            } else {
                paramDao.updateUnDefaultByType(param.getType(), param.getCode());
            }
        }

        param.setName(bkParamDto.getName());
        param.setValue(bkParamDto.getValue());
        param.setRemark(bkParamDto.getRemark());
        param.setExtend01(bkParamDto.getExtend01());
        param.setExtend02(bkParamDto.getExtend02());
        param.setPriority(bkParamDto.getPriority());
        param.setIsDefault(bkParamDto.getIsDefault());
        paramDao.save(param);

        //清除缓存
        redisHelper.removeKey(SystemCache.SYS_PARAM_V, param.getCode());
    }

    @Override
    public String getKeyByTypeAndExtend01(ParamType paramType, String extend01) {
        Param param = paramDao.findByTypeAndExtend01(paramType, extend01);
        String key = param.getCode();
        return key;
    }
}

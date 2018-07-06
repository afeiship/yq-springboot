package cn.jzyunqi.ms.system.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.ms.system.common.dto.backend.BkParamDto;
import cn.jzyunqi.ms.system.common.dto.backend.query.ParamQueryDto;
import cn.jzyunqi.ms.system.common.enums.ParamType;
import cn.jzyunqi.ms.system.service.ParamService;
import cn.jzyunqi.ms.system.web.validator.BkParamDtoValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author wiiyaya
 * @date 2018/5/15.
 */
@RestController
@Validated
public class ParamController extends AdminRestBaseController {

    @Resource
    private ParamService paramService;

    /**
     * 获取参数详情
     *
     * @return 参数信息
     */
    @RequestMapping(value = "/param/detail")
    public RestResultDto paramList(@RequestParam @NotNull String code) {
        return RestResultDto.success(paramService.getParamByCode(code));
    }


    /**
     * 获取所有参数
     *
     * @return 参数信息
     */
    @RequestMapping(value = "/param/list")
    public RestResultDto paramList(@RequestParam @NotNull ParamType paramType) {
        return RestResultDto.success(paramService.getParamListByType(paramType));
    }

    /**
     * 查询参数列表
     *
     * @return 参数信息
     */
    @RequestMapping(value = "/param/page")
    public RestResultDto paramPage(ParamQueryDto paramQueryDto, Pageable pageable) {
        return RestResultDto.success(paramService.findParamPage(paramQueryDto, pageable));
    }

    /**
     * 新增参数
     *
     * @param bkParamDto    参数信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/param/add")
    public RestResultDto paramAdd(@Validated(BkParamDtoValidator.Add.class) BkParamDtoValidator bkParamDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkParamDto, BkParamDtoValidator.Add.class);
        paramService.saveParam(BeanUtilPlus.copyAs(bkParamDto, BkParamDto.class));

        return RestResultDto.success();
    }

    /**
     * 修改参数初始化
     *
     * @param id 参数id
     * @return 参数信息
     */
    @RequestMapping(value = "/param/editInit")
    public RestResultDto paramEditInit(@RequestParam @NotNull Long id) throws BusinessException {
        return RestResultDto.success(paramService.getParamById(id));
    }

    /**
     * 修改参数
     *
     * @param bkParamDto    参数信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/param/edit")
    public RestResultDto paramEdit(@Validated(BkParamDtoValidator.Edit.class) BkParamDtoValidator bkParamDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkParamDto, BkParamDtoValidator.Edit.class);
        paramService.updateParam(BeanUtilPlus.copyAs(bkParamDto, BkParamDto.class));

        return RestResultDto.success();
    }
}

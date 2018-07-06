package cn.jzyunqi.ms.uaa.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkPrivilegeDto;
import cn.jzyunqi.ms.uaa.service.PrivilegeService;
import cn.jzyunqi.ms.uaa.web.validator.BkPrivilegeDtoValidator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wiiyaya
 * @date 2018/5/17.
 */
@RestController
@Validated
public class PrivilegeController extends AdminRestBaseController {

    @Resource
    private PrivilegeService privilegeService;

    /**
     * 获取权限列表
     *
     * @return 权限列表
     */
    @RequestMapping(value = "/privilege/list")
    public RestResultDto privilegeList() {
        return RestResultDto.success(privilegeService.getAllPrivilegeList());
    }


    /**
     * 权限新增
     *
     * @param bkPrivilegeDto 权限信息
     * @param bindingResult  校验信息
     * @return 成功
     */
    @RequestMapping(value = "/privilege/add")
    public RestResultDto privilegeAdd(@Validated(BkPrivilegeDtoValidator.Add.class) BkPrivilegeDtoValidator bkPrivilegeDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkPrivilegeDto, BkPrivilegeDtoValidator.Add.class);
        return RestResultDto.success(privilegeService.savePrivilege(BeanUtilPlus.copyAs(bkPrivilegeDto, BkPrivilegeDto.class)));
    }
}

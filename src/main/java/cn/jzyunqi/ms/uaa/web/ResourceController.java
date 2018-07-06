package cn.jzyunqi.ms.uaa.web;

import cn.jzyunqi.AdminRestBaseController;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.ms.uaa.common.dto.PrivilegeDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkResourceDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkResourceQueryDto;
import cn.jzyunqi.ms.uaa.service.PrivilegeService;
import cn.jzyunqi.ms.uaa.service.ResourceService;
import cn.jzyunqi.ms.uaa.web.validator.BkResourceDtoValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2018/5/17.
 */
@RestController
@Validated
public class ResourceController extends AdminRestBaseController {

    @Resource
    private ResourceService resourceService;

    @Resource
    private PrivilegeService privilegeService;

    /**
     * 获取资源树结构
     *
     * @return 资源树
     */
    @RequestMapping(value = "/resource/tree")
    public RestResultDto resourceTree(@RequestParam(required = false) Long parentId) {
        if (parentId == null) {
            return RestResultDto.success(resourceService.getMainResourceTree());
        } else {
            return RestResultDto.success(resourceService.getChildResourceTree(parentId));
        }
    }

    /**
     * 资源查询
     *
     * @return 资源信息
     */
    @RequestMapping(value = "/resource/page")
    public RestResultDto resourceTree(BkResourceQueryDto bkResourceQueryDto, Pageable pageable) {
        return RestResultDto.success(resourceService.queryByPageable(bkResourceQueryDto, pageable));
    }

    /**
     * 资源新增
     *
     * @param bkResourceDto 资源信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/resource/add")
    public RestResultDto resourceAdd(@Validated(BkResourceDtoValidator.Add.class) BkResourceDtoValidator bkResourceDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkResourceDto, BkResourceDtoValidator.Add.class);
        resourceService.saveResource(BeanUtilPlus.copyAs(bkResourceDto, BkResourceDto.class));

        return RestResultDto.success();
    }

    /**
     * 资源修改初始化
     *
     * @param id 资源id
     * @return 资源信息
     */
    @RequestMapping(value = "/resource/editInit")
    public RestResultDto resourceEditInit(@RequestParam @NotNull Long id) throws BusinessException {
        return RestResultDto.success(resourceService.getResourceById(id));
    }

    /**
     * 修改资源
     *
     * @param bkResourceDto 资源信息
     * @param bindingResult 校验信息
     * @return 成功
     */
    @RequestMapping(value = "/resource/edit")
    public RestResultDto resourceEdit(@Validated(BkResourceDtoValidator.Edit.class) BkResourceDtoValidator bkResourceDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, bkResourceDto, BkResourceDtoValidator.Edit.class);
        resourceService.updateResource(BeanUtilPlus.copyAs(bkResourceDto, BkResourceDto.class));

        return RestResultDto.success();
    }

    /**
     * 资源权限修改初始化(1. 获取资源基本信息)
     *
     * @param id 资源id
     * @return 资源信息
     */
    @RequestMapping(value = "/resource/privilege/editInit")
    public RestResultDto resourcePrivilegeEditInit(@RequestParam @NotNull Long id) throws BusinessException {
        BkResourceDto bkResourceDto = resourceService.getResourceById(id);
        List<PrivilegeDto> privilegeList = privilegeService.getResourcePrivilege(id);
        List<PrivilegeDto> otherList = privilegeService.getOtherPrivilege(id);

        Map<String, Object> mixObject = new HashMap<>();
        mixObject.put("resourceInfo", bkResourceDto);
        mixObject.put("privilegeList", privilegeList);
        mixObject.put("otherList", otherList);

        return RestResultDto.success(mixObject);
    }

    /**
     * 资源权限修改
     *
     * @param id 资源id
     * @return 资源信息
     */
    @RequestMapping(value = "/resource/privilege/edit")
    public RestResultDto resourcePrivilegeEditInit(@RequestParam @NotNull Long id, @RequestParam List<Long> privilegeIds) throws BusinessException {
        resourceService.updateResourceAuth(id, privilegeIds);
        return RestResultDto.success();
    }
}

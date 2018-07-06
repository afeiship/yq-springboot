package cn.jzyunqi.ms.uaa.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.model.TreeDto;
import cn.jzyunqi.common.persistence.dao.tools.JF;
import cn.jzyunqi.common.persistence.domain.BaseDomain;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.common.utils.BooleanUtilPlus;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.uaa.common.constant.UaaMessageConstant;
import cn.jzyunqi.ms.uaa.common.dto.ResourceDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkResourceDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkResourceQueryDto;
import cn.jzyunqi.ms.uaa.common.enums.PermitType;
import cn.jzyunqi.ms.uaa.common.enums.ResourceType;
import cn.jzyunqi.ms.uaa.domain.Resource;
import cn.jzyunqi.ms.uaa.domain.ResourcePrivilege;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.PrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.ResourceDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.ResourcePrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.querydsl.ResourceQry;
import cn.jzyunqi.ms.uaa.service.ResourceService;
import com.querydsl.jpa.JPQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @javax.annotation.Resource
    private ResourceDao resourceDao;

    @javax.annotation.Resource
    private PrivilegeDao privilegeDao;

    @javax.annotation.Resource
    private ResourcePrivilegeDao resourcePrivilegeDao;

    @Override
    public String[] getNoNeedAuthResources() {
        List<String> resources = resourceDao.findNoNeedAuthResources();
        return resources.toArray(new String[resources.size()]);
    }

    @Override
    public Map<String, String[]> getNeedAuthResources() {
        List<Resource> resources = resourceDao.findNeedAuthResources();

        Map<String, String[]> resourcePrivilegeMap = new HashMap<>();
        for (Resource resource : resources) {
            List<Long> privilegeIdDbs = resourcePrivilegeDao.findPrivilegeIds(resource.getId());//查找资源对应的权限id
            if (CollectionUtilPlus.isEmpty(privilegeIdDbs)) {
                LOG.warn("Resource [" + resource.getId() + "] no privilege has been found!");
                continue;
            }
            List<String> privilegeCodes = privilegeDao.findCodes(privilegeIdDbs);//查找权限代码
            resourcePrivilegeMap.put(resource.getUrl(), privilegeCodes.toArray(new String[privilegeCodes.size()]));
        }
        return resourcePrivilegeMap;
    }

    @Override
    public List<ResourceDto> getUserAuthoritiesMainMenu(List<Long> privilegeIds) throws BusinessException {
        if (CollectionUtilPlus.isEmpty(privilegeIds)) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_ANY_AUTH);
        }
        List<Long> resourceIdDbs = resourcePrivilegeDao.findResourceIds(privilegeIds);//查找权限对应的资源id
        if (CollectionUtilPlus.isEmpty(resourceIdDbs)) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_ANY_AUTH);
        }
        List<Resource> menuList = resourceDao.findMainMenus(resourceIdDbs);

        List<ResourceDto> mainNodes = new ArrayList<>();
        for (Resource resource : menuList) {
            ResourceDto dto = BeanUtilPlus.copyAs(resource, ResourceDto.class);
            mainNodes.add(dto);
        }
        return mainNodes;
    }

    @Override
    public List<ResourceDto> getUserAuthoritiesChildMenu(List<Long> privilegeIds, Long parentId) throws BusinessException {
        if (CollectionUtilPlus.isEmpty(privilegeIds)) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_ANY_AUTH);
        }
        List<Long> resourceIdDbs = resourcePrivilegeDao.findResourceIds(privilegeIds);//查找权限对应的资源id
        if (CollectionUtilPlus.isEmpty(resourceIdDbs)) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_ANY_AUTH);
        }

        List<Resource> menuList = resourceDao.findChildMenus(resourceIdDbs, parentId);

        List<ResourceDto> childNodes = new ArrayList<>();
        for (Resource resource : menuList) {
            ResourceDto dto = BeanUtilPlus.copyAs(resource, ResourceDto.class);
            childNodes.add(dto);
        }
        return childNodes;
    }

    @Override
    public PageDto<BkResourceDto> queryByPageable(BkResourceQueryDto bkResourceQueryDto, Pageable pageRequest) {
        Page<Resource> resourceDbs = resourceDao.findAllJF(new JF() {
            @Override
            public <T> void prepareQry(JPQLQuery<T> qry, boolean notCountQry) {
                ResourceQry.searchResource(qry, notCountQry, bkResourceQueryDto);
            }
        }, pageRequest);

        List<BkResourceDto> resourceDtoList = resourceDbs.stream().map(resource -> {
            BkResourceDto bkResourceDto = new BkResourceDto();
            bkResourceDto.setId(resource.getId());
            bkResourceDto.setName(resource.getName());
            bkResourceDto.setUrl(resource.getUrl());
            bkResourceDto.setMsClientId(resource.getMsClientId());
            bkResourceDto.setType(resource.getType());
            bkResourceDto.setPermitType(resource.getPermitType());
            bkResourceDto.setRoot(resource.getRoot());
            bkResourceDto.setLeaf(resource.getLeaf());
            bkResourceDto.setPriority(resource.getPriority());

            if (resource.getParentId() != null) {
                Resource parent = resourceDao.findById(resource.getParentId()).orElseGet(Resource::new);
                BkResourceDto bkParent = new BkResourceDto();
                bkParent.setId(parent.getId());
                bkParent.setName(parent.getName());
                bkResourceDto.setParent(bkParent);
            }

            List<String> privilegeNames = privilegeDao.findNamesByResourceId(resource.getId());//查找权限名称
            bkResourceDto.setPrivilegeNames(StringUtilPlus.join(privilegeNames, StringUtilPlus.SPACE));

            return bkResourceDto;
        }).collect(Collectors.toList());
        return new PageDto<>(resourceDtoList, resourceDbs.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void saveResource(BkResourceDto bkResourceDto) throws BusinessException {
        if (resourceDao.countUrl(bkResourceDto.getUrl()) > 0) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_PATH_EXIST);
        }

        Resource resource = new Resource();

        resource.setMsClientId(bkResourceDto.getMsClientId());
        resource.setType(bkResourceDto.getType());
        resource.setName(bkResourceDto.getName());
        resource.setUrl(bkResourceDto.getUrl());
        resource.setRoot(bkResourceDto.getRoot());
        resource.setParentId(bkResourceDto.getParentId());
        resource.setPriority(bkResourceDto.getPriority());
        resource.setLeaf(bkResourceDto.getLeaf());
        resource.setPermitType(bkResourceDto.getPermitType());

        if (resource.getType() == ResourceType.M) {//如果是菜单
            checkAndSetMenuParent(resource, bkResourceDto);
        }

        resourceDao.save(resource);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateResource(BkResourceDto bkResourceDto) throws BusinessException {
        if (resourceDao.countUrlWithoutId(bkResourceDto.getUrl(), bkResourceDto.getId()) > 0) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_PATH_EXIST);
        }

        Optional<Resource> optionalResource = resourceDao.findById(bkResourceDto.getId());
        if (!optionalResource.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_FOUND);
        }
        Resource resource = optionalResource.get();
        resource.setMsClientId(bkResourceDto.getMsClientId());
        resource.setType(bkResourceDto.getType());
        resource.setName(bkResourceDto.getName());
        resource.setUrl(bkResourceDto.getUrl());
        resource.setRoot(bkResourceDto.getRoot());
        resource.setParentId(bkResourceDto.getParentId());
        resource.setPriority(bkResourceDto.getPriority());
        resource.setLeaf(bkResourceDto.getLeaf());
        resource.setPermitType(bkResourceDto.getPermitType());

        if (bkResourceDto.getType() == ResourceType.M) {//如果是目录，才可以设置以下属性
            checkAndSetMenuParent(resource, bkResourceDto);
        } else {
            resource.setLeaf(null);
            resource.setPriority(null);
            resource.setParentId(null);
        }
        resourceDao.save(resource);
    }

    @Override
    public BkResourceDto getResourceById(Long resourceId) throws BusinessException {
        Optional<Resource> optionalResource = resourceDao.findById(resourceId);
        if (!optionalResource.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_FOUND);
        }
        Resource resource = optionalResource.get();

        BkResourceDto bkResourceDto = new BkResourceDto();
        bkResourceDto.setId(resource.getId());
        bkResourceDto.setMsClientId(resource.getMsClientId());
        bkResourceDto.setType(resource.getType());
        bkResourceDto.setName(resource.getName());
        bkResourceDto.setUrl(resource.getUrl());
        bkResourceDto.setRoot(resource.getRoot());
        bkResourceDto.setParentId(resource.getParentId());
        bkResourceDto.setPriority(resource.getPriority());
        bkResourceDto.setLeaf(resource.getLeaf());
        bkResourceDto.setPermitType(resource.getPermitType());

        return bkResourceDto;
    }

    @Override
    public List<TreeDto> getMainResourceTree() {
        List<TreeDto> mainMenuTreeDto = new ArrayList<>();
        List<Resource> resourceList = resourceDao.findMainResource();

        for (Resource resource : resourceList) {
            mainMenuTreeDto.add(prepareTreeNode(resource));
        }
        return mainMenuTreeDto;
    }

    @Override
    public List<TreeDto> getChildResourceTree(Long parentId) {
        Resource parent = resourceDao.findById(parentId).get();
        List<TreeDto> childMenuTreeDto = new ArrayList<>();
        List<Resource> resourceList = null;
        switch (parent.getType()) {
            case M:
                if (!parent.getLeaf()) {
                    resourceList = resourceDao.findChildResource(parentId);
                }
                break;
            default:
                break;
        }
        if (resourceList != null) {
            for (Resource resource : resourceList) {
                childMenuTreeDto.add(prepareTreeNode(resource));
            }
        }
        return childMenuTreeDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateResourceAuth(Long resourceId, List<Long> privilegeIds) throws BusinessException {
        Optional<Resource> optionalResource = resourceDao.findById(resourceId);
        if (!optionalResource.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_FOUND);
        }
        Resource resource = optionalResource.get();
        if (resource.getPermitType() != PermitType.A) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_CANT_SET_AUTH);
        }
        if (resource.getType() == ResourceType.M && !BooleanUtilPlus.isTrue(resource.getLeaf())) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_CANT_SET_AUTH);
        }

        List<ResourcePrivilege> oldPrivilegeList = resourcePrivilegeDao.findByResourceId(resource.getId());
        //删除全部旧的权限
        resourcePrivilegeDao.deleteAll(oldPrivilegeList);
        //增加新的权限
        List<ResourcePrivilege> newPrivilegeList = privilegeIds.stream().map(privilegeId -> {
            ResourcePrivilege resourcePrivilege = new ResourcePrivilege();
            resourcePrivilege.setResourceId(resource.getId());
            resourcePrivilege.setPrivilegeId(privilegeId);
            return resourcePrivilege;
        }).collect(Collectors.toList());
        resourcePrivilegeDao.saveAll(newPrivilegeList);

        //如果是叶子菜单，则需要处理父菜单权限
        if (resource.getType() == ResourceType.M && BooleanUtilPlus.isTrue(resource.getLeaf())) {
            //父目录重新设置所有子目录权限
            List<ResourcePrivilege> parentOldPrivilegeList = resourcePrivilegeDao.findByResourceId(resource.getParentId());
            //删除全部旧的权限
            resourcePrivilegeDao.deleteAll(parentOldPrivilegeList);

            List<Resource> childList = resourceDao.findChildResource(resource.getParentId());
            List<Long> childResourceIdList = childList.stream().map(BaseDomain::getId).collect(Collectors.toList());
            List<Long> childPrivilegeIdList = resourcePrivilegeDao.findPrivilegeIds(childResourceIdList);
            //父目录增加新的权限
            List<ResourcePrivilege> parentNewPrivilegeList = childPrivilegeIdList.stream().map(privilegeId -> {
                ResourcePrivilege resourcePrivilege = new ResourcePrivilege();
                resourcePrivilege.setResourceId(resource.getParentId());
                resourcePrivilege.setPrivilegeId(privilegeId);
                return resourcePrivilege;
            }).collect(Collectors.toList());
            resourcePrivilegeDao.saveAll(parentNewPrivilegeList);
        }
    }

    private void checkAndSetMenuParent(Resource resource, ResourceDto resourceDto) throws BusinessException {
        if (resourceDto.getPermitType() != PermitType.A) {
            throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_MENU_NEED_AUTH);
        }
        if (resourceDto.getRoot() == null || !resourceDto.getRoot()) {
            if (resourceDto.getParentId() == null) {
                throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_PARENT_NOT_FOUND);
            }
            Optional<Resource> optionalResource = resourceDao.findById(resourceDto.getParentId());
            if (!optionalResource.isPresent()) {
                throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_NOT_FOUND);
            }
            Resource parent = optionalResource.get();
            if (parent.getLeaf()) {
                throw new BusinessException(UaaMessageConstant.ERROR_RESOURCE_MENU_PARENT_CANT_BE_LEAF);
            }
            resource.setRoot(Boolean.FALSE);
            resource.setParentId(parent.getId());
        } else {
            resource.setRoot(Boolean.TRUE);
            resource.setParentId(null);
        }
    }

    private TreeDto prepareTreeNode(Resource resource) {

        TreeDto treeDtoNode = new TreeDto();
        treeDtoNode.setId(resource.getId().toString());
        treeDtoNode.setParent(StringUtilPlus.defaultString(String.valueOf(resource.getParentId()), "0"));
        treeDtoNode.setText(resource.getName());
        switch (resource.getType()) {
            case M:
                if (!resource.getLeaf()) {
                    treeDtoNode.setState(TreeDto.State.closed);//如果不是叶子节点，一定还有子目录
                }
                break;
            case H:
                treeDtoNode.setState(TreeDto.State.open);
                break;
            default:
                treeDtoNode.setState(TreeDto.State.open);//如果是按钮，一定没有其他内容了
                break;
        }

        treeDtoNode.setChecked(Boolean.FALSE);

        return treeDtoNode;
    }
}

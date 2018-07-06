package cn.jzyunqi.ms.uaa.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.ms.uaa.common.constant.UaaMessageConstant;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.PrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.ResourcePrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.RolePrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.Privilege;
import cn.jzyunqi.ms.uaa.common.dto.PrivilegeDto;
import cn.jzyunqi.ms.uaa.service.PrivilegeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

    @Resource
    private PrivilegeDao privilegeDao;

    @Resource
    private ResourcePrivilegeDao resourcePrivilegeDao;

    @Resource
    private RolePrivilegeDao rolePrivilegeDao;

    @Override
    public List<PrivilegeDto> getAllPrivilegeList() {
        List<Privilege> privilegeList = privilegeDao.findAll();

        List<PrivilegeDto> privilegeDtoList = new ArrayList<>();
        for (Privilege privilege : privilegeList) {
            PrivilegeDto privilegeDto = new PrivilegeDto();
            privilegeDto.setId(privilege.getId());
            privilegeDto.setName(privilege.getName());
            privilegeDtoList.add(privilegeDto);
        }
        return privilegeDtoList;
    }

    @Override
    public List<PrivilegeDto> getResourcePrivilege(Long resourceId) {
        List<Long> privilegeIdDbs = resourcePrivilegeDao.findPrivilegeIds(resourceId);//查找资源对应的权限id
        List<Privilege> privilegeList = null;
        List<PrivilegeDto> privilegeDtoList = new ArrayList<>();
        if (CollectionUtilPlus.isNotEmpty(privilegeIdDbs)) {
            privilegeList = privilegeDao.findAllById(privilegeIdDbs);//查找权限
            for (Privilege privilege : privilegeList) {
                PrivilegeDto privilegeDto = new PrivilegeDto();
                privilegeDto.setCode(privilege.getId().toString());
                privilegeDto.setName(privilege.getName());
                privilegeDtoList.add(privilegeDto);
            }
        }
        return privilegeDtoList;
    }

    @Override
    public List<PrivilegeDto> getOtherPrivilege(Long resourceId) {
        List<Long> privilegeIdDbs = resourcePrivilegeDao.findPrivilegeIds(resourceId);//查找资源对应的权限id
        List<Privilege> privilegeList;
        if (CollectionUtilPlus.isNotEmpty(privilegeIdDbs)) {
            privilegeList = privilegeDao.findAllNotIn(privilegeIdDbs);//查找除了指定id的权限
        } else {
            privilegeList = privilegeDao.findAll();//查找全部的权限
        }
        List<PrivilegeDto> privilegeDtoList = new ArrayList<>();
        for (Privilege privilege : privilegeList) {
            PrivilegeDto privilegeDto = new PrivilegeDto();
            privilegeDto.setCode(privilege.getId().toString());
            privilegeDto.setName(privilege.getName());
            privilegeDtoList.add(privilegeDto);
        }

        return privilegeDtoList;
    }

    @Override
    public List<PrivilegeDto> getPrivilegesByIdList(List<Long> privilegeIdList) {
        List<PrivilegeDto> privilegeDtoList = new ArrayList<>();

        List<Privilege> privilegeDbs = privilegeDao.findAllById(privilegeIdList);
        for (Privilege privilege : privilegeDbs) {
            PrivilegeDto privilegeDto = BeanUtilPlus.copyAs(privilege, PrivilegeDto.class);
            privilegeDtoList.add(privilegeDto);
        }
        return privilegeDtoList;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public PrivilegeDto savePrivilege(PrivilegeDto privilegeDto) throws BusinessException {
        if (privilegeDao.countCode(privilegeDto.getCode()) > 0) {
            throw new BusinessException(UaaMessageConstant.ERROR_PRIVILEGE_CODE_EXIST);
        }
        Privilege privilege = new Privilege();
        privilege.setCode(privilegeDto.getCode());
        privilege.setName(privilegeDto.getName());

        privilegeDao.save(privilege);

        privilegeDto.setId(privilege.getId());
        return privilegeDto;
    }

    @Override
    public List<Long> getRolePrivilegeIds(Long roleId) {
        return rolePrivilegeDao.findPrivilegeIdList(roleId);//查找权限id
    }

    @Override
    public List<Long> getRolePrivilegeIdsByRoleIdList(List<Long> roleIdList) {
        return rolePrivilegeDao.findPrivilegeIdList(roleIdList);//查找权限id
    }
}

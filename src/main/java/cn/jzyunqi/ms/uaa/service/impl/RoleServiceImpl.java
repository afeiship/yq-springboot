package cn.jzyunqi.ms.uaa.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.persistence.dao.tools.JF;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.uaa.common.constant.UaaMessageConstant;
import cn.jzyunqi.ms.uaa.common.dto.RoleDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.BkRoleDto;
import cn.jzyunqi.ms.uaa.common.dto.backend.query.BkRoleQueryDto;
import cn.jzyunqi.ms.uaa.domain.Role;
import cn.jzyunqi.ms.uaa.domain.RolePrivilege;
import cn.jzyunqi.ms.uaa.domain.UserRole;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.PrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.RoleDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.RolePrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.UserRoleDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.querydsl.RoleQry;
import cn.jzyunqi.ms.uaa.service.RoleService;
import com.querydsl.jpa.JPQLQuery;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RolePrivilegeDao rolePrivilegeDao;

    @Resource
    private PrivilegeDao privilegeDao;

    @Override
    public PageDto<BkRoleDto> queryByPageable(BkRoleQueryDto bkRoleQueryDto, Pageable pageable) {
        List<BkRoleDto> rstList = new ArrayList<>();
        Page<Role> roleDbs = roleDao.findAllJF(new JF() {
            @Override
            public <T> void prepareQry(JPQLQuery<T> qry, boolean notCountQry) {
                RoleQry.searchRole(qry, notCountQry, bkRoleQueryDto);
            }
        }, pageable);
        for (Role roleDb : roleDbs) {
            BkRoleDto bkRoleDto = new BkRoleDto();
            bkRoleDto.setId(roleDb.getId());
            bkRoleDto.setCode(roleDb.getCode());
            bkRoleDto.setName(roleDb.getName());

            List<String> privilegeNames = privilegeDao.findNamesByRoleId(roleDb.getId());//查找权限名称
            bkRoleDto.setPrivilegeNames(StringUtilPlus.join(privilegeNames, StringUtilPlus.SPACE));
            rstList.add(bkRoleDto);
        }
        return new PageDto<>(rstList, roleDbs.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long saveRole(BkRoleDto bkRoleDto) throws BusinessException {
        Role roleDb = new Role();
        roleDb.setName(bkRoleDto.getName());
        roleDb.setCode(bkRoleDto.getCode());
        roleDao.save(roleDb);

        Long roleId = roleDb.getId();
        //保存角色权限关系
        List<RolePrivilege> rolePrivilegeList = new ArrayList<>();
        for (Long privilegeId : bkRoleDto.getPrivilegeIdList()) {
            RolePrivilege rolePrivilege = new RolePrivilege();
            rolePrivilege.setRoleId(roleId);
            rolePrivilege.setPrivilegeId(privilegeId);
            rolePrivilegeList.add(rolePrivilege);
        }
        rolePrivilegeDao.saveAll(rolePrivilegeList);

        return roleId;
    }

    @Override
    public BkRoleDto bkGetRoleById(Long roleId) throws BusinessException {
        Optional<Role> optionalRoleDb = roleDao.findById(roleId);
        if (!optionalRoleDb.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_ROLE_NOT_FOUND);
        }
        Role role = optionalRoleDb.get();
        BkRoleDto bkRoleDto = new BkRoleDto();
        bkRoleDto.setId(role.getId());
        bkRoleDto.setCode(role.getCode());
        bkRoleDto.setName(role.getName());

        //获取角色权限id
        List<Long> privilegeIdList = rolePrivilegeDao.findPrivilegeIdList(bkRoleDto.getId());
        bkRoleDto.setPrivilegeIdList(privilegeIdList);
        return bkRoleDto;
    }

    @Override
    public void addUserRoles(Long userId, List<Long> roleIdList) {
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIdList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        userRoleDao.saveAll(userRoles);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateRole(BkRoleDto bkRoleDto) throws BusinessException {
        Optional<Role> optionalRoleDb = roleDao.findById(bkRoleDto.getId());
        if (!optionalRoleDb.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_ROLE_NOT_FOUND);
        }

        Role roleDb = optionalRoleDb.get();
        roleDb.setName(bkRoleDto.getName());
        roleDao.save(roleDb);

        //获取角色权限列表
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.findByRoleId(roleDb.getId());

        //先删除页面上没选择的权限
        List<RolePrivilege> needDeletes = new ArrayList<>();
        for (RolePrivilege rolePrivilegeDb : rolePrivilegeList) {
            boolean existInPage = IterableUtils.matchesAny(bkRoleDto.getPrivilegeIdList(), new Predicate<Long>() {
                @Override
                public boolean evaluate(Long pagePrivilegeId) {
                    return rolePrivilegeDb.getPrivilegeId().equals(pagePrivilegeId);
                }
            });
            if (!existInPage) {
                needDeletes.add(rolePrivilegeDb);
            }
        }
        if (CollectionUtilPlus.isNotEmpty(needDeletes)) {
            rolePrivilegeDao.deleteAll(needDeletes);
        }
        //再增加页面上的数据
        List<RolePrivilege> needAdds = new ArrayList<>();
        for (Long pagePrivilegeId : bkRoleDto.getPrivilegeIdList()) {
            boolean existInDb = IterableUtils.matchesAny(rolePrivilegeList, new Predicate<RolePrivilege>() {
                @Override
                public boolean evaluate(RolePrivilege rolePrivilegeDb) {
                    return pagePrivilegeId.equals(rolePrivilegeDb.getPrivilegeId());
                }
            });
            if (!existInDb) {
                RolePrivilege rolePrivilege = new RolePrivilege();
                rolePrivilege.setRoleId(roleDb.getId());
                rolePrivilege.setPrivilegeId(pagePrivilegeId);
                needAdds.add(rolePrivilege);
            }
        }
        if (CollectionUtilPlus.isNotEmpty(needAdds)) {
            rolePrivilegeDao.saveAll(needAdds);
        }
    }

    @Override
    public void updateUserRoles(Long userId, List<Long> roleIdList) {
        //获取用户角色关系
        List<UserRole> userRoleList = userRoleDao.findByUserId(userId);
        //删除所有角色
        userRoleDao.deleteAll(userRoleList);

        //再增加页面上的数据
        List<UserRole> needAdds = roleIdList.stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());
        userRoleDao.saveAll(needAdds);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void deleteRole(Long roleId) throws BusinessException {
        //先删除角色权限关系
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.findByRoleId(roleId);
        rolePrivilegeDao.deleteAll(rolePrivilegeList);
        //再删除角色
        roleDao.deleteById(roleId);
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        return userRoleDao.findRoleIdListByUserId(userId);//查找角色id
    }

    @Override
    public List<String> findNamesByUserId(Long userId) {
        return roleDao.findNamesByUserId(userId);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleList = roleDao.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Role role : roleList) {
            RoleDto roleDto = new BkRoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());

            roleDtoList.add(roleDto);
        }

        return roleDtoList;
    }
}

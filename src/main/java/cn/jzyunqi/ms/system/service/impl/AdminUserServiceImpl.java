package cn.jzyunqi.ms.system.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.helper.NoGenHelper;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.model.SnowflakeId;
import cn.jzyunqi.common.persistence.dao.tools.JF;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.system.common.dto.backend.BkAdminUserDto;
import cn.jzyunqi.ms.system.common.dto.backend.query.BkAdminUserQueryDto;
import cn.jzyunqi.ms.system.domain.AdminUser;
import cn.jzyunqi.ms.system.domain.dao.jpa.AdminUserDao;
import cn.jzyunqi.ms.system.domain.dao.jpa.querydsl.AdminUserQry;
import cn.jzyunqi.ms.system.service.AdminUserService;
import cn.jzyunqi.ms.uaa.common.constant.UaaMessageConstant;
import cn.jzyunqi.ms.uaa.common.dto.UserDto;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.service.RoleService;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import cn.jzyunqi.ms.uaa.service.UserService;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserDao adminUserDao;

    @Resource
    private NoGenHelper noGenHelper;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Override
    public PageDto<BkAdminUserDto> queryAdminUserPage(BkAdminUserQueryDto bkAdminUserQueryDto, Pageable pageable) {
        List<BkAdminUserDto> rstList = new ArrayList<>();
        Page<AdminUser> adminUserDbs = adminUserDao.findAllJF(new JF() {
            @Override
            public <T> void prepareQry(JPQLQuery<T> schQry, boolean notCountQry) {
                AdminUserQry.searchUser(schQry, notCountQry, bkAdminUserQueryDto);
            }
        }, pageable);

        for (AdminUser adminUserDb : adminUserDbs) {
            BkAdminUserDto bkAdminUserDto = new BkAdminUserDto();
            bkAdminUserDto.setId(adminUserDb.getId());
            bkAdminUserDto.setNickname(adminUserDb.getNickname());
            bkAdminUserDto.setCreateTime(adminUserDb.getCreateTime());

            Boolean enabled = userAuthService.isUserEnabled(adminUserDb.getId());
            bkAdminUserDto.setEnabled(enabled);

            List<String> roleNames = roleService.findNamesByUserId(adminUserDb.getId());
            bkAdminUserDto.setRoleNames(StringUtilPlus.join(roleNames, StringUtilPlus.SPACE));

            rstList.add(bkAdminUserDto);
        }
        return new PageDto<>(rstList, adminUserDbs.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void addAdminUser(BkAdminUserDto bkAdminUserDto) throws BusinessException {
        UserDto userDto = userAuthService.createUser(AuthType.NORMAL, bkAdminUserDto.getUsername(), bkAdminUserDto.getPassword());

        AdminUser adminUserDb = new AdminUser();
        adminUserDb.setId(userDto.getId());
        adminUserDb.setNickname(bkAdminUserDto.getNickname());
        adminUserDb.setEmail(bkAdminUserDto.getEmail());
        adminUserDb.setPhone(bkAdminUserDto.getPhone());
        adminUserDao.save(adminUserDb);

        //保存用户角色关系
        roleService.addUserRoles(userDto.getId(), bkAdminUserDto.getRoleIdList());
    }

    @Override
    public BkAdminUserDto bkGetUserById(Long userId) throws BusinessException {
        Optional<AdminUser> optionalAdminUserDb = adminUserDao.findById(userId);
        if (!optionalAdminUserDb.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_NOT_FOUND);
        }
        AdminUser adminUserDb = optionalAdminUserDb.get();
        BkAdminUserDto bkAdminUserDto = new BkAdminUserDto();
        bkAdminUserDto.setId(adminUserDb.getId());
        bkAdminUserDto.setNickname(adminUserDb.getNickname());

        String username = userAuthService.findUsernameByUserId(AuthType.NORMAL, adminUserDb.getId());
        bkAdminUserDto.setUsername(username);

        //获取用户角色
        List<Long> userRoleIds = roleService.getUserRoleIds(bkAdminUserDto.getId());
        bkAdminUserDto.setRoleIdList(userRoleIds);
        return bkAdminUserDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void editAdminUser(BkAdminUserDto bkAdminUserDto) throws BusinessException {
        Optional<AdminUser> optionalAdminUserDb = adminUserDao.findById(bkAdminUserDto.getId());
        if (!optionalAdminUserDb.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_NOT_FOUND);
        }
        AdminUser adminUserDb = optionalAdminUserDb.get();

        if (CollectionUtilPlus.isEmpty(bkAdminUserDto.getRoleIdList())) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_MUST_HAS_ROLE);
        }
        //复制页面信息
        adminUserDb.setNickname(bkAdminUserDto.getNickname());
        adminUserDb.setEmail(bkAdminUserDto.getEmail());
        adminUserDb.setPhone(bkAdminUserDto.getPhone());

        adminUserDao.save(adminUserDb);

        //不允许修改超级管理员权限
        if (adminUserDb.getId() > 1L) {
            roleService.updateUserRoles(adminUserDb.getId(), bkAdminUserDto.getRoleIdList());
        }
    }

    @Override
    public BkAdminUserDto getUserProfile(Long userId) throws BusinessException {
        Optional<AdminUser> optionalAdminUser = adminUserDao.findById(userId);
        if (!optionalAdminUser.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_NOT_FOUND);
        }
        AdminUser adminUser = optionalAdminUser.get();
        BkAdminUserDto bkAdminUserDto = new BkAdminUserDto();
        bkAdminUserDto.setNickname(adminUser.getNickname());
        return bkAdminUserDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateUserProfile(BkAdminUserDto adminUserDto) throws BusinessException {
        Optional<AdminUser> optionalAdminUser = adminUserDao.findById(adminUserDto.getId());
        if (!optionalAdminUser.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_NOT_FOUND);
        }
        AdminUser adminUser = optionalAdminUser.get();
        adminUser.setNickname(adminUserDto.getNickname());
        adminUser.setEmail(adminUserDto.getEmail());
        adminUser.setPhone(adminUserDto.getPhone());

        adminUserDao.save(adminUser);
    }

    @Override
    public BkAdminUserDto login(String username, String password) throws BusinessException {
        //1. 读取用户信息
        Long userId = userAuthService.findIdByUsername(AuthType.NORMAL, username);
        if (userId == null) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_CERTIFICATE_FAILED);
        }
        //2. 检查是否管理员
        Optional<AdminUser> optionalAdminUser = adminUserDao.findById(userId);
        if (!optionalAdminUser.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_CERTIFICATE_FAILED);
        }
        //3. 生成token 返回
        BkAdminUserDto adminUserDto = new BkAdminUserDto();
        adminUserDto.setUid(userService.findUidById(userId));
        adminUserDto.setToken(userAuthService.oauth2Login(AuthType.NORMAL, username, password, true));
        return adminUserDto;
    }

    @Override
    public void logout(String username) {
        userAuthService.removeMemberToken(username);
    }

    @Override
    public LocalDateTime getSystemTime() {
        Optional<TemporalAccessor> temporalAccessor = dateTimeProvider.getNow();
        return temporalAccessor.map(LocalDateTime::from).orElseGet(LocalDateTime::now);
    }

    @Override
    public SnowflakeId snowflakeIdDecrypt(String snowflakeId) {
        return noGenHelper.snowflakeIdDecrypt(snowflakeId);
    }
}

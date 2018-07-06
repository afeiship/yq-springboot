package cn.jzyunqi.ms.uaa.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.helper.NoGenHelper;
import cn.jzyunqi.common.helper.RedisHelper;
import cn.jzyunqi.common.model.spring.AuthorityDto;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.support.spring.RedisTokenStoreJDK8;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.common.utils.BooleanUtilPlus;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.uaa.common.constant.UaaCache;
import cn.jzyunqi.ms.uaa.common.constant.UaaMessageConstant;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.PrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.RolePrivilegeDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.UserAuthDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.UserDao;
import cn.jzyunqi.ms.uaa.domain.dao.jpa.UserRoleDao;
import cn.jzyunqi.ms.uaa.domain.Privilege;
import cn.jzyunqi.ms.uaa.domain.User;
import cn.jzyunqi.ms.uaa.domain.UserAuth;
import cn.jzyunqi.ms.uaa.common.dto.UserDto;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.service.OAuth2ClientService;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {
    private static final Logger log = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    private String clientId = "uaa";

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private UserDao userDao;

    @Resource
    private RolePrivilegeDao rolePrivilegeDao;

    @Resource
    private PrivilegeDao privilegeDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisHelper redisHelper;

    @Resource
    private OAuth2ClientService oAuth2ClientService;

    @Resource
    private NoGenHelper noGenHelper;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    private AuthorizationServerTokenServices tokenServices;

    private TokenStore tokenStore;

    @PostConstruct
    public void init() {
        this.tokenStore = new RedisTokenStoreJDK8(redisConnectionFactory);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenEnhancer(new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String userType = authentication.getOAuth2Request().getRequestParameters().get("user_type");
                if ("adminUser".equals(userType)) {
                    ((DefaultOAuth2AccessToken) accessToken).setExpiration(new Date(System.currentTimeMillis() + (12 * 3600 * 1000)));//修改后台管理用户的token有效时间为12小时
                }
                return accessToken;
            }
        });
        tokenServices.setClientDetailsService(oAuth2ClientService);
        tokenServices.setTokenStore(this.tokenStore);
        this.tokenServices = tokenServices;
    }

    @Override
    public String oauth2Login(AuthType authType, String username, String password, boolean adminUser) throws BusinessException {
        //获取用户
        UserAuth userAuth = userAuthDao.findByAuthTypeAndUsername(authType, username);
        if (userAuth == null) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_CERTIFICATE_FAILED);
        }

        //判断密码
        if (StringUtilPlus.isEmpty(userAuth.getPassword()) || StringUtilPlus.isEmpty(password) || !passwordEncoder.matches(password, userAuth.getPassword())) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_CERTIFICATE_FAILED);
        }

        //判断禁用
        User user = userDao.findById(userAuth.getUserId()).get();
        if (BooleanUtilPlus.isFalse(user.getEnabled())) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_DISABLED);
        }

        List<AuthorityDto> privilegeList = new ArrayList<>();
        List<Long> privilegeIdList = new ArrayList<>();
        List<Long> roleIdList = userRoleDao.findRoleIdListByUserId(user.getId());//查找用户角色id
        if (CollectionUtilPlus.isNotEmpty(roleIdList)) {
            List<Long> privilegeIdDbs = rolePrivilegeDao.findPrivilegeIdList(roleIdList);//查找角色权限id
            if (CollectionUtilPlus.isNotEmpty(privilegeIdDbs)) {
                List<Privilege> privilegeDbs = privilegeDao.findAllById(privilegeIdDbs);//查找权限
                for (Privilege privilegeDb : privilegeDbs) {
                    AuthorityDto auth = new AuthorityDto();
                    auth.setPrivilege(privilegeDb.getCode());
                    privilegeList.add(auth);
                    privilegeIdList.add(privilegeDb.getId());
                }
            }
        }

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setId(user.getId());
        loginUserDto.setUsername(user.getUid());
        loginUserDto.setLoginUsername(userAuth.getUsername());
        loginUserDto.setAuthorities(privilegeList);
        loginUserDto.setAuthorityIds(privilegeIdList);
        loginUserDto.setEnabled(user.getEnabled());
        loginUserDto.setAccountNonExpired(true);
        loginUserDto.setAccountNonLocked(true);
        loginUserDto.setCredentialsNonExpired(true);

        //删除之前登录的token
        removeMemberToken(loginUserDto.getUsername());

        //读取本地客户端详情，并组装相关授权信息
        ClientDetails client = oAuth2ClientService.loadClientByClientId(clientId);
        HashMap<String, String> modifiable = new HashMap<>();
        modifiable.put("client_id", client.getClientId());
        modifiable.put("username", username);
        modifiable.put("grant_type", "password");
        if (adminUser) {
            modifiable.put("user_type", "adminUser");
        }
        OAuth2Request storedOAuth2Request = new OAuth2Request(modifiable, client.getClientId(), client.getAuthorities(), true, client.getScope(), client.getResourceIds(), null, null, null);

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(loginUserDto, null, loginUserDto.getAuthorities());
        result.setDetails(modifiable);

        OAuth2Authentication a = new OAuth2Authentication(storedOAuth2Request, result);
        OAuth2AccessToken oAuth2AccessToken = tokenServices.createAccessToken(a);

        log.info("======== oauth2 user login {}, token is {}", username, oAuth2AccessToken);
        return oAuth2AccessToken.getValue();
    }

    @Override
    public boolean isPasswordExpired(Long userId) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public UserDto createUser(AuthType authType, String username, String password) throws BusinessException {
        UserAuth existsUserAuth = userAuthDao.findByAuthTypeAndUsername(authType, username);
        if (existsUserAuth != null) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_USERNAME_EXISTS, authType.getDesc());
        }

        User user = new User();
        user.setUid(noGenHelper.generateUid());
        user.setEnabled(true); //是否有效
        userDao.save(user);

        //创建登陆账户
        this.createUserAuth(authType, user.getId(), username, password);

        return BeanUtilPlus.copyAs(user, UserDto.class);
    }

    @Override
    public String findUsernameByUserId(AuthType authType, Long userId) {
        UserAuth userAuth = userAuthDao.findByAuthTypeAndUserId(authType, userId);
        return userAuth.getUsername();
    }

    @Override
    public Long findIdByUsername(AuthType authType, String username) {
        UserAuth userAuth = userAuthDao.findByAuthTypeAndUsername(authType, username);
        if (userAuth == null) {
            return null;
        }
        return userAuth.getUserId();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void enableUser(Long userId) throws BusinessException {
        Optional<User> optionalUser = userDao.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_NOT_FOUND);
        }
        User user = optionalUser.get();
        if (user.getId() <= 1L) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_CANT_AMD);
        }
        user.setEnabled(true);
        userDao.save(user);

        cleanPasswordCount(user.getId());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void disableUserById(Long userId) throws BusinessException {
        Optional<User> optionalUser = userDao.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_NOT_FOUND);
        }
        User user = optionalUser.get();
        if (user.getId() <= 1L) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_CANT_AMD);
        }
        user.setEnabled(false);
        userDao.save(user);
    }

    //@Override
    //@Transactional(rollbackFor = BusinessException.class)
    //public void disableUserByUsername(String username) {
    //    UserAuth userAuth = userAuthDao.findByUsername(username);
    //    if (userAuth != null) {
    //        Long errorCount = increasePasswordCount(userAuth.getUserId());
    //        if (errorCount >= 5) {
    //            User user = userDao.findById(userAuth.getUserId()).get();
    //            user.setEnabled(false);
    //            userDao.save(user);
    //        }
    //    }
    //}

    //@Override
    //public void cleanPasswordCountByUsername(String username) {
    //    UserAuth userAuth = userAuthDao.findByUsername(username);
    //    cleanPasswordCount(userAuth.getUserId());
    //}

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void resetPassword(Long userId, Long currentUserId, String newPassword, String currentUserPassword) throws BusinessException {
        UserAuth currentUserAuth = userAuthDao.findByAuthTypeAndUserId(AuthType.NORMAL, currentUserId);
        //检查当前用户密码是否正确
        if (!passwordEncoder.matches(currentUserPassword, currentUserAuth.getPassword())) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_CURRENT_USER_PWD_ERROR);
        }

        UserAuth adminUserAuth = userAuthDao.findByAuthTypeAndUserId(AuthType.NORMAL, userId);
        adminUserAuth.setPassword(passwordEncoder.encode(newPassword));
        userAuthDao.save(adminUserAuth);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void changeMyPassword(Long currentUserId, String oldPassword, String newPassword) throws BusinessException {
        if (oldPassword.equals(newPassword)) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_PWD_CANT_LIKE_OLD);
        }

        //Pattern p = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d~!@#$%^&*-=_+/\\(\\)]{8,20}");
        //Matcher m = p.matcher(newPassword);
        //if (!m.matches()) {
        //    throw new BusinessException(UaaMessageConstant.ERROR_USER_PWD_NOT_MATCH_REGX);
        //}

        UserAuth userAuth = userAuthDao.findById(currentUserId).get();
        if (!passwordEncoder.matches(oldPassword, userAuth.getPassword())) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_OLD_PWD_NOT_MATCH);
        }
        userAuth.setPassword(passwordEncoder.encode(newPassword));
        userAuthDao.save(userAuth);
    }

    @Override
    public Boolean isUserEnabled(Long userId) {
        User user = userDao.findById(userId).get();
        return user.getEnabled();
    }

    @Override
    public void removeMemberToken(String username) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, username);
        if (CollectionUtilPlus.isNotEmpty(tokens)) {
            for (OAuth2AccessToken token : tokens) {
                OAuth2RefreshToken refTk = token.getRefreshToken();
                if (refTk != null) {
                    tokenStore.removeRefreshToken(refTk);
                }
                tokenStore.removeAccessToken(token);
            }
        }
        redisHelper.removeKey("uname_to_access:" + clientId + ":" + username);
    }

    @Override
    public boolean hasPassword(AuthType authType, Long userId) {
        UserAuth userAuth = userAuthDao.findByAuthTypeAndUserId(authType, userId);
        return StringUtilPlus.isNotBlank(userAuth.getPassword());
    }

    @Override
    public boolean hasAuthType(AuthType authType, Long userId) {
        UserAuth userAuth = userAuthDao.findByAuthTypeAndUserId(authType, userId);
        return userAuth != null;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long createAuth(AuthType authType, Long userId, String username, String password) throws BusinessException {
        //创建手新账号，如果指定账号已经存在，则将当前账号合并到已经存在的账号（已存在的账号必须没有当前账号类型）

        //1. 查找当前用户是否已经存在了指定类型的账号
        UserAuth existUserAuth = userAuthDao.findByAuthTypeAndUserId(authType, userId);
        if (existUserAuth != null) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_AUTH_TYPE_EXISTS);
        }

        //2. 查找指定类型的账号用户名是否已经存在
        UserAuth existUsername = userAuthDao.findByAuthTypeAndUsername(authType, username);
        if (existUsername == null) {
            //直接创建新的账号
            this.createUserAuth(authType, userId, username, password);
            return userId;
        } else {
            return existUsername.getUserId();
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void mergeAuth(Long sourceMemberId, Long destMemberId) throws BusinessException {
        //查找目标账号是否包含当指定号类型
        UserAuth destWeixinUserAuth = userAuthDao.findByAuthTypeAndUserId(AuthType.WX_OPEN, destMemberId);
        if (destWeixinUserAuth != null) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_AUTH_BIND_BY_OTHER);
        }

        //将来源账号合并到目标账号下
        UserAuth sourceWeixinUserAuth = userAuthDao.findByAuthTypeAndUserId(AuthType.WX_OPEN, sourceMemberId);
        sourceWeixinUserAuth.setUserId(destMemberId); //用户id
        userAuthDao.save(sourceWeixinUserAuth);

        //删除源用户
        userDao.deleteById(sourceMemberId);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void resetPassword(AuthType authType, String username, String password) throws BusinessException {
        UserAuth userAuth = userAuthDao.findByAuthTypeAndUsername(authType, username);

        if (userAuth == null) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_NOT_FOUND);
        }

        userAuth.setPassword(passwordEncoder.encode(password)); //密码
        userAuthDao.save(userAuth);
    }

    @Override
    public void resetUsername(AuthType authType, Long userId, String username) throws BusinessException {
        UserAuth existsUserAuth = userAuthDao.findByAuthTypeAndUsername(authType, username);
        //查找用户名类型是否已经存在
        if (existsUserAuth != null) {
            throw new BusinessException(UaaMessageConstant.ERROR_USER_AUTH_BIND_BY_OTHER);
        }

        UserAuth userAuth = userAuthDao.findByAuthTypeAndUserId(authType, userId);
        userAuth.setUsername(username); //用户名
        userAuthDao.save(userAuth);
    }

    /**
     * 创建一个新的账户
     *
     * @param authType 账户类型
     * @param userId   用户id
     * @param username 用户名
     * @param password 密码
     */
    private void createUserAuth(AuthType authType, Long userId, String username, String password) throws BusinessException {
        if(StringUtilPlus.isBlank(username)){
            throw new BusinessException(UaaMessageConstant.ERROR_USER_AUTH_USERNAME_EMPTY);
        }
        //创建登陆账户
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(userId); //用户id
        userAuth.setType(authType); //账号类型
        userAuth.setUsername(username); //用户名
        if (StringUtilPlus.isNotEmpty(password)) {
            userAuth.setPassword(passwordEncoder.encode(password));
        } else {
            userAuth.setPassword(null);
        }
        userAuth.setVerified(true); //是否已验证

        userAuthDao.save(userAuth);
    }

    /**
     * 清除密码错误次数
     *
     * @param userId 用户id
     */
    private void cleanPasswordCount(Long userId) {
        redisHelper.removeKey(UaaCache.UAA_ERROR_PASSWORD_COUNT_V, userId.toString());
    }

    /**
     * 增加密码错误计数
     *
     * @param userId 用户id
     * @return 错误次数
     */
    private Long increasePasswordCount(Long userId) {
        return redisHelper.vIncrement(UaaCache.UAA_ERROR_PASSWORD_COUNT_V, userId.toString(), 1L);
    }
}

package cn.jzyunqi.ms.uaa.service.impl;

import cn.jzyunqi.ms.uaa.domain.dao.jpa.UserDao;
import cn.jzyunqi.ms.uaa.domain.User;
import cn.jzyunqi.ms.uaa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shenhui
 * @date 2018-6-14.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

    @Override
    public String findUidById(Long userId) {
        User user = userDao.findById(userId).get();
        return user.getUid();
    }
}

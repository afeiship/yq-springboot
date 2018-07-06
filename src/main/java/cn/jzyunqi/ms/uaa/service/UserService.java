package cn.jzyunqi.ms.uaa.service;

/**
 * @author shenhui
 * @date 2018-6-14.
 */
public interface UserService {
    /**
     * 获取用户UID
     *
     * @param userId 用户id
     * @return UID
     */
    String findUidById(Long userId);
}

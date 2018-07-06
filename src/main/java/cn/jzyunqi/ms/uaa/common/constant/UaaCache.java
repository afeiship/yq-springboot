package cn.jzyunqi.ms.uaa.common.constant;

import cn.jzyunqi.common.helper.Cache;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiiyaya
 * @date 2018/5/22.
 */
@Getter
@AllArgsConstructor
public class UaaCache implements Cache {

    public static final UaaCache UAA_ERROR_PASSWORD_COUNT_V;

    static {
        UAA_ERROR_PASSWORD_COUNT_V = new UaaCache(CacheType.V, "UAA_ERROR_PASSWORD_COUNT_V:", 86400);
    }

    private CacheType type;

    private String Prefix;

    private long expiration;
}

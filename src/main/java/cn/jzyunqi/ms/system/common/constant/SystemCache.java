package cn.jzyunqi.ms.system.common.constant;

import cn.jzyunqi.common.helper.Cache;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiiyaya
 * @date 2018/5/22.
 */
@Getter
@AllArgsConstructor
public class SystemCache implements Cache {

    public static final SystemCache SYS_PARAM_V;
    public static final SystemCache SYS_I18N_MESSAGE_V;

    static {
        SYS_PARAM_V = new SystemCache(CacheType.V, "SYS_PARAM_V:", 3600);
        SYS_I18N_MESSAGE_V = new SystemCache(CacheType.V, "SYS_I18N_MESSAGE_V:", 3600);
    }

    private CacheType type;

    private String Prefix;

    private long expiration;
}

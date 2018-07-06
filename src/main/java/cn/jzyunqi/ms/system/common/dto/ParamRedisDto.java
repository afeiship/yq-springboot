package cn.jzyunqi.ms.system.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2018/5/22.
 */
@Getter
@Setter
public class ParamRedisDto implements Serializable {
    private static final long serialVersionUID = -4577095257100440602L;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 值
     */
    private LocalDateTime time;
}

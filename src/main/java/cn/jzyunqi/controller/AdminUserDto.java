package cn.jzyunqi.controller;

import cn.jzyunqi.common.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@Setter
public class AdminUserDto extends BaseDto<Long, Long> {
    private static final long serialVersionUID = 9148897545280054685L;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * email
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

}

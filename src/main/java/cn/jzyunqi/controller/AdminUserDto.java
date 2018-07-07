package cn.jzyunqi.controller;

import cn.jzyunqi.common.model.BaseDto;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

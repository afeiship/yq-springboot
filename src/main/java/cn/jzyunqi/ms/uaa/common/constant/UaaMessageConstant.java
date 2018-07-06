/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.jzyunqi.ms.uaa.common.constant;

/**
 * <p>类简述：常量类</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public class UaaMessageConstant {

    /**
     * 用户名或密码错误
     */
    public static final String ERROR_USER_CERTIFICATE_FAILED = "uaa_error_user_certificate_failed";

    /**
     * 尊敬的用户，您的账号由于违反平台使用规则已经被封停
     */
    public static final String ERROR_USER_DISABLED = "uaa_error_user_disabled";

    /**
     * 找不到指定用户
     */
    public static final String ERROR_USER_NOT_FOUND = "uaa_error_user_not_found";

    /**
     * 你已绑定过该类型账号
     */
    public static final String ERROR_USER_AUTH_TYPE_EXISTS = "uaa_error_user_auth_type_exists";

    /**
     * 已经被其他人绑定
     */
    public static final String ERROR_USER_AUTH_BIND_BY_OTHER = "uaa_error_user_auth_bind_by_other";

    /**
     * 账户名不能为空
     */
    public static final String ERROR_USER_AUTH_USERNAME_EMPTY = "uaa_error_user_auth_username_empty";

    /**
     * 不允许修改系统默认用户
     */
    public static final String ERROR_USER_CANT_AMD = "uaa_error_user_cant_amd";

    /**
     * {0}已经存在
     */
    public static final String ERROR_USER_USERNAME_EXISTS = "uaa_error_user_username_exists";

    /**
     * 手机号已存在
     */
    public static final String ERROR_USER_PHONE_EXISTS = "uaa_error_user_phone_exists";

    /**
     * 请至少选择一个角色
     */
    public static final String ERROR_USER_MUST_HAS_ROLE = "uaa_error_user_must_has_role";

    /**
     * 当前用户密码错误
     */
    public static final String ERROR_USER_CURRENT_USER_PWD_ERROR = "uaa_error_user_current_user_pwd_error";

    /**
     * 密码不符合规则
     */
    public static final String ERROR_USER_PWD_NOT_MATCH_REGX = "uaa_error_user_pwd_not_match_regx";

    /**
     * 新旧密码不能一样
     */
    public static final String ERROR_USER_PWD_CANT_LIKE_OLD = "uaa_error_user_pwd_cant_like_old";

    /**
     * 无法匹配旧密码
     */
    public static final String ERROR_USER_OLD_PWD_NOT_MATCH = "uaa_error_user_old_pwd_not_match";

    /**
     * 找不到指定角色
     */
    public static final String ERROR_ROLE_NOT_FOUND = "uaa_error_role_not_found";

    /**
     * 找不到指定资源
     */
    public static final String ERROR_RESOURCE_NOT_FOUND = "uaa_error_resource_not_found";

    /**
     * 资源路径已经存在
     */
    public static final String ERROR_RESOURCE_PATH_EXIST = "uaa_error_resource_path_exist";

    /**
     * 菜单资源需要设置权限
     */
    public static final String ERROR_RESOURCE_MENU_NEED_AUTH = "uaa_error_resource_menu_need_auth";

    /**
     * 找不到父节点
     */
    public static final String ERROR_RESOURCE_PARENT_NOT_FOUND = "uaa_error_resource_parent_not_found";

    /**
     * 菜单父节点不允许为叶子节点
     */
    public static final String ERROR_RESOURCE_MENU_PARENT_CANT_BE_LEAF = "uaa_error_resource_menu_parent_cant_be_leaf";

    /**
     * 权限代码已经存在
     */
    public static final String ERROR_PRIVILEGE_CODE_EXIST = "uaa_error_privilege_code_exist";

    /**
     * 无权访问任何目录
     */
    public static final String ERROR_RESOURCE_NOT_ANY_AUTH = "uaa_error_resource_not_any_auth";

    /**
     * 当前资源不允许设置权限
     */
    public static final String ERROR_RESOURCE_CANT_SET_AUTH = "uaa_error_resource_cant_set_auth";
}

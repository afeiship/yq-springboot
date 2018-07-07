package cn.jzyunqi.controller;

import cn.jzyunqi.common.support.SqlFilter;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.controller.BkAdminUserQueryDto;
import cn.jzyunqi.ms.system.domain.QAdminUser;
import com.querydsl.jpa.JPQLQuery;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class AdminUserQry {

    /**
     * 后台用户表
     */
    private static final QAdminUser ADMIN_USER = QAdminUser.adminUser;

    /**
     * 组装查询条件
     *
     * @param schQry              JPQLQuery
     * @param notCountQry         是否是查询数量 true：是， false：否
     * @param bkAdminUserQueryDto 查询条件
     */
    public static <T> void searchUser(JPQLQuery<T> schQry, boolean notCountQry, BkAdminUserQueryDto bkAdminUserQueryDto) {
        if (StringUtilPlus.isNotEmpty(bkAdminUserQueryDto.getNickname())) {
            String inputNickname = SqlFilter.filterForLike(bkAdminUserQueryDto.getNickname().toUpperCase());
            schQry.where(ADMIN_USER.nickname.upper().like(inputNickname, SqlFilter.DEFALT_ESCAPE_CHAR));
        }
        if (StringUtilPlus.isNotEmpty(bkAdminUserQueryDto.getEmail())) {
            schQry.where(ADMIN_USER.email.eq(bkAdminUserQueryDto.getEmail()));
        }
        if (StringUtilPlus.isNotEmpty(bkAdminUserQueryDto.getPhone())) {
            schQry.where(ADMIN_USER.phone.eq(bkAdminUserQueryDto.getPhone()));
        }
    }
}

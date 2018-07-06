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
package cn.jzyunqi.ms.uaa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>类简述：资源权限中间表</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
@Entity
@Table(name = "uaa_resource_privilege")
@IdClass(ResourcePrivilegePK.class)
public class ResourcePrivilege implements Serializable {
    private static final long serialVersionUID = 8014253187884572358L;

    /**
     * 资源id
     */
    private Long resourceId;

    /**
     * 授权的id
     */
    private Long privilegeId;

    @Id
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Id
    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }
}

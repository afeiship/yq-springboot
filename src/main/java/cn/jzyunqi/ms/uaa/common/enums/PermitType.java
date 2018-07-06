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
package cn.jzyunqi.ms.uaa.common.enums;

/**
 * <p>类简述：资源授权类型</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public enum PermitType {
    /**
     * 不需要授权
     */
    N("不需要授权"),

    /**
     * 需要授权
     */
    A("需要授权"),

    /**
     * 登陆即可访问
     */
    L("登陆即可访问"),;

    /**
     * 描述
     */
    private String desc;

    PermitType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

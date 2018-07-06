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
package cn.jzyunqi.ms.job.common.enums;

/**
 * <p>类简述：</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public enum DayType {
    /**
     * 每天
     */
    A("每天"),

    /**
     * 最后一天
     */
    L("最后一天"),

    /**
     * 每个工作日
     */
    W("每个工作日"),

    /**
     * 最后一天前的第X天
     */
    B("最后一天前的第X天"),

    /**
     * 选择指定日期
     */
    O("选择指定日期"),

    /**
     * 离X号最近的工作日
     */
    N("离X号最近的工作日"),

    /**
     * 选择指定星期
     */
    CW("选择指定星期"),

    /**
     * 最后一个星期X
     */
    LW("最后一个星期X"),

    /**
     * 第X周的星期Y
     */
    DW("第X周的星期Y"),;

    private String desc;

    DayType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

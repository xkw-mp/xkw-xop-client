/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client;

/**
 * AreaDto
 * XOP基础数据接口-行政区Dto
 *
 * @author LiuJibin
 */
public class AreaDto {

    private String id;
    private String name;
    private String level;
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override public String toString() {
        return "AreaDto{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", level='" + level + '\'' + ", parentId='"
            + parentId + '\'' + '}';
    }
}

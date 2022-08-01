/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client;

/**
 * DemoDto
 * 示例Dto，用于展示解析
 * @author LiuJibin
 */
public class DemoDto {
    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String toString() {
        return "name: " + name + ", value: " + value;
    }
}

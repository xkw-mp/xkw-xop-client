/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client;

/**
 * DemoDto
 * description
 *
 * @author LiuJibin
 * @version 1.0
 * @since 2021年09月17日
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

package com.tgb.config;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuyingcheng
 * @Description:
 * @Date: Created on 9:56 2018/2/6.
 */
public class Bean {
    private  String name;
    private  String className;

    private List<Property> properties = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", properties=" + properties +
                '}';
    }
}

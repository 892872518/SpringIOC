package com.tgb.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @Author: xuyingcheng
 * @Description:
 * @Date: Created on 11:04 2018/2/6.
 */
public class BeanUtils {
    public static  Method getWriteMethod(Object beanObj,String name){
        Method method = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            if (pds!=null){
                for (PropertyDescriptor pd:pds) {
                    String pName = pd.getName();
                    if (pName.equals(name)){
                        method = pd.getWriteMethod();
                    }
                }
            }

        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (method == null){
            throw  new RuntimeException("请检查"+name+"属性的set方法是否创建");
        }
        return method;
    }
}

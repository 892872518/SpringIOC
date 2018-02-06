package com.tgb.main;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.tab.config.parse.ConfigManager;
import com.tgb.config.Bean;
import com.tgb.config.Property;
import com.tgb.utils.BeanUtils;

/**
 * @Author: xuyingcheng
 * @Description:
 * @Date: Created on 9:45 2018/2/6.
 */
public class ClassPathXmlApplicationContext implements  BeanFactory {
    //配置信息
    private Map<String,Bean> config;
    private  Map<String,Object> context = new HashMap<>();

    @Override
    public Object getBean(String beanName) {
        Object bean = context.get(beanName);
        return bean;
    }
    public ClassPathXmlApplicationContext(String path){
        config = ConfigManager.getConfig(path);
        if (config!=null){
            for (Map.Entry<String,Bean> en:config.entrySet()) {
                String beanName = en.getKey();
                Bean bean = en.getValue();
                Object exsitBean = context.get(beanName);
                if (exsitBean !=null){
                    Object beanObj = createBean(bean);
                    context.put(beanName,beanObj);
                }
            }
        }
    }
    //根据bean配置创建bean对象
    private  Object createBean(Bean bean){
        //获得要创建的bean的class
        String className = bean.getClassName();
        Class clazz = null;
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw  new RuntimeException("请检查bean的class配置"+className);
        }
        //将class对应的对象创建出来
        Object beanObj = null;
        try {
            beanObj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("bean没有空参构造"+className);
        }
        //获得bean的属性，将其注入
        if (bean.getProperties()!=null){
            for (Property property:bean.getProperties()) {
                String name = property.getName();
                Method setMethod = BeanUtils.getWriteMethod(beanObj, name);
                Object parm=null;
                if (property.getValue()!=null){
                    String value = property.getValue();
                    parm=value;
                }
                if (property.getRef()!=null){
                    Object exsitBean = context.get(property.getRef());
                    if (exsitBean==null){
                        exsitBean = createBean(config.get(property.getRef()));
                        context.put(property.getRef(),exsitBean);
                    }
                    parm=exsitBean;
                }
                //调用set方法注入
                try {
                    setMethod.invoke(beanObj,parm);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("bean的属性"+parm+"没有对应的set方法，或者参数不正确"+className);

                }
            }
        }
        return beanObj;
    }
}

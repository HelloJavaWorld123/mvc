package com.jzy.api.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class CustomerContextAware implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (CustomerContextAware.applicationContext == null) {
            CustomerContextAware.applicationContext = applicationContext;
        }
    }

    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(Class beanClass) {
        return getApplicationContext().getBean(beanClass);
    }
}

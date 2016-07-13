/*
 * Copyright 2016 alessandro negrin &lt;alessandro@sixdegreeshq.com&gt;.
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
package com.sixdegreeshq.aop;

import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * a bean factory that integrates ByteBuddy
 *
 * @author alessandro negrin &lt;alessandro@sixdegreeshq.com&gt;
 */
public class ByteBuddyProxyFactoryBean implements FactoryBean<Object> {

    private static Log log = LogFactory.getLog(ByteBuddyProxyFactoryBean.class);

    private boolean singleton = true;

    private Object target;

    private Object instance;

    /**
     * set the target bean
     *
     * @param target the target bean
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * get the instance to be published in spring context
     *
     * @return the bean instance
     * @throws Exception if something goes wrong :D
     */
    @Override
    public Object getObject() throws Exception {
        if (singleton) {
            return getInstance();
        } else {
            return newInstance();
        }
    }

    /**
     * type of the bean
     *
     * @return the type of the set target
     */
    @Override
    public Class<?> getObjectType() {
        return target != null ? target.getClass() : null;
    }

    /**
     * return if the bean has to be a singleton
     *
     * @return if the bean has to be a singleton
     *
     */
    @Override
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * set if singleton
     *
     * @param singleton if singleton
     */
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    /**
     * create a new instance: using ByteBuddy to create a subclass and
     * intercepting all methods declared in the "superclass"
     *
     * @return an extended instance of target
     */
    private Object newInstance() {

        try {
            Class<? extends Object> targetClass = target.getClass();
            Class<?> proxyType = new ByteBuddy()
                    .subclass(targetClass)
                    .name(targetClass.getName() + "Subclass")
                    //.method(ElementMatchers.any())//if you want to match anything
                    .method(ElementMatchers.isDeclaredBy(targetClass))
                    .intercept(MethodDelegation.to(new Interceptor(target)))
                    .make()
                    .load(getClass().getClassLoader())
                    .getLoaded();

            return proxyType.newInstance();
        } catch (Throwable t) {
            t.printStackTrace();
            return target;
        }
    }

    /**
     * get the singleton instance (uses newInstance())
     *
     * @return singleton instance
     */
    private Object getInstance() {
        synchronized (this) {
            if (instance == null) {
                instance = newInstance();
            }
        }

        return instance;
    }

    /**
     * a simple interceptor that logs ms for invocation
     */
    public static class Interceptor {

        private final Object target;

        private Interceptor(Object target) {
            this.target = target;
        }

        public @RuntimeType
        Object intercept(@Origin(cache = true) Method method,
                @AllArguments Object[] arguments)
                throws Exception {
            long start = System.currentTimeMillis();

            try {
                return method.invoke(target, arguments);
            } finally {
                log.info("invocation of " + method.getName() + " in " + (System.currentTimeMillis() - start) + " ms");
            }
        }
    }
}

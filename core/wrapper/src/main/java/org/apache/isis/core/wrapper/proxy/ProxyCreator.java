/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.core.wrapper.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.isis.applib.services.wrapper.WrappingObject;
import org.apache.isis.commons.internal.base._Casts;
import org.apache.isis.commons.internal.collections._Arrays;
import org.apache.isis.core.commons.lang.ArrayExtensions;
import org.apache.isis.core.metamodel.specloader.classsubstitutor.ProxyEnhanced;
import org.apache.isis.core.plugins.codegen.ProxyFactory;
import org.apache.isis.core.wrapper.handlers.DelegatingInvocationHandler;

import com.google.common.collect.MapMaker;

public class ProxyCreator {

    /**
     * Lazily constructed cache.
     */
    private final Map<Class<?>, ProxyFactory<?>> proxyFactoryByClass;

    public ProxyCreator() {
        this(new MapMaker().weakKeys().concurrencyLevel(10).<Class<?>, ProxyFactory<?>>makeMap());
    }

    public ProxyCreator(Map<Class<?>, ProxyFactory<?>> proxyFactoryByClass) {
        this.proxyFactoryByClass = proxyFactoryByClass;
    }

    @SuppressWarnings("unchecked")
    public <T> T instantiateProxy(final DelegatingInvocationHandler<T> handler) {

        final T toProxy = handler.getDelegate();

        final Class<T> base = (Class<T>) toProxy.getClass();

        if (base.isInterface()) {
            return createInstanceForInterface(base, handler, WrappingObject.class);
        } else {
            final ProxyFactory<T> proxyFactory = proxyFactoryFor(base);
            return proxyFactory.createInstance(handler, false);
        }
    }

    // -- HELPER

    private <T> ProxyFactory<T> proxyFactoryFor(final Class<T> toProxyClass) {
        ProxyFactory<T> proxyFactory = _Casts.uncheckedCast(proxyFactoryByClass.get(toProxyClass));
        if(proxyFactory == null) {
            proxyFactory = createProxyFactoryFor(toProxyClass);
            proxyFactoryByClass.put(toProxyClass, proxyFactory);
        }
        return proxyFactory;
    }

    private <T> ProxyFactory<T> createProxyFactoryFor(final Class<T> toProxyClass) {

        final Class<?>[] interfaces = ArrayExtensions.combine(
                toProxyClass.getInterfaces(),
                new Class<?>[] { ProxyEnhanced.class, WrappingObject.class });

        final ProxyFactory<T> proxyFactory = ProxyFactory.builder(toProxyClass)
                .interfaces(interfaces)
                .build();

        return proxyFactory;
    }

    @SuppressWarnings("unchecked")
    private static <T> T createInstanceForInterface(
            final Class<T> base, final InvocationHandler handler, final Class<?>... auxiliaryTypes) {
        return (T) Proxy.newProxyInstance(base.getClassLoader(), _Arrays.combine(base, auxiliaryTypes) , handler);
    }

}

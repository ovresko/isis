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

package org.apache.isis.applib.internal.discover;

import java.util.List;
import java.util.Set;

import org.apache.isis.applib.internal.context._Context;
import org.apache.isis.applib.internal.context._Plugin;
import org.apache.isis.applib.plugins.classdiscovery.ClassDiscovery;
import org.apache.isis.applib.plugins.classdiscovery.ClassDiscoveryPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>- internal use only -</h1>
 * <p>
 * Java reflective utilities.
 * </p>
 * <p>
 * <b>WARNING</b>: Do <b>NOT</b> use any of the classes provided by this package! <br/> 
 * These may be changed or removed without notice!
 * </p>
 * @since 2.0.0
 */
public final class _Discover {

	private _Discover(){}
	
	// -- CLASS DISCOVERY VIA PLUGIN

	//TODO missing java-doc
	public static ClassDiscovery discover(String packageNamePrefix) {
		return getPlugin().discover(packageNamePrefix);
	}
	
	//TODO missing java-doc
	public static ClassDiscovery discover(List<String> packageNamePrefixes) {
		return getPlugin().discover(packageNamePrefixes);
	}
	
	//TODO missing java-doc 
	public static ClassDiscovery discoverFullscan(String packageNamePrefix) {
		return getPlugin().discover(packageNamePrefix);
	}
	
	// -- HELPER
	
	private static ClassDiscoveryPlugin getPlugin() {
		return _Context.computeIfAbsent(ClassDiscoveryPlugin.class, __->loadPluginsPickAny());
	}

	private static ClassDiscoveryPlugin loadPluginsPickAny() {
		final Set<ClassDiscoveryPlugin> plugins = _Plugin.load(ClassDiscoveryPlugin.class);
		
		if(plugins.isEmpty()) {
			return ClassDiscoveryPlugin.nop();
		}
		
		if(plugins.size()>1) {
			final Logger LOG = LoggerFactory.getLogger(ClassDiscoveryPlugin.class);
			LOG.warn("you have more than one ClassDiscoveryPlugin on your class path, just picking one");
		}
		
		return plugins.iterator().next();
	}
	
}
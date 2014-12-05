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

package org.apache.isis.viewer.wicket.model.models;

import java.util.List;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.facets.object.domainservice.DomainServiceFacet;

/**
 * Backing model for actions of application services menu bar (typically, as
 * displayed along the top or side of the page).
 */
public class ServiceActionsModel extends ModelAbstract<List<ObjectAdapter>> {

    private static final long serialVersionUID = 1L;

    private final DomainService.MenuBar menuBar;

    private static Predicate<ObjectAdapter> with(final DomainService.MenuBar menuBar) {
        return new Predicate<ObjectAdapter>() {
            @Override
            public boolean apply(ObjectAdapter input) {
              final DomainServiceFacet facet = input.getSpecification().getFacet
                  (DomainServiceFacet.class);
                return facet != null && facet.getMenuBar() == menuBar;
            }
        };
    }

    public ServiceActionsModel(DomainService.MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public DomainService.MenuBar getMenuBar() {
        return menuBar;
    }

    protected List<ObjectAdapter> load() {
        return Lists.newArrayList(Iterables.filter(getServiceAdapters(), with(menuBar)));
    }

    protected List<ObjectAdapter> getServiceAdapters() {
        return getPersistenceSession().getServices();
    }


}
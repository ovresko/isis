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

package junit.todo;

import junit.AbstractTest;

import org.apache.isis.viewer.junit.Fixture;
import org.apache.isis.viewer.junit.Fixtures;
import org.junit.Before;

import dom.scalars.PrimitiveValuesEntity;
import fixture.scalars.PrimitiveValuesEntityFixture;

@Fixtures({ @Fixture(PrimitiveValuesEntityFixture.class) })
public class ScalarEntityTest extends AbstractTest {

    private PrimitiveValuesEntity firstItem;

    @Override
    @Before
    public void setUp() {
        firstItem = wrapped(primitivesEntityRepository.list().get(0));
    }
    

}

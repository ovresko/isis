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

package org.apache.isis.commons.internal.base;

/**
 * <h1>- internal use only -</h1>
 * <p>
 * Time measuring utilities.
 * </p>
 * <p>
 * <b>WARNING</b>: Do <b>NOT</b> use any of the classes provided by this package! <br/>
 * These may be changed or removed without notice!
 * </p>
 *
 * @since 2.0.0
 */
public final class _Timing {

    private _Timing(){}

    /**
     * @return a new 'now' started instance of {@link StopWatch}
     */
    public static StopWatch now() {
        return new StopWatch();
    }

    /**
     * Non thread safe start/stop watch utilizing the currently running
     * JVM's high-resolution time source.
     */
    public static final class StopWatch {
        
        private long t0 = 0;
        private long t1 = 0;
        private boolean stopped;
        
        private StopWatch() {
            start();
        }
        
        public StopWatch start() {
            t0 = System.nanoTime();
            stopped = false;
            return this;
        }
        
        public StopWatch stop() {
            t1 = System.nanoTime();
            stopped  = true;
            return this;
        }
        
        public double getMillis() {
            return 0.000_001 * getNanos();
        }
        
        public double getMicros() {
            return 0.001 * getNanos();
        }
        
        public long getNanos() {
            return stopped ? t1 - t0 : System.nanoTime() - t0 ;
        }
        
    }

}

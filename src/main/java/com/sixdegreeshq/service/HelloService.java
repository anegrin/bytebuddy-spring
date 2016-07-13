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
package com.sixdegreeshq.service;

import java.util.Random;

/**
 * the hello service
 *
 * @author alessandro negrin &lt;alessandro@sixdegreeshq.com&gt;
 */
public class HelloService {

    private Random random = new Random();

    private String helloPrefix;

    /**
     * set an hello message prefix (to verify the instance to be the good one :D
     * )
     *
     * @param helloPrefix a stupid message :D
     */
    public void setHelloPrefix(String helloPrefix) {
        this.helloPrefix = helloPrefix;
    }

    /**
     * sleep randomly and return the message
     *
     * @return helloPrefix+" "+sleep
     */
    public String get() {

        int sleep = random.nextInt(500);
        try {
            Thread.sleep(sleep);
        } catch (Throwable t) {
        }

        return helloPrefix + " " + sleep;
    }

}

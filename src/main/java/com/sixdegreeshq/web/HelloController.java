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
package com.sixdegreeshq.web;

import com.sixdegreeshq.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * the root controller
 *
 * @author alessandro negrin &lt;alessandro@sixdegreeshq.com&gt;
 */
@Controller
public class HelloController {

    /*
    * please not that the ByteBuddyProxyFactoryBean.getObject will be autowired
    * as configured in applicationContext.xml
     */
    @Autowired
    private HelloService helloService;

    /**
     * map /
     *
     * @return the message from the hello service
     */
    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "got " + helloService.get() + " from an instanceof " + helloService;
    }
}

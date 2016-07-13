# ByteBuddy + Spring Boot demo

This is a simple demo to integrate ByteBuddy in Spring as a FactoryBean to wrap method invocation and log timings

## Build

`mvn clean package`

## Run

`mvn spring-boot:run`

## Use

build, run and the open your browser on http://127.0.0.1:8080 you shoud see a message like this:

`got hello 309 from an instanceof com.sixdegreeshq.service.HelloServiceSubclass@713845ff`

and in the log you should see

`invocation of get in 309 ms`





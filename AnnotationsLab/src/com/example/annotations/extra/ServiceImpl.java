package com.example.annotations.extra;

public class ServiceImpl implements Service {

    @Override
    @Loggable
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    @Loggable
    @RequiresRole("ADMIN")
    public String adminAction(String data) {
        return "ADMIN OK: " + data;
    }
}

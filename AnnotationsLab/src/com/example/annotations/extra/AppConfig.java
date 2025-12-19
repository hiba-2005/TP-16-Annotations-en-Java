package com.example.annotations.extra;

public class AppConfig {

    @ConfigValue(key = "app.name")
    private String appName;

    @ConfigValue(key = "app.maxUsers")
    private int maxUsers;

    public String getAppName() { return appName; }
    public int getMaxUsers() { return maxUsers; }

    @Override
    public String toString() {
        return "AppConfig{appName='" + appName + "', maxUsers=" + maxUsers + "}";
    }
}

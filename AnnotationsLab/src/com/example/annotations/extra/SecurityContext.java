package com.example.annotations.extra;

import java.util.HashSet;
import java.util.Set;

public final class SecurityContext {
    private static final Set<String> roles = new HashSet<>();

    private SecurityContext() {}

    public static void setRoles(String... r) {
        roles.clear();
        for (String x : r) roles.add(x);
    }

    public static boolean hasRole(String role) {
        return roles.contains(role);
    }
}
package com.example.annotations.extra;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresRole {
    String value(); // ex: "ADMIN"
}
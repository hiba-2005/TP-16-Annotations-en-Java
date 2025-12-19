package com.example.annotations.extra;

import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public final class LogSecurityProxy {

    private LogSecurityProxy() {}

    @SuppressWarnings("unchecked")
    public static <T> T create(T target, Class<T> iface) {
        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                new Class<?>[]{iface},
                new Handler(target)
        );
    }

    private static class Handler implements InvocationHandler {
        private final Object target;

        Handler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // on récupère la méthode réelle (implémentation)
            Method implMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

            // Sécurité
            RequiresRole rr = implMethod.getAnnotation(RequiresRole.class);
            if (rr != null && !SecurityContext.hasRole(rr.value())) {
                throw new SecurityException("Accès refusé: rôle requis = " + rr.value());
            }

            // Log
            boolean log = implMethod.isAnnotationPresent(Loggable.class);
            if (log) {
                System.out.println("[LOG] " + LocalDateTime.now()
                        + " call " + method.getName()
                        + " args=" + Arrays.toString(args));
            }

            Object result = implMethod.invoke(target, args);

            if (log) {
                System.out.println("[LOG] " + method.getName() + " return=" + result);
            }

            return result;
        }
    }
}

package com.example.annotations.extra;

public class Demo {

    public static void main(String[] args) {

        // --- Ex 10: config injection ---
        AppConfig cfg = new AppConfig();
        ConfigInjector.inject(cfg, "app.properties");
        System.out.println("Config: " + cfg);

        // --- Ex 8 & 9: log + security via proxy ---
        Service service = LogSecurityProxy.create(new ServiceImpl(), Service.class);

        SecurityContext.setRoles("USER"); // pas admin
        System.out.println(service.hello("Roudaina"));

        try {
            System.out.println(service.adminAction("delete"));
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        SecurityContext.setRoles("ADMIN"); // maintenant admin
        System.out.println(service.adminAction("delete"));
    }
}

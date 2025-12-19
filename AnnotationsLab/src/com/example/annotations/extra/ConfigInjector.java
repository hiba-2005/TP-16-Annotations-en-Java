package com.example.annotations.extra;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public final class ConfigInjector {

    private ConfigInjector() {}

    public static void inject(Object obj, String propertiesFile) {
        try (InputStream in = ConfigInjector.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            if (in == null) throw new RuntimeException("Fichier introuvable: " + propertiesFile);

            Properties props = new Properties();
            props.load(in);

            for (Field f : obj.getClass().getDeclaredFields()) {
                if (!f.isAnnotationPresent(ConfigValue.class)) continue;

                ConfigValue cv = f.getAnnotation(ConfigValue.class);
                String value = props.getProperty(cv.key());

                f.setAccessible(true);

                // Conversion simple (String/int/boolean)
                if (f.getType() == int.class || f.getType() == Integer.class) {
                    f.set(obj, Integer.parseInt(value));
                } else if (f.getType() == boolean.class || f.getType() == Boolean.class) {
                    f.set(obj, Boolean.parseBoolean(value));
                } else {
                    f.set(obj, value); // String
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur injection config: " + e.getMessage(), e);
        }
    }
}

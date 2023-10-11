package com.project.pescueshop.util;

import com.project.pescueshop.model.annotation.Name;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public String generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        Class<?> clazz = o.getClass();
        Name annotation = clazz.getAnnotation(Name.class);
        if (annotation == null) {
            throw new IllegalArgumentException("No class annotated with @Name found.");
        }

        String prefix = "";
        prefix = annotation.prefix();

        // Add your custom logic here to generate an ID
        return generateCustomId(prefix);
    }

    private String generateCustomId(String prefix) {
        return prefix + "_" + generateValue();
    }

    private Long generateValue() {
        return System.currentTimeMillis();
    }
}

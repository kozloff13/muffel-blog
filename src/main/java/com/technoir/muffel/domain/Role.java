package com.technoir.muffel.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role {

    ANONYMOUS("Аноним"),
    REGULAR("Рядовой пользователь"),
    ADMIN("Администратор");

    Role(String desc) {
    }
}

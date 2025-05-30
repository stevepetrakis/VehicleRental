package com.evol.config;

public enum Role {
    ADMIN(1),
    USER(2);

    private final int i;
    private Role(int i) {
        this.i=i;
    }
}

package org.techabraao.api.contacts.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Roles {
    ADMIN("admin"),
    USER("user");

    private String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @JsonCreator
    public static Roles from(String value){
        return Roles.valueOf(value.toUpperCase());
    }
}

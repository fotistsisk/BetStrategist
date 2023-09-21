package com.fotistsiskakis.betstrategist.services;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Utils {
    public static UUID getUuidFromString(String id){
        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        return uuid;
    }
}

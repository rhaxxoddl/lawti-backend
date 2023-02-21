package com.oli.HometownPolitician.domain.user.equipment;

public class UserPrefixEquipment {
    static private final String UUID_PREFIX = "UUID-";
    static private final String BEARER_PREFIX = "Bearer ";



    static public String addPrefixUUID(String uuid) {
        return UUID_PREFIX + uuid;
    }
    static public String deletePrefix(String authorization) {
        return deleteUuidPrefix(deleteBearerPrefix(authorization));
    }

    static public String deleteUuidPrefix(String uuid) {
        if (uuid.contains(UUID_PREFIX))
            return uuid.substring(UUID_PREFIX.length());
        return uuid;
    }

    static public String deleteBearerPrefix(String bearerToken) {
        if (bearerToken.contains(BEARER_PREFIX))
            return bearerToken.substring(BEARER_PREFIX.length());
        return bearerToken;
    }
}

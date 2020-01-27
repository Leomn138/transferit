package org.demo.transferit.utils;

public class UuidChecker {
    public static boolean isUuid(String uuid) {
        if (uuid == null) { return false; }
        return uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
    }
}

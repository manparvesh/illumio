package com.manparvesh.illumio.components;

public class Util {
    public static int ipAddressToInt(String ipAddress) {
        String[] offsets = ipAddress.split("\\.");
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result *= 256;
            result += Integer.parseInt(offsets[i]);
        }
        return result;
    }
}

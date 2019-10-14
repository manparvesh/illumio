package com.manparvesh.illumio;

import com.manparvesh.illumio.components.RuleTrees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Firewall {
    private static final String INBOUND = "inbound";
    private static final String OUTBOUND = "outbound";
    private static final String TCP = "tcp";
    private static final String UDP = "udp";

    private RuleTrees inboundTcp;
    private RuleTrees inboundUdp;
    private RuleTrees outboundTcp;
    private RuleTrees outboundUdp;

    /**
     * Constructor method that takes a csv file as input
     * This csv file contains all the rules required for our firewall
     *
     * @param csvFile path to csv file with rules
     */
    Firewall(String csvFile) {
        inboundTcp = new RuleTrees();
        inboundUdp = new RuleTrees();
        outboundTcp = new RuleTrees();
        outboundUdp = new RuleTrees();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(csvFile)).getFile());

        // populate lists according to values in csv file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                String direction = values[0], protocol = values[1], port = values[2], ipAddress = values[3];
                if (INBOUND.equals(direction) && TCP.equals(protocol)) {
                    inboundTcp.insert(port, ipAddress);
                } else if (OUTBOUND.equals(direction) && TCP.equals(protocol)) {
                    outboundTcp.insert(port, ipAddress);
                } else if (INBOUND.equals(direction) && UDP.equals(protocol)) {
                    inboundUdp.insert(port, ipAddress);
                } else if (OUTBOUND.equals(direction) && UDP.equals(protocol)) {
                    outboundUdp.insert(port, ipAddress);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean accept_packet(String direction, String protocol, int port, String ipAddress) {
        if (INBOUND.equals(direction) && TCP.equals(protocol)) {
            return inboundTcp.check(port, ipAddress);
        } else if (OUTBOUND.equals(direction) && TCP.equals(protocol)) {
            return outboundTcp.check(port, ipAddress);
        } else if (INBOUND.equals(direction) && UDP.equals(protocol)) {
            return inboundUdp.check(port, ipAddress);
        } else if (OUTBOUND.equals(direction) && UDP.equals(protocol)) {
            return outboundUdp.check(port, ipAddress);
        }

        return false;
    }
}

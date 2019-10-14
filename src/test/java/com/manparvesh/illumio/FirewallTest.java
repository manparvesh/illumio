package com.manparvesh.illumio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FirewallTest {
    private static final String TRUE = "true";

    private void testFirewallTemplate(int inputNumber) {
        Firewall firewall = new Firewall("input/" + inputNumber + ".csv");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("output/" + inputNumber + ".csv")).getFile());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                String direction = values[0], protocol = values[1], port = values[2], ipAddress = values[3];

                boolean result = TRUE.equals(values[4]);
                assertThat(firewall.accept_packet(direction, protocol, Integer.parseInt(port), ipAddress), is(result));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFirewall1() {
        testFirewallTemplate(1);
    }

    @Test
    public void testFirewall2() {
        testFirewallTemplate(2);
    }
}
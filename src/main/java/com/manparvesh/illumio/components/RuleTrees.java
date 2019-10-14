package com.manparvesh.illumio.components;

import com.lodborg.intervaltree.IntegerInterval;
import com.lodborg.intervaltree.Interval;
import com.lodborg.intervaltree.IntervalTree;

import static com.manparvesh.illumio.components.Util.ipAddressToInt;

/**
 * Here we save 2 interval trees. Implementation of interval trees is taken from: https://github.com/lodborg/interval-tree
 * 1 interval tree is to save intervals of ports and the other is to save hash of IP addresses.
 */
public class RuleTrees {
    private IntervalTree<Integer> ports;
    private IntervalTree<Integer> ipAddresses;

    public RuleTrees() {
        ports = new IntervalTree<>();
        ipAddresses = new IntervalTree<>();
    }

    public void insert(String portRange, String ipAddressRange) {
        boolean isPortRange;
        boolean isIpAddressRange;

        int portStart;
        int portEnd;

        int ipAddressStart;
        int ipAddressEnd;

        String[] portsInRange = portRange.split("-");
        isPortRange = portsInRange.length > 1;
        portStart = Integer.parseInt(portsInRange[0]);
        if (isPortRange) {
            portEnd = Integer.parseInt(portsInRange[1]);
        } else {
            portEnd = portStart;
        }

        String[] ipAddressesInRange = ipAddressRange.split("-");
        isIpAddressRange = ipAddressesInRange.length > 1;
        ipAddressStart = ipAddressToInt(ipAddressesInRange[0]);
        if (isIpAddressRange) {
            ipAddressEnd = ipAddressToInt(ipAddressesInRange[1]);
        } else {
            ipAddressEnd = ipAddressStart;
        }

        ports.add(new IntegerInterval(portStart, portEnd, Interval.Bounded.CLOSED));
        ipAddresses.add(new IntegerInterval(ipAddressStart, ipAddressEnd, Interval.Bounded.CLOSED));
    }

    public boolean check(int port, String ipAddress) {
        int ipAddressHash = ipAddressToInt(ipAddress);
        return ports.query(new IntegerInterval(port, port, Interval.Bounded.CLOSED)).size() != 0
                &&
                ipAddresses.query(
                        new IntegerInterval(ipAddressHash, ipAddressHash, Interval.Bounded.CLOSED)
                ).size() != 0;
    }
}

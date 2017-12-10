package mafoe.util;

import mafoe.service.DemoService;

/**
 * Utility functions for Spring remoting.
 */
public class RemotingHelper {

    /**
     * Uniform way to create an endpoint name from a service interface. Used on both server and client to arrive at
     * the same endpoint name.
     */
    public static String serviceInterfaceToEndpoint(Class<? extends DemoService> serviceInterfaceClass) {
        return "/" + serviceInterfaceClass.getSimpleName();
    }
}

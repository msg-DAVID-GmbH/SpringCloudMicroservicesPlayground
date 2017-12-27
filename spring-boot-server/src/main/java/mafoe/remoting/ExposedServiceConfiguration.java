package mafoe.remoting;

/**
 * Interface for a bean a user needs to implement to get the exposing of services functionality.
 */
public interface ExposedServiceConfiguration {

    /**
     * @return the super interface of all interfaces eligible for service exposure
     */
    Class<?> getMarkerInterface();
}

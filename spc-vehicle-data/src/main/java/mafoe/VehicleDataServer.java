package mafoe;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Web application server.
 */
@EnableEurekaClient
@SpringBootApplication
public class VehicleDataServer {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(VehicleDataServer.class)
                .bannerMode(Banner.Mode.OFF)
                .profiles("populate-db")
                .run(args);
    }
}

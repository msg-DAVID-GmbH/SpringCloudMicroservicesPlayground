package mafoe;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Web application server.
 */
@SpringBootApplication
public class VehicleDataServer {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(VehicleDataServer.class)
                // this is a ridiculously amazing feature
                .bannerMode(Banner.Mode.CONSOLE)
                .profiles("populate-db")
                .run(args);
    }
}

package mafoe;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Web application server.
 */
@SpringBootApplication
public class Server {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(Server.class)
                // this is a ridiculously amazing feature
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);
    }
}

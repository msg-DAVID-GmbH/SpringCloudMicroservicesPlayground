package mafoe;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Web application server.
 * <p>
 * Uses an h2 database instead of hsqldb. Spring Boot autoconfigures an hsqldb unless you configure some database
 * properties to point it into a different direction. Here, I use the "h2" profile so that the application-h2.yaml
 * is used by spring boot. It contains the information to use an h2 database instead. Spring Boot then uses the h2
 * driver and the h2 dialect for hibernate, but since I'm using JPA it still works without having to change anything
 * else.
 * </p></>
 */
@SpringBootApplication
public class ServerWithH2 {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(ServerWithH2.class)
                // this is a ridiculously amazing feature
                .bannerMode(Banner.Mode.CONSOLE)
                .profiles("h2", "populate-db")
                .run(args);
    }
}

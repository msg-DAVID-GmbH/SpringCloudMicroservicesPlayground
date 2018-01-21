package mafoe;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Web application server.
 * <p>
 * Uses an h2 database instead of hsqldb. Spring Boot autoconfigures an hsqldb unless you configure some database
 * properties to point it into a different direction. Here, I use the "h2" profile so that the application-h2.yaml
 * is used by spring boot. It contains the information to use an h2 database instead. Spring Boot then uses the h2
 * driver and the h2 dialect for hibernate, but since I'm using JPA it still works without having to change anything
 * else.
 * </p>
 * Note that this class is not annotated with @SpringBootApplication as is Server.java. If you have two or more classes
 * with that annotation in your project, you get into all kinds of funny business, which you don't want to.
 */
public class ServerWithH2 {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(Server.class)
                // this is a ridiculously amazing feature
                .bannerMode(Banner.Mode.CONSOLE)
                .profiles("h2", "populate-db")
                .run(args);
    }
}

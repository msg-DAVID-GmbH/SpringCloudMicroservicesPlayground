package mafoe.brand;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BrandWebServiceApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(BrandWebServiceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .profiles("populate-db")
                .run(args);
    }
}

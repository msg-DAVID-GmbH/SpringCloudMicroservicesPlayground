package mafoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ClassPathResource;

/**
 * Simple JavaFX Client for the spring-boot demo.
 */
@SpringBootApplication
public class FxClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        new SpringApplicationBuilder()
                .sources(FxClient.class)
                // this is a ridiculously amazing feature
                .bannerMode(Banner.Mode.CONSOLE)
                .run();

        ClassPathResource cpr = new ClassPathResource("mafoe/controller/ShowBooks.fxml");
        Parent root = FXMLLoader.load(cpr.getURL());
        Scene scene = new Scene(root, 600, 275);

        stage.setTitle("Here are all our books");
        stage.setScene(scene);
        stage.show();
    }
}

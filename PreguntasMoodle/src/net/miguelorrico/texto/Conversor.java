package net.miguelorrico.texto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Conversor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        Controlador controlador = new Controlador(primaryStage);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("TextoAPregunta.fxml"));
        Parent root = (Parent) loader.load();
        TextoAPregunta principal = (TextoAPregunta) loader.getController();
        principal.setControlador(controlador);
        controlador.setVentanaPrincipal(principal);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Conversor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

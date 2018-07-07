package net.miguelorrico.aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorPreguntas extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        Controlador controlador = new Controlador(primaryStage);



        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "Principal.fxml"));
        Parent root = (Parent) loader.load();
        Principal principal=loader.getController();
        principal.setControlador(controlador);
        controlador.setVentanaPrincipal(principal);


        Scene scene = new Scene(root);


        primaryStage.setTitle("Editor de preguntas Moodle");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

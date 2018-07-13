package net.miguelorrico.texto;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.miguelorrico.preguntas.Pregunta;
import net.miguelorrico.preguntas.PreguntaMultiple;
import net.miguelorrico.preguntas.UtilidadesTextoPreguntas;

public class Controlador {
    private Pregunta pregunta=new PreguntaMultiple();
    private final Stage stage;
    private TextoAPregunta ventanaPrincipal;
    private int estado=0;

    public Controlador(Stage primaryStage) {
        this.stage=primaryStage;
    }

    public void setVentanaPrincipal(TextoAPregunta ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        ventanaPrincipal.vistaPrevia.getEngine().loadContent(this.pregunta.htmlPregunta());
    }

    public TextoAPregunta getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public void teclaPulsada(KeyEvent keyEvent) {
        this.pregunta=UtilidadesTextoPreguntas.textoAPregunta(this.ventanaPrincipal.textoPregunta.getText());
        ventanaPrincipal.vistaPrevia.getEngine().loadContent(this.pregunta.htmlPregunta());
    }
}

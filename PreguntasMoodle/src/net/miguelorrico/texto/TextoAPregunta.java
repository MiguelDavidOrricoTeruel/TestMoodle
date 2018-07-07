package net.miguelorrico.texto;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

public class TextoAPregunta {
    public WebView vistaPrevia;
    public TextArea textoPregunta;
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void handleKeyReleades(KeyEvent keyEvent) {
        controlador.teclaPulsada(keyEvent);
    }
}

package net.miguelorrico.aplicacion;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

public class Principal {
    public Button abrirFichero;
    public Label estatus;
    public Label etiquetaNumeroPreguntas;
    public TreeView<String> arbolPreguntas;
    public TextArea textoPregunta;
    public WebView htmlPregunta;
    public WebView webViewPregunta;
    public Label estatusCreacion;
    public Button nuevoFichero;
    Controlador controlador;

    public WebView getWebViewPregunta() {
        return webViewPregunta;
    }

    public TextArea getTextoPregunta() {
        return textoPregunta;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void abrirFicheroHandle(ActionEvent actionEvent) {
        controlador.abrirFichero();
    }

    public void nuevoFicheroHandle(ActionEvent actionEvent) {
        controlador.nuevoFichero();
    }

    public TreeView<String> getArbolPreguntas() {
        return arbolPreguntas;
    }

    public void teclaPulsada(KeyEvent keyEvent) {
        controlador.teclaPulsada(keyEvent);
    }

    public void guardarArchivoHandle(ActionEvent actionEvent) {
        controlador.guardarArchivo();
    }

    public void itemSeleccionadoHandle(TreeView.EditEvent<String> stringEditEvent) {

    }
}


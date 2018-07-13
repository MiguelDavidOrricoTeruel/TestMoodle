package net.miguelorrico.aplicacion;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.miguelorrico.preguntas.*;

import java.io.File;
import java.util.*;

public class Controlador {
    ListaPreguntas listaPreguntas;
    Stage stage;
    File ficheroActual;
    Principal ventanaPrincipal;
    boolean modoCreacion = true;
    Categoria categoriaActual;
    Pregunta preguntaActual;
    int estadoPregunta=0;


    public Controlador(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void setVentanaPrincipal(Principal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void abrirFichero() {
        System.out.println("Abriendo fichero");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir fichero de preguntas");
        File ficheroElegido = fileChooser.showOpenDialog(stage);
        if (ficheroElegido != null) {
            this.ficheroActual = ficheroElegido;
            ventanaPrincipal.estatus.setText("Fichero Actual: " + ficheroActual.getName());
            listaPreguntas = new ListaPreguntas();
            listaPreguntas.leerFichero(ficheroActual.getName());
            ventanaPrincipal.etiquetaNumeroPreguntas.setText("Número de preguntas: " + listaPreguntas.size());
            ventanaPrincipal.getWebViewPregunta().getEngine().loadContent(listaPreguntas.listaPreguntas.get(0).htmlPregunta());
            actualizaArbolPreguntas(listaPreguntas);
            ventanaPrincipal.estatusCreacion.setText("Modo Revisión");
            modoCreacion = false;
        }

    }

    private void actualizaArbolPreguntas(ListaPreguntas listaPreguntas) {
        TreeView<String> arbolPreguntas=ventanaPrincipal.getArbolPreguntas();
        TreeItem<String> raiz = new TreeItem<>("Categorías");
        arbolPreguntas.setRoot(raiz);
        Set<Categoria> listaCategorias = new TreeSet<>();
        for (Pregunta p : listaPreguntas.listaPreguntas) {
            if (listaCategorias.add(p.getCategoria())) {
                TreeItem<String> categoria = new TreeItem<String>(p.getCategoria().getFinalNombre());
                categoria.setExpanded(true);
                raiz.getChildren().add(categoria);
                for (Pregunta pregunta : p.getCategoria().getPreguntas()) {
                    categoria.getChildren().add(new TreeItem<>(pregunta.getEnunciadoElipsis()));
                }
            }
        }
        raiz.setExpanded(true);

    }

    public void nuevoFichero() {
        System.out.println("Abriendo fichero");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Nuevo fichero de preguntas");
        File ficheroElegido = fileChooser.showSaveDialog(stage);
        if (ficheroElegido == null) {
            return;
        }
        this.ficheroActual = ficheroElegido;
        ventanaPrincipal.estatus.setText("Fichero Actual: " + ficheroActual.getName());
        listaPreguntas = new ListaPreguntas();
        ventanaPrincipal.etiquetaNumeroPreguntas.setText("Número de preguntas: " + listaPreguntas.size());
        ventanaPrincipal.estatusCreacion.setText("Modo Creación");
        modoCreacion = true;
        TextInputDialog dialog = new TextInputDialog("Nombre de la Categoría");
        dialog.setTitle("Nombre de la Categoría");
        dialog.setHeaderText("Introduzca el nombre de la categoría");
        dialog.setContentText("Categoría a utilizar a partir de ahora");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            categoriaActual=new Categoria(result.get());
            TreeItem<String> raiz = new TreeItem<>("Categorías");
            TreeView<String> arbolPreguntas=ventanaPrincipal.getArbolPreguntas();
            arbolPreguntas.setRoot(raiz);
            TreeItem<String> categoria = new TreeItem<String>(result.get());

            categoria.setExpanded(true);
            raiz.getChildren().add(categoria);
            raiz.setExpanded(true);
            arbolPreguntas.getSelectionModel().select(categoria);
            ventanaPrincipal.getTextoPregunta().requestFocus();
        }


    }

    public void teclaPulsada(KeyEvent keyEvent) {
        preguntaActual=UtilidadesTextoPreguntas.textoAPregunta(this.ventanaPrincipal.getTextoPregunta().getText());
        if(preguntaActual.isTerminada()){
            preguntaActual.setCategoria(this.categoriaActual);
            listaPreguntas.listaPreguntas.add(preguntaActual);
            this.ventanaPrincipal.getTextoPregunta().setText("");
            preguntaActual=UtilidadesTextoPreguntas.textoAPregunta("");
            actualizaArbolPreguntas(listaPreguntas);
            ventanaPrincipal.etiquetaNumeroPreguntas.setText("Número de preguntas: " + listaPreguntas.size());
        }
        ventanaPrincipal.getWebViewPregunta().getEngine().loadContent(preguntaActual.htmlPregunta());
    }

    public void guardarArchivo() {
        UtilidadesXMLPruebas.escribeXML(ficheroActual.getName(),this.listaPreguntas.listaPreguntas);
        new Alert(Alert.AlertType.INFORMATION, "Archivo Guardado").showAndWait();
    }
}

package net.miguelorrico.aplicacion;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.miguelorrico.preguntas.Categoria;
import net.miguelorrico.preguntas.Pregunta;
import net.miguelorrico.preguntas.PreguntaFactory;
import net.miguelorrico.preguntas.UtilidadesXMLPruebas;

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
    int estadoPregunta = 0;


    public Controlador(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void setVentanaPrincipal(Principal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.ventanaPrincipal.getArbolPreguntas().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                System.out.println("Selected Text : " + selectedItem.getValue());
                for (Pregunta p : listaPreguntas.listaPreguntas) {
                    if (p.getEnunciado().equals(selectedItem.getValue())) {
                        actualizaVistaCambioPregunta(p);
                        break;
                    }
                }
            }
        });
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
            listaPreguntas.leerFichero(ficheroActual.getAbsolutePath());
            ventanaPrincipal.etiquetaNumeroPreguntas.setText("Número de preguntas: " + listaPreguntas.size());
            actualizaVistaCambioPregunta(listaPreguntas.listaPreguntas.get(0));
            actualizaArbolPreguntas(listaPreguntas, 0);
            ventanaPrincipal.estatusCreacion.setText("Modo Revisión");
            modoCreacion = false;
        }

    }

    private void actualizaVistaCambioPregunta(Pregunta p) {
        ventanaPrincipal.getTextoPregunta().setText(p.preguntaATexto());
        ventanaPrincipal.getWebViewPregunta().getEngine().loadContent(p.htmlPregunta());
    }

    private void actualizaArbolPreguntas(ListaPreguntas listaPreguntas, int preguntaSeleccionada) {
        TreeView<String> arbolPreguntas = ventanaPrincipal.getArbolPreguntas();
        TreeItem<String> raiz = new TreeItem<>("Categorías");
        List<TreeItem<String>> preguntasArbol = new ArrayList<>();
        arbolPreguntas.setRoot(raiz);
        Set<Categoria> listaCategorias = new TreeSet<>();
        for (Pregunta p : listaPreguntas.listaPreguntas) {
            if (listaCategorias.add(p.getCategoria())) {
                TreeItem<String> categoria = new TreeItem<>(p.getCategoria().getFinalNombre());
                categoria.setExpanded(true);
                raiz.getChildren().add(categoria);
                for (Pregunta pregunta : p.getCategoria().getPreguntas()) {
                    TreeItem<String> preguntaAnyadida = new TreeItem<>(pregunta.getEnunciadoElipsis());
                    preguntaAnyadida.setExpanded(true);
                    categoria.getChildren().add(preguntaAnyadida);
                    preguntasArbol.add(preguntaAnyadida);
                }
            }
        }
        System.out.println("Seleccionadda en el árbol:" + preguntaSeleccionada);
        arbolPreguntas.getSelectionModel().select(preguntasArbol.get(preguntaSeleccionada));
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
        if (result.isPresent()) {
            categoriaActual = new Categoria(result.get());
            TreeItem<String> raiz = new TreeItem<>("Categorías");
            TreeView<String> arbolPreguntas = ventanaPrincipal.getArbolPreguntas();
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
        preguntaActual = PreguntaFactory.getPregunta(this.ventanaPrincipal.getTextoPregunta().getText());
        if (preguntaActual.isTerminada()) {
            preguntaActual.setCategoria(this.categoriaActual);
            listaPreguntas.listaPreguntas.add(preguntaActual);
            this.ventanaPrincipal.getTextoPregunta().setText("");
            preguntaActual = PreguntaFactory.getPregunta("");
            actualizaArbolPreguntas(listaPreguntas, 0);
            ventanaPrincipal.etiquetaNumeroPreguntas.setText("Número de preguntas: " + listaPreguntas.size());
        }
        ventanaPrincipal.getWebViewPregunta().getEngine().loadContent(preguntaActual.htmlPregunta());
    }

    public void guardarArchivo() {
        UtilidadesXMLPruebas.escribeXML(ficheroActual.getAbsolutePath(), this.listaPreguntas.listaPreguntas);
        new Alert(Alert.AlertType.INFORMATION, "Archivo Guardado").showAndWait();
    }
}

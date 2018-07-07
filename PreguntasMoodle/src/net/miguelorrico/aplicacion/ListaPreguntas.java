package net.miguelorrico.aplicacion;

import net.miguelorrico.preguntas.Pregunta;
import net.miguelorrico.preguntas.UtilidadesXMLPruebas;

import java.util.ArrayList;
import java.util.List;

public class ListaPreguntas {
    List<Pregunta> listaPreguntas=new ArrayList<>();

    public void leerFichero(String nombreFichero){
        listaPreguntas=UtilidadesXMLPruebas.leeXML(nombreFichero);
    }

    public int size(){
        return listaPreguntas.size();
    }
}

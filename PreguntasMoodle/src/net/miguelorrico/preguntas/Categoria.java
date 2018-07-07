package net.miguelorrico.preguntas;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Item implements Comparable{
    private final String nombre;
    private List<Pregunta> preguntas;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.preguntas = new ArrayList<>();
    }

    public void addPregunta(Pregunta p){
        this.preguntas.add(p);
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.nombre.compareTo(((Categoria)o).nombre);
    }

    public String getFinalNombre(){
        int ultimo=this.nombre.lastIndexOf('/');
        return this.nombre.substring(ultimo+1);
    }
}

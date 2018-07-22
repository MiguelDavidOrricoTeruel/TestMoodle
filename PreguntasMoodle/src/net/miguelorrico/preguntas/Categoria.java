package net.miguelorrico.preguntas;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import static net.miguelorrico.preguntas.UtilidadesXMLPruebas.addConText;

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

    public Element getElementXML(Document doc) {
        Element salida = doc.createElement("question");
        Attr attr = doc.createAttribute("type");
        attr.setValue("category");
        salida.setAttributeNode(attr);
        salida.appendChild(addConText(doc, "category", "$course$/" + this.nombre, false));
        return salida;
    }

}

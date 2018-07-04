package net.miguelorrico.preguntas;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Respuesta {
    private double peso;
    private String texto;
    private String feedback;

    public Respuesta(double peso, String texto, String feedback) {
        this.peso = peso;
        this.texto = texto;
        this.feedback = feedback;
    }

    public String cadenaRespuesta() {
        String salida = "";
        salida += this.peso > 0 ? "* " : "  ";
        salida += this.texto;
        return salida;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "peso=" + peso +
                ", texto='" + texto + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Node getRespuestaXML(Document doc) {
        Element e = doc.createElement("answer");
        Attr attr = doc.createAttribute("fraction");
        attr.setValue("" + this.peso);
        e.setAttribute("fraction", "" + this.peso);
        Attr attr2 = doc.createAttribute("format");
        attr2.setValue("html");
        e.setAttributeNode(attr2);
        Element texto = doc.createElement("text");

        Node cdata = doc.createCDATASection(this.texto);
        texto.appendChild(cdata);
        e.appendChild(texto);
        Element feedback=doc.createElement("feedback");


        Element textoTag = doc.createElement("text");
        textoTag.setTextContent(this.feedback);
        feedback.appendChild(textoTag);
        e.appendChild(feedback);
        return e;
    }
}

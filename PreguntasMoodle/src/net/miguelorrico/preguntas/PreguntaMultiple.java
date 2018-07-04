package net.miguelorrico.preguntas;

import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class PreguntaMultiple extends Pregunta {
    private boolean unicaOpcion;
    private boolean barajarRespuestas;
    private final String feedbackCorrecto;
    private final String feedbackParcial;
    private final String feedbackIncorrecto;
    private TiposNumeracionRespuestas numeracion;
    private List<Respuesta> respuestas;

    @Override
    public Element getElementXML(Document doc) {
        Element salida=super.getElementXML(doc);
        salida.appendChild(addSinText(doc,"single",""+this.unicaOpcion,false));
        salida.appendChild(addSinText(doc,"shuffleanswers",""+this.barajarRespuestas,false));
        salida.appendChild(addSinText(doc,"answernumbering",this.numeracion.getTextoMostrado(),false));
        salida.appendChild(addConText(doc,"correctfeedback",this.feedbackCorrecto,true));
        salida.appendChild(addConText(doc,"partiallycorrectfeedback",this.feedbackParcial,true));
        salida.appendChild(addConText(doc,"incorrectfeedback",this.feedbackIncorrecto,true));
        for (int i = 0; i < respuestas.size(); i++) {
            salida.appendChild(respuestas.get(i).getRespuestaXML(doc));
        }
        return salida;
    }

    public PreguntaMultiple(Element element) {
        super(element);
        System.out.println("CREANDO PREGUNTA MULTIPLE");
        unicaOpcion = Boolean.parseBoolean(element.getElementsByTagName("single").item(0).getTextContent());
        barajarRespuestas = Boolean.parseBoolean(element.getElementsByTagName("shuffleanswers").item(0).getTextContent());
        this.feedbackCorrecto = getStringTagText(element.getElementsByTagName("correctfeedback").item(0));
        this.feedbackParcial = getStringTagText(element.getElementsByTagName("partiallycorrectfeedback").item(0));
        this.feedbackIncorrecto = getStringTagText(element.getElementsByTagName("incorrectfeedback").item(0));
        String cadenaNumeracion = element.getElementsByTagName("answernumbering").item(0).getTextContent();
        for (TiposNumeracionRespuestas t : TiposNumeracionRespuestas.values()) {
            if (cadenaNumeracion.equals(t.getTextoMostrado())) {
                this.numeracion = t;
                break;
            }
        }
        respuestas = new ArrayList<>();
        NodeList nodosRespuestas=element.getElementsByTagName("answer");
        for (int i = 0; i < nodosRespuestas.getLength(); i++) {
            NamedNodeMap atributos=nodosRespuestas.item(i).getAttributes();
            double peso=Double.parseDouble(atributos.getNamedItem("fraction").getNodeValue());
            String texto=getStringTagText(nodosRespuestas.item(i));
            String feedback="";
            if (nodosRespuestas.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element elementoRespuesta = (Element) nodosRespuestas.item(i);

                Node nodoFeedback = elementoRespuesta.getElementsByTagName("feedback").item(0);
                feedback=getStringTagText(nodoFeedback);
            }
            respuestas.add(new Respuesta(peso,texto,feedback));
        }
    }

    @Override
    public String cadenaPregunta() {
        String salida = super.cadenaPregunta();
        salida += "\n";
        salida += "Elija la opción correcta:\n";
        int numero=1;
        for (Respuesta r:this.respuestas){
            salida+="\n"+this.numeracion.getNumeracion(numero++)+" "+r.cadenaRespuesta();
        }
        return salida;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPreguntaMultiple{" +
                "unicaOpcion=" + unicaOpcion +
                ", barajarRespuestas=" + barajarRespuestas +
                ", feedbackCorrecto='" + feedbackCorrecto + '\'' +
                ", feedbackParcial='" + feedbackParcial + '\'' +
                ", feedbackIncorrecto='" + feedbackIncorrecto + '\'' +
                ", numeracion=" + numeracion +
                '}';
    }
}

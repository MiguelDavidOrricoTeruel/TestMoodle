package net.miguelorrico.preguntas;

import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

import static net.miguelorrico.preguntas.UtilidadesXMLPruebas.addConText;
import static net.miguelorrico.preguntas.UtilidadesXMLPruebas.addSinText;

public class PreguntaMultiple extends Pregunta {
    private boolean unicaOpcion=true;
    private boolean barajarRespuestas=true;
    private String feedbackCorrecto="";
    private String feedbackParcial="";
    private String feedbackIncorrecto="";
    private TiposNumeracionRespuestas numeracion=TiposNumeracionRespuestas.MINUSCULAS;
    private List<Respuesta> respuestas=new ArrayList<>();

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setUnicaOpcion(boolean unicaOpcion) {
        this.unicaOpcion = unicaOpcion;
    }

    public void setBarajarRespuestas(boolean barajarRespuestas) {
        this.barajarRespuestas = barajarRespuestas;
    }

    public void setFeedbackCorrecto(String feedbackCorrecto) {
        this.feedbackCorrecto = feedbackCorrecto;
    }

    public void setFeedbackParcial(String feedbackParcial) {
        this.feedbackParcial = feedbackParcial;
    }

    public void setFeedbackIncorrecto(String feedbackIncorrecto) {
        this.feedbackIncorrecto = feedbackIncorrecto;
    }

    public void setNumeracion(TiposNumeracionRespuestas numeracion) {
        this.numeracion = numeracion;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public PreguntaMultiple() {
    }

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

    @Override
    public String htmlPregunta() {
        String salida=super.htmlPregunta();
        salida+="<p>Elija la opci&oacute;n correcta:</p>";
        salida+="<table>\n" +
                "<tbody>";

        int numero=1;
        for (Respuesta r:this.respuestas){
            salida+="<tr><td>"+this.numeracion.getNumeracion(numero++)+"</td>";
            salida+="<td>";
            if(r.getPeso()>0){
                salida+="<div style=\"color:green\">";
            } else {
                salida+="<div>";
            }
            salida+=r.getTexto();
            salida+="</div></td></tr>";
        }
        salida+="</tbody>\n" +
                "</table>";
        return salida;
    }


}

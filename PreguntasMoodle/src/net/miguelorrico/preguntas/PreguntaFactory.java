package net.miguelorrico.preguntas;

import org.w3c.dom.Element;

public class PreguntaFactory {
    public static Pregunta getPregunta(Element element){
        String tipo=element.getAttribute("type");
        return TiposPregunta.valueOf(tipo.toUpperCase()).getPregunta(element);
    }
}

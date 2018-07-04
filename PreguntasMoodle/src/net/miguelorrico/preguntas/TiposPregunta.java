package net.miguelorrico.preguntas;

import org.w3c.dom.Element;

public enum TiposPregunta {
    MULTICHOICE{
        @Override
        public Pregunta getPregunta(Element element) {
            return new PreguntaMultiple(element);
        }
    };
    public abstract Pregunta getPregunta(Element element);
}

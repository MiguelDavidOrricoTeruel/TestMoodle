package net.miguelorrico.preguntas;

import org.w3c.dom.Element;

import java.util.List;

public class PreguntaFactory {
    public static Pregunta getPregunta(Element element) {
        String tipo = element.getAttribute("type");
        return TiposPregunta.valueOf(tipo.toUpperCase()).getPregunta(element);
    }

    public static Pregunta getPregunta(String texto) {

        Pregunta salida = new PreguntaMultiple();
        texto = texto.replace("\n\n", "<br>");
        String[] partes = texto.split("\n");

        if (partes.length == 0) {
            return salida;
        }
        for (String s : partes) {
            System.out.println(s);
        }
        try {
            salida.setTitulo(partes[0]);
            salida.setEnunciado(partes[1]);
            List<Respuesta> respuestas = ((PreguntaMultiple) salida).getRespuestas();
            for (int i = 2; i < partes.length; i++) {
                int peso = 0;
                if (partes[i].charAt(0) == '+') {
                    peso = 100;
                    partes[i] = partes[i].substring(1);
                }
                if (partes[i].endsWith("<br>")) {
                    salida.setTerminada(true);
                    System.out.println("Terminada");
                    partes[i] = partes[i].substring(0, partes[i].length() - 8);
                    System.out.println(partes[i]);
                }
                respuestas.add(new Respuesta(peso, partes[i], ""));
            }

        } catch (IndexOutOfBoundsException i) {
            System.out.println("Terminado el parseo de la pregunta");
        }
        return salida;
    }

}

package net.miguelorrico.preguntas;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ItemFactory {
    public static Item getItem(Node nodo){

        System.out.println("Elemento:" + nodo.getNodeName());
        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) nodo;
            if(element.getAttribute("type").equals("category")){
                return new Categoria(element.getElementsByTagName("text").item(0).getTextContent());
            }
            return PreguntaFactory.getPregunta(element);
        }
        return null;
    }
}

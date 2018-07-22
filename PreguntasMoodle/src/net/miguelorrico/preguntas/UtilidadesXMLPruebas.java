package net.miguelorrico.preguntas;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilidadesXMLPruebas {



    public static List<Pregunta> leeXML(String nombreFichero){
        List<Pregunta> listaPreguntas = new ArrayList<>();
        Categoria categoriaActual=null;
        try {
            File archivo = new File(nombreFichero);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            NodeList listaNodos = document.getElementsByTagName("question");
            for (int temp = 0; temp < listaNodos.getLength(); temp++) {
                Node nodo = listaNodos.item(temp);
                Item preguntaLeida=ItemFactory.getItem(nodo);
                if(!(preguntaLeida instanceof Categoria)) {
                    Pregunta leida=(Pregunta)preguntaLeida;
                    leida.setCategoria(categoriaActual);
                    listaPreguntas.add(leida);
                } else {
                    System.out.println("Nueva Categoria:" + preguntaLeida);
                    categoriaActual=(Categoria)preguntaLeida;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Se han leido: "+listaPreguntas.size()+" preguntas");
        for(Pregunta p:listaPreguntas){
            System.out.println(p.cadenaPregunta());
            System.out.println(p);
        }
        return listaPreguntas;
    }

    public static Set<Categoria> obtenCategorias(List<Pregunta> listaPreguntas) {
        Set<Categoria> categorias = new HashSet<>();
        for (Pregunta p : listaPreguntas) {
            categorias.add(p.getCategoria());
        }
        return categorias;
    }

    public static void escribeXML(String nombreFichero,List<Pregunta> listaPreguntas) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Elemento raíz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("quiz");
            doc.appendChild(rootElement);
            doc.getDocumentElement().normalize();

            for (Categoria c : obtenCategorias(listaPreguntas)) {
                rootElement.appendChild(c.getElementXML(doc));
                for (int i = 0; i < listaPreguntas.size(); i++) {
                    rootElement.appendChild(listaPreguntas.get(i).getElementXML(doc));
                }
            }
            //Primer elemento
//            for (int i = 0; i < listaPreguntas.size(); i++) {
//                rootElement.appendChild(listaPreguntas.get(i).getElementXML(doc));
//            }

            //Se escribe el contenido del XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nombreFichero));
            transformer.transform(source, result);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UtilidadesXMLPruebas.leeXML("nuevo.xml");

    }

    protected static Element addConText(Document doc, String elemento, String valor, boolean format) {
        Element e = doc.createElement(elemento);
        if (format) {
            Attr attr = doc.createAttribute("format");
            attr.setValue("html");
            e.setAttributeNode(attr);

        }
        Element texto = doc.createElement("text");
        if (format) {
            Node cdata = doc.createCDATASection(valor);
            texto.appendChild(cdata);
        } else {
            texto.setTextContent(valor);
        }
        e.appendChild(texto);
        return e;
    }

    protected static Element addSinText(Document doc, String elemento, String valor, boolean format) {
        Element e = doc.createElement(elemento);
        if (format) {
            Attr attr = doc.createAttribute("format");
            attr.setValue("html");
            e.setAttributeNode(attr);
        }
        e.setTextContent(valor);
        return e;
    }
}

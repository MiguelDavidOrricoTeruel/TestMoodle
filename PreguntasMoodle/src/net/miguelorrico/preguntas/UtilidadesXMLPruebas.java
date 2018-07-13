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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    public static void escribeXML(String nombreFichero,List<Pregunta> listaPreguntas) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Elemento raíz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("quiz");
            doc.appendChild(rootElement);
            doc.getDocumentElement().normalize();
            //Primer elemento
            for (int i = 0; i < listaPreguntas.size(); i++) {
                rootElement.appendChild(listaPreguntas.get(i).getElementXML(doc));
            }

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
        UtilidadesXMLPruebas.leeXML("ejemplo2.xml");

    }
}

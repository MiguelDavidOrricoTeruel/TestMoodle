package net.miguelorrico.preguntas;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class Pregunta extends Item{
    protected final String titulo;
    protected final String enunciado;
    protected final String feedbackGeneral;
    protected final double notaPorDefecto;
    protected final double penalizacion;
    protected final int oculta;
    protected Categoria categoria;

    public Element getElementXML(Document doc){
        Element salida=doc.createElement("question");
        Attr attr = doc.createAttribute("type");
        attr.setValue("multichoice");
        salida.setAttributeNode(attr);
        salida.appendChild(addConText(doc,"name",this.titulo,false));
        salida.appendChild(addConText(doc,"questiontext",this.enunciado,true));
        salida.appendChild(addSinText(doc,"generalfeedback",""+this.feedbackGeneral,true));
        salida.appendChild(addSinText(doc,"defaultgrade",""+this.notaPorDefecto,false));
        salida.appendChild(addSinText(doc,"penalty",""+this.penalizacion,false));
        salida.appendChild(addSinText(doc,"hidden",""+this.oculta,false));

        return salida;
    }

    protected Element addConText(Document doc,String elemento,String valor,boolean format) {
        Element e=doc.createElement(elemento);
        if(format) {
            Attr attr = doc.createAttribute("format");
            attr.setValue("html");
            e.setAttributeNode(attr);

        }
        Element texto=doc.createElement("text");
        if(format){
            Node cdata = doc.createCDATASection(valor);
            texto.appendChild(cdata);
        } else {
            texto.setTextContent(valor);
        }
        e.appendChild(texto);
        return e;
    }
    protected Element addSinText(Document doc,String elemento,String valor,boolean format) {
        Element e=doc.createElement(elemento);
        if(format) {
            Attr attr = doc.createAttribute("format");
            attr.setValue("html");
            e.setAttributeNode(attr);
        }
        e.setTextContent(valor);
        return e;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Pregunta(Element element) {
        Node nodoName =element.getElementsByTagName("name").item(0);
        this.titulo = getStringTagText(nodoName);
        Node nodoQuestionText=element.getElementsByTagName("questiontext").item(0);
        this.enunciado=getStringTagText(nodoQuestionText);
        this.feedbackGeneral=getStringTagText(element.getElementsByTagName("generalfeedback").item(0));
        notaPorDefecto=Double.parseDouble(element.getElementsByTagName("defaultgrade").item(0).getTextContent());
        penalizacion=Double.parseDouble(element.getElementsByTagName("penalty").item(0).getTextContent());
        oculta=Integer.parseInt(element.getElementsByTagName("hidden").item(0).getTextContent());
    }

    protected String getStringTagText(Node nodoName) {
        if (nodoName.getNodeType() == Node.ELEMENT_NODE) {
            Element elementoName = (Element) nodoName;

            Node texto = elementoName.getElementsByTagName("text").item(0);
            return texto.getTextContent();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "titulo='" + titulo + '\'' +
                ", enunciado='" + enunciado + '\'' +
                ", feedbackGeneral='" + feedbackGeneral + '\'' +
                ", notaPorDefecto=" + notaPorDefecto +
                ", penalizacion=" + penalizacion +
                ", oculta=" + oculta +
                ", categoria=" + categoria +
                '}';
    }

    public String cadenaPregunta(){
        String salida="";
        salida+="TITULO DE LA PREGUNTA: "+this.titulo+" Categoría: "+this.categoria;
        salida+="\nENUNCIADO: "+this.enunciado;
        return salida;

    }
}

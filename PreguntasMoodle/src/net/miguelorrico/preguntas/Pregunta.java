package net.miguelorrico.preguntas;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static net.miguelorrico.preguntas.UtilidadesXMLPruebas.addConText;
import static net.miguelorrico.preguntas.UtilidadesXMLPruebas.addSinText;

public abstract class Pregunta extends Item {

    private static final int ANTES_ELIPSIS = 50;

    protected  String titulo;
    protected  String enunciado;
    protected  String feedbackGeneral;
    protected  double notaPorDefecto;
    protected  double penalizacion;
    protected  int oculta;
    protected  Categoria categoria;
    protected boolean terminada=false;


    public Pregunta(String titulo, String enunciado, String feedbackGeneral, double notaPorDefecto, double penalizacion, int oculta) {
        this.titulo = titulo;
        this.enunciado = enunciado;
        this.feedbackGeneral = feedbackGeneral;
        this.notaPorDefecto = notaPorDefecto;
        this.penalizacion = penalizacion;
        this.oculta = oculta;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }

    public Pregunta() {
        this("","","",0,0,0);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getFeedbackGeneral() {
        return feedbackGeneral;
    }

    public void setFeedbackGeneral(String feedbackGeneral) {
        this.feedbackGeneral = feedbackGeneral;
    }

    public double getNotaPorDefecto() {
        return notaPorDefecto;
    }

    public void setNotaPorDefecto(double notaPorDefecto) {
        this.notaPorDefecto = notaPorDefecto;
    }

    public double getPenalizacion() {
        return penalizacion;
    }

    public void setPenalizacion(double penalizacion) {
        this.penalizacion = penalizacion;
    }

    public int getOculta() {
        return oculta;
    }

    public void setOculta(int oculta) {
        this.oculta = oculta;
    }

    public Element getElementXML(Document doc) {
        Element salida = doc.createElement("question");
        Attr attr = doc.createAttribute("type");
        attr.setValue("multichoice");
        salida.setAttributeNode(attr);
        salida.appendChild(addConText(doc, "name", this.titulo, false));
        salida.appendChild(addConText(doc, "questiontext", this.enunciado, true));
        salida.appendChild(addConText(doc, "generalfeedback", "" + this.feedbackGeneral, true));
        salida.appendChild(addSinText(doc, "defaultgrade", "" + this.notaPorDefecto, false));
        salida.appendChild(addSinText(doc, "penalty", "" + this.penalizacion, false));
        salida.appendChild(addSinText(doc, "hidden", "" + this.oculta, false));

        return salida;
    }



    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        categoria.addPregunta(this);
    }

    public Pregunta(Element element) {
        Node nodoName = element.getElementsByTagName("name").item(0);
        this.titulo = getStringTagText(nodoName);
        Node nodoQuestionText = element.getElementsByTagName("questiontext").item(0);
        this.enunciado = getStringTagText(nodoQuestionText);
        this.feedbackGeneral = getStringTagText(element.getElementsByTagName("generalfeedback").item(0));
        notaPorDefecto = Double.parseDouble(element.getElementsByTagName("defaultgrade").item(0).getTextContent());
        penalizacion = Double.parseDouble(element.getElementsByTagName("penalty").item(0).getTextContent());
        oculta = Integer.parseInt(element.getElementsByTagName("hidden").item(0).getTextContent());
    }

    protected String getStringTagText(Node nodoName) {
        if (nodoName.getNodeType() == Node.ELEMENT_NODE) {
            Element elementoName = (Element) nodoName;

            Node texto = elementoName.getElementsByTagName("text").item(0);
//            System.out.println("Nodo texto"+texto);
            return texto.getTextContent() == null ? "" : texto.getTextContent();
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

    public String cadenaPregunta() {
        String salida = "";
        salida += "TITULO DE LA PREGUNTA: " + this.titulo + " Categoría: " + this.categoria;
        salida += "\nENUNCIADO: " + this.enunciado;
        return salida;
    }

    public String getEnunciadoElipsis() {

        int limit = ANTES_ELIPSIS - 3;
        String sinHTML=this.enunciado.replaceAll("\\<.*?>","");
        return (sinHTML.length() > ANTES_ELIPSIS) ? sinHTML.substring(0, limit) + "..." : sinHTML;

    }

    public String htmlPregunta() {
        String salida="<h1>Vista previa de la pregunta</h1>";
salida+="<h2>"+this.titulo+"</h2>";
salida+=this.enunciado;

        return salida;
    }

    public String preguntaATexto() {
        String salida = "";
        salida += this.getTitulo().replace("<br>", "\n\n");
        return salida;
    }

}

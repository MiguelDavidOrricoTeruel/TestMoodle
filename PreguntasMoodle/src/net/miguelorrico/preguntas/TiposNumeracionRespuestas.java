package net.miguelorrico.preguntas;

public enum TiposNumeracionRespuestas {
    //TODO cambiar para que no tenga límite de cinco
    NINGUNA("none", new char[]{' ', ' ', ' ', ' ',' '}),
    MINUSCULAS("abc", new char[]{'a', 'b', 'c','d','e'}),
    MAYUSCULAS("ABCD", new char[]{'A', 'B', 'C','D','E'}),
    NUMEROS("123", new char[]{'1', '2', '3','4','5'});
    private final char[] numeracion;
    String textoMostrado;

    TiposNumeracionRespuestas(String s, char[] chars) {
        this.textoMostrado=s;
        this.numeracion=chars;
    }

    public char getNumeracion(int i){
        return this.numeracion[i-1];
    }

    public String getTextoMostrado() {
        return textoMostrado;
    }
}

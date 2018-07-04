package net.miguelorrico.preguntas;

public class Categoria extends Item {
    private final String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}

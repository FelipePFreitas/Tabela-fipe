package br.com.alura.models;

import com.fasterxml.jackson.annotation.JsonAlias;


public record Marcas(
        @JsonAlias("codigo") String codigo,
        @JsonAlias("nome") String marca) {

    @Override
    public String toString() {
        return "Marcas: " + "Código = " + codigo + " | Marca = " + marca;
    }
}

package br.com.alura.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Modelos(
        @JsonAlias("codigo") String codigo,
        @JsonAlias("nome") String modelo) {

    @Override
    public String toString() {
        return "Modelo: " + "Código = " + codigo + " | Modelo = " + modelo;
    }
}


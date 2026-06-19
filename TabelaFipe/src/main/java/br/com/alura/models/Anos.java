package br.com.alura.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Anos(@JsonAlias("codigo") String codigo,
                   @JsonAlias("nome") String ano) {
    @Override
    public String toString() {
        return "Marcas: " + "Código = " + codigo + " | Ano = " + ano;
    }
}

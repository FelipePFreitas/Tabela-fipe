package br.com.alura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Valor(
        @JsonAlias("TipoVeiculo") String tipoVeiculo,
        @JsonAlias("Valor") String valor,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") String anoModelo,
        @JsonAlias({"Combustível", "Combustivel", "Combustivo"}) String combustivel,
        @JsonAlias({"CodigoFipe", "CódigoFipe"}) String codigoFipe,
        @JsonAlias("MesReferencia") String mesReferencia,
        @JsonAlias({"SiglaCombustivel", "SigleCombustivel"}) String siglaCombustivel) {

    public Valor comCombustivelNormalizado() {
        if (modelo == null) {
            return this;
        }
        if (modelo.contains("Flex")) {
            return new Valor(tipoVeiculo, valor, marca, modelo, anoModelo, "Flex", codigoFipe, mesReferencia, "F");
        }
        if (modelo.toLowerCase().contains("dies")) {
            return new Valor(tipoVeiculo, valor, marca, modelo, anoModelo, "Diesel", codigoFipe, mesReferencia, "D");
        }
        return this;
    }
}

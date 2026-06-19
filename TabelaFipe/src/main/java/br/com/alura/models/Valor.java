package br.com.alura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Valor {
    @JsonAlias("TipoVeiculo")
    String tipoVeiculo;
    @JsonAlias("Valor")
    String valor;
    @JsonAlias("Marca")
    String marca;
    @JsonAlias("Modelo")
    String modelo;
    @JsonAlias("AnoModelo")
    String anoModelo;
    @JsonAlias("{Combustível,Combustivo}")
    String combustivel;
    @JsonAlias("{CodigoFipe,CódigoFipe}")
    String codigoFipe;
    @JsonAlias("MesReferencia")
    String mesReferencia;
    @JsonAlias("SigleCombustivel")
    String siglaCombustivel;

    @Override
    public String toString() {
        return "Tipo do Veiculo: Carro " +
                "\n| Valor:  " + valor +
                "\n| Marca: " + marca +
                "\n| Modelo: " + modelo +
                "\n| Ano Modelo: " + anoModelo +
                "\n| Combustível: " + combustivel +
                "\n| Código FIPE: " + codigoFipe +
                "\n| Mês referencia: " + mesReferencia +
                "\n| Sigla combustível: " + siglaCombustivel;
    }
}

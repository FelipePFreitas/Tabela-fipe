package br.com.alura.service;

import br.com.alura.models.Anos;
import br.com.alura.models.Marcas;
import br.com.alura.models.Modelos;
import br.com.alura.models.RespostaModelos;
import br.com.alura.models.Valor;
import br.com.alura.util.ConsumoAPI;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CarrosService {
    private final ConsumoAPI consumoAPI;

    public CarrosService(ConsumoAPI consumoAPI) {
        this.consumoAPI = consumoAPI;
    }

    public List<Marcas> consultarMarcas() {
        return Arrays.asList(consumoAPI.obterDados("/carros/marcas", Marcas[].class));
    }

    public List<Modelos> consultarModelos(String codigoMarca) {
        return consumoAPI.obterDados("/carros/marcas/" + codigoMarca + "/modelos", RespostaModelos.class)
                .modelos();
    }

    public List<Anos> consultarAnos(String codigoMarca, String codigoModelo) {
        return Arrays.asList(consumoAPI.obterDados(
                "/carros/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos", Anos[].class));
    }

    public Valor consultarValor(String codigoMarca, String codigoModelo, String codigoAno) {
        var valor = consumoAPI.obterDados(
                "/carros/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/" + codigoAno,
                Valor.class);
        return valor.comCombustivelNormalizado();
    }
}

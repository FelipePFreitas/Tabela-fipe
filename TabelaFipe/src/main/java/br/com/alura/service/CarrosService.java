package br.com.alura.service;

import br.com.alura.models.*;
import br.com.alura.util.ConsumoAPI;
import br.com.alura.util.ConverterDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarrosService {
    private final Scanner scanner = new Scanner(System.in);
    private final ConverterDados converterDados = new ConverterDados();
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String MODELOS = "https://parallelum.com.br/fipe/api/v1/carros/marcas";

    public void consultaMarcasCarros() throws JsonProcessingException {

        String json = consumoAPI.obterDados(MODELOS);
        Marcas[] marcasConvertidas = converterDados.obterDados(json, Marcas[].class);
        List<Marcas> marcas = Arrays.asList(marcasConvertidas);
        marcas.forEach(System.out::println);

        System.out.println("Digite o código da marca");
        String entradaMarca = scanner.nextLine();

        Optional<Marcas> marcasOptional = marcas.stream().filter(f -> f.codigo().equalsIgnoreCase(entradaMarca)).findFirst();

        if (marcasOptional.isPresent()) {
            final String ENTRADA_MARCA = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" + entradaMarca + "/modelos";
            String jsonMarca = consumoAPI.obterDados(ENTRADA_MARCA);
            RespostaModelos resposta = converterDados.obterDados(jsonMarca, RespostaModelos.class);
            List<Modelos> listaModelos = resposta.modelos();
            listaModelos.forEach(System.out::println);

            System.out.println("Digite o código do modelo");
            String entradaModelo = scanner.nextLine();

            Optional<Modelos> modelosOptional =
                    listaModelos.stream().filter(f -> f.codigo().equalsIgnoreCase(entradaModelo)).findFirst();
            if (modelosOptional.isPresent()) {
                final String ENTRADA_MODELO = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" + entradaMarca +
                        "/modelos/" + entradaModelo + "/anos";
                String jsonModelo = consumoAPI.obterDados(ENTRADA_MODELO);
                Anos[] respostaAnos = converterDados.obterDados(jsonModelo, Anos[].class);
                List<Anos> listAnos = Arrays.asList(respostaAnos);
                listAnos.forEach(System.out::println);

                System.out.println("Digite o código de qual modelo deseja saber");
                String entradaCodigo = scanner.nextLine();
                final String ENTRADA_VALOR = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" + entradaMarca +
                        "/modelos/" + entradaModelo + "/anos/" + entradaCodigo;
                String jsonValor = consumoAPI.obterDados(ENTRADA_VALOR);
                Valor respostaValor = converterDados.obterDados(jsonValor, Valor.class);

                if (respostaValor.getModelo().contains("Flex")) {
                    respostaValor.setCombustivel("Flex");
                    respostaValor.setSiglaCombustivel("F");
                }else if (respostaValor.getModelo().toLowerCase().contains("dies")){
                    respostaValor.setCombustivel("Diesel");
                    respostaValor.setSiglaCombustivel("D");
                }


                System.out.println(respostaValor);

            } else {
                System.out.println("Modelo não encontrado");
            }
        } else {
            System.out.println("Cógido da marca não encontrado");
        }


    }


}

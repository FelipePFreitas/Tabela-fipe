package br.com.alura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ConsumoAPI {
    private final RestClient restClient;

    public ConsumoAPI(@Value("${fipe.api.base-url:https://parallelum.com.br/fipe/api/v1}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public <T> T obterDados(String caminho, Class<T> tipo) {
        try {
            return restClient.get().uri(caminho).retrieve().body(tipo);
        } catch (RuntimeException exception) {
            throw new FipeApiException("Não foi possível consultar a API FIPE.", exception);
        }
    }

    public static class FipeApiException extends RuntimeException {
        public FipeApiException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

# 🚗 Consulta Tabela FIPE

Aplicação web para consultar preços médios de carros usando a API da Tabela FIPE. O projeto possui um back-end em Spring Boot e um front-end em Angular.

## Tecnologias

- Java 21
- Spring Boot 3.4.5
- Angular 20
- API externa: [Parallelum FIPE](https://parallelum.com.br/fipe/api/v1)

## Funcionalidades

- Listagem de marcas de carros
- Listagem de modelos por marca
- Listagem de anos e combustíveis por modelo
- Consulta do valor médio do veículo
- Normalização do combustível retornado pela API

## Como executar

### Pré-requisitos

- JDK 21
- Node.js e npm

### Back-end

Na raiz do projeto Java:

```bash
cd TabelaFipe
mvn spring-boot:run
```

O back-end ficará disponível em `http://localhost:8080`.

Caso o Maven não esteja configurado no PATH, execute o JAR compilado:

```bash
cd TabelaFipe
java -jar target/desafio-tabela-fipe-1.0.0.jar
```

### Front-end

Em outro terminal:

```bash
cd frontend
npm install
npm start
```

Acesse `http://localhost:4200`.

O front-end usa `frontend/proxy.conf.json` para encaminhar as requisições `/api` para o back-end em `http://localhost:8080`. Portanto, os dois serviços precisam estar em execução.

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/fipe/carros/marcas` | Lista as marcas |
| GET | `/api/fipe/carros/marcas/{codigoMarca}/modelos` | Lista os modelos |
| GET | `/api/fipe/carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos` | Lista os anos disponíveis |
| GET | `/api/fipe/carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{codigoAno}` | Consulta o valor do veículo |

Documentação OpenAPI disponível em `http://localhost:8080/swagger-ui/index.html`.

## Estrutura

```text
TabelaFipe/
└── src/main/java/br/com/alura/
    ├── controller/   # Endpoints REST
    ├── models/       # Modelos de resposta
    ├── service/      # Regras de consulta
    └── util/         # Consumo e conversão da API FIPE

frontend/
└── src/app/
    ├── app.component.ts
    └── models/
```

## Configuração

A URL da API FIPE pode ser alterada em:

```text
TabelaFipe/src/main/resources/application.properties
```

```properties
fipe.api.base-url=https://parallelum.com.br/fipe/api/v1
```

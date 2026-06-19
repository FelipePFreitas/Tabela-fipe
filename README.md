# 🚗 Consulta Tabela FIPE - API Client

![Java](https://shields.io)
![Apache Maven](https://shields.io)

Aplicação interativa via console desenvolvida em Java para consulta de preços médios de veículos no mercado brasileiro, consumindo dados em tempo real da API da Tabela FIPE. O fluxo guia o usuário desde a seleção da marca até o detalhamento de preços por ano-modelo.

## 🚀 Funcionalidades

* 🏢 **Listagem de Marcas**: Exibe e valida o catálogo de marcas de veículos.
* 🚘 **Filtragem Avançada**: Filtra os modelos de carros dinamicamente por trecho de texto (ignora maiúsculas/minúsculas).
* 📅 **Busca de Anos**: Identifica as variações de anos e tipos de combustível disponíveis para um veículo específico.
* 🛠️ **Tratamento de Dados Inconsistentes**: Varre e corrige dinamicamente falhas de preenchimento oriundas do JSON da API (ex: inferência automática de combustível caso o campo retorne nulo).

## 🛠️ Tecnologias e Conceitos Utilizados

* **Java 17** (Records, Text Blocks, Generics).
* **Jackson Databind**: Manipulação, mapeamento e desserialização customizada de payloads JSON complexos.
* **Java Streams API & Optional**: Filtragens expressivas com `.filter()`, `.findFirst()` e prevenção de `NullPointerException`.
* **Clean Code**: Separação de responsabilidades utilizando DTOs e inversão simples de controle.

## 🧱 Arquitetura de Mapeamento (JSON Parser)

Para contornar as variações de estruturas enviadas pela API (ora objetos isolados, ora arrays diretos), a aplicação foi desenhada com flexibilidade:

1. **`Marcas` / `Modelos` / `DadosAnos`**: Implementados como `record` para garantir imutabilidade no tráfego dos nós de dados.
2. **`RespostaModelos`**: Wrapper de encapsulamento criado para ler nós de dados aninhados enviados pela API.
3. **`Valor`**: Modelado como uma classe tradicional para permitir manipulação dinâmica e aplicação de regras de negócio (`Setters`) durante a desserialização do Jackson, tratando erros ortográficos e dados ausentes da API.

---

## ⚙️ Configuração do Projeto

### Dependência Maven (`pom.xml`)
Insira a dependência do Jackson Databind no escopo do seu projeto:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.2</version>
</dependency>
```

### Como Executar

1. Certifique-se de possuir o **JDK 17** configurado na sua máquina.
2. Baixe ou clone o repositório:
   ```bash
   git clone https://github.com
   ```
3. Abra em sua IDE favorita (IntelliJ IDEA, Eclipse, NetBeans).
4. Execute o arquivo `Main.java` e interaja pelo terminal de comando.

---

## 📋 Demonstração de Uso no Console

```text
--- Marcas Disponíveis ---
Código: 21 | Nome: Fiat
Código: 22 | Nome: Ford
Código: 59 | Nome: VW - Volkswagen

Digite o código da marca desejada: 
> 59

--- Modelos desta marca ---
Modelo: Código = 7524 | Modelo = VOYAGE Trendline 1.0 T.Flex 12V 4p
Modelo: Código = 6809 | Modelo = VOYAGE Trendline 1.6 T.Flex 8V 4p

Digite um trecho do nome do carro para busca (ex: Palio):
> Voyage

Modelos encontrados:
Modelo: Código = 6809 | Modelo = VOYAGE Trendline 1.6 T.Flex 8V 4p

Digite o código do modelo desejado para ver os anos:
> 6809

--- Anos disponíveis para este modelo ---
Código: 2015-1 | Nome: 2015 Gasolina

Digite o código de qual modelo deseja saber:
> 2015-1

--- DADOS DO VEÍCULO ---
Valor: R\$ 41.592,00
Marca: VW - Volkswagen
Modelo: VOYAGE Trendline 1.6 T.Flex 8V 4p
Ano Modelo: 2015
Combustível: Flex
Código FIPE: 005380-5
Mês referência: junho de 2026
```

---
Desenhado como parte de estudos práticos em integração de APIs e manipulação de JSON no ecossistema Java.

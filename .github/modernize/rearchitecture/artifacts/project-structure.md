# Project structure

## Project type

Small Java 21 command-line application using Maven. The runtime entry point is `br.com.alura.Main`; the application has one interactive vehicle-consultation flow.

## Functional domain

- Vehicle price lookup through the public FIPE API.
- Hierarchical lookup: car brands, models, model years, then vehicle price.

## Layers observed

- Entry point: `Main`.
- Orchestration: `CarrosService`.
- External integration and serialization: `ConsumoAPI`, `ConverterDados`, `IConverterDados`.
- Data contracts: records/classes under `models`.

## Spring Boot target

The CLI boundary is replaced by HTTP resources under `/api/fipe/carros`. The FIPE integration remains external and configurable through `fipe.api.base-url`.

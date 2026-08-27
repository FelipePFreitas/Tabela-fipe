# Data model

The project has transport models rather than persisted entities:

- `Marcas`: FIPE brand code and name.
- `Modelos`: model code and name.
- `RespostaModelos`: response envelope containing model list.
- `Anos`: year code and display name.
- `Valor`: vehicle type, price, brand, model, year, fuel, FIPE code and reference month.

There is no database, ORM, repository, or local persistence. The model relationships are hierarchical through FIPE path parameters: brand -> model -> year -> price.

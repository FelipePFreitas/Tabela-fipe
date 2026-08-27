# Implementation Guide

This index is not the full contract. Implementation agents must read the listed artifact paths before changing the corresponding unit.

## consulta-veiculos

- Read `unit_graph.yaml` to preserve the entrypoint inputs and outputs.
- Read `units/consulta-veiculos/behavior.yaml` for side effects, branches, and error behavior.
- Read `units/consulta-veiculos/bindings.yaml` for framework wiring and runtime configuration.
- Read `units/consulta-veiculos/unit_decomposition.yaml` for design-only split candidates; `commit` is false.
- Filter `wire_contracts.yaml` to rows whose `source_loc` belongs to this unit.
- Filter `shared_modules.yaml` to `used_by_units` containing `consulta-veiculos`.
- Read `migration_boundary.yaml`; implement only `must_rewrite`, while preserving the listed legacy allowance.
- Completion evidence: Spring Boot starts, all four GET resources are wired, FIPE base URL is configurable, and upstream failures are surfaced.

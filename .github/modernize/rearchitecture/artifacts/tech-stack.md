# Technology stack

- Java 21.
- Maven.
- Jackson databind, previously instantiated manually; Spring Boot now supplies the configured mapper.
- Spring Boot Web and Validation.
- Springdoc OpenAPI for API documentation.
- External dependency: `https://parallelum.com.br/fipe/api/v1`.

## Migration blockers and constraints

- The existing flow is synchronous and depends on the FIPE response shape.
- FIPE endpoint failures must remain visible as an API error rather than being silently ignored.
- Existing Portuguese model fields and FIPE aliases are part of the data contract.

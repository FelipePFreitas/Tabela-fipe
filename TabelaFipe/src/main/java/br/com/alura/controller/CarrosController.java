package br.com.alura.controller;

import br.com.alura.models.Anos;
import br.com.alura.models.Marcas;
import br.com.alura.models.Modelos;
import br.com.alura.models.Valor;
import br.com.alura.service.CarrosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fipe/carros")
@Tag(name = "Tabela FIPE", description = "Consultas de veículos na Tabela FIPE")
public class CarrosController {
    private final CarrosService carrosService;

    public CarrosController(CarrosService carrosService) {
        this.carrosService = carrosService;
    }

    @GetMapping("/marcas")
    @Operation(summary = "Lista as marcas de carros")
    public List<Marcas> consultarMarcas() {
        return carrosService.consultarMarcas();
    }

    @GetMapping("/marcas/{codigoMarca}/modelos")
    @Operation(summary = "Lista os modelos de uma marca")
    public List<Modelos> consultarModelos(@PathVariable String codigoMarca) {
        return carrosService.consultarModelos(codigoMarca);
    }

    @GetMapping("/marcas/{codigoMarca}/modelos/{codigoModelo}/anos")
    @Operation(summary = "Lista os anos de um modelo")
    public List<Anos> consultarAnos(@PathVariable String codigoMarca, @PathVariable String codigoModelo) {
        return carrosService.consultarAnos(codigoMarca, codigoModelo);
    }

    @GetMapping("/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{codigoAno}")
    @Operation(summary = "Consulta o valor de um veículo")
    public Valor consultarValor(@PathVariable String codigoMarca,
                                @PathVariable String codigoModelo,
                                @PathVariable String codigoAno) {
        return carrosService.consultarValor(codigoMarca, codigoModelo, codigoAno);
    }
}

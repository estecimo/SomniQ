package com.example.credito.interfaces.rest;

import com.example.credito.application.ClasificarCreditoUseCase;
import com.example.credito.domain.CreditoCaso;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credito")
@CrossOrigin
public class CreditoController {

    private final ClasificarCreditoUseCase useCase;

    public CreditoController(ClasificarCreditoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/clasificar")
    public CreditoResponseDTO clasificar(@RequestBody CreditoRequestDTO req) throws Exception {

        CreditoCaso caso = new CreditoCaso(
                req.edad,
                req.ingresos,
                req.antiguedadLaboral,
                req.tieneDeudas,
                req.historial
        );

        return new CreditoResponseDTO(useCase.ejecutar(caso));
    }
}

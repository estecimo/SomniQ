package com.example.credito.application;

import com.example.credito.domain.CreditoCaso;
import com.example.credito.infrastructure.weka.WekaModeloCreditoService;
import org.springframework.stereotype.Service;

@Service
public class ClasificarCreditoUseCase {

    private final WekaModeloCreditoService modelo;

    public ClasificarCreditoUseCase(WekaModeloCreditoService modelo) {
        this.modelo = modelo;
    }

    public String ejecutar(CreditoCaso caso) throws Exception {
        return modelo.clasificar(caso);
    }
}

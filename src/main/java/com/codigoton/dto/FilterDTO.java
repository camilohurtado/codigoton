package com.codigoton.dto;

import lombok.*;

/**
 * @author Camilo Hurtado
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

    private String mesa;
    private int tipoDeCliente;
    private String codUbicacionGeo;
    private double rangoInicialBalance;
    private double rangoFinalBalance;

}

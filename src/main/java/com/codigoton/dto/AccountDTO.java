package com.codigoton.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author Camilo Hurtado
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private long id;
    private long clientId;
    private double balance;
}

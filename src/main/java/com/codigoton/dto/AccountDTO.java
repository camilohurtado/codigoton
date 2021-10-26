package com.codigoton.dto;

import lombok.*;

/**
 * @author Camilo Hurtado
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDTO {
    private long id;
    private long clientId;
    private double balance;
}

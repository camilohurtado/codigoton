package com.codigoton.persistence.model;

import com.codigoton.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Camilo Hurtado
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column private long clientId;
    @Column private double balance;

    public static AccountDTO toDTO(Account account){
        return AccountDTO.builder()
                .id(account.id)
                .balance(account.balance)
                .clientId(account.clientId)
                .build();
    }
}

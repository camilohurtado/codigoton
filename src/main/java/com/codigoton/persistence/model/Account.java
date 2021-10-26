package com.codigoton.persistence.model;

import com.codigoton.dto.AccountDTO;
import lombok.*;

import javax.persistence.*;

/**
 * @author Camilo Hurtado
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "client_id") private long clientId;
    @Column(name = "balance") private double balance;

    public static AccountDTO toDTO(Account account){
        return AccountDTO.builder()
                .id(account.id)
                .balance(account.balance)
                .clientId(account.clientId)
                .build();
    }
}

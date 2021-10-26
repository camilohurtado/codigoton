package com.codigoton.persistence.model;

import com.codigoton.dto.AccountDTO;
import com.codigoton.dto.ClientDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Camilo Hurtado
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column private String code;
    @Column private char male;
    @Column private int type;
    @Column private String location;
    @Column private String company;
    @Column private char encrypt;

    public static ClientDTO toDTO(Client client){

        return ClientDTO.builder()
                .id(client.id)
                .code(client.code)
                .company(client.company)
                .encrypt(client.encrypt)
                .location(client.location)
                .male(client.male)
                .type(client.type)
                .build();
    }
}

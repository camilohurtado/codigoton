package com.codigoton.dto;

import lombok.*;

import java.util.List;

/**
 * @author Camilo Hurtado
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientDTO {

    private long id;
    private String code;
    private char male;
    private int type;
    private String location;
    private String company;
    private char encrypt;

    private List<AccountDTO> accounts;
}

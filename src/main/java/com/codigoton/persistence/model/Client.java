package com.codigoton.persistence.model;

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
}

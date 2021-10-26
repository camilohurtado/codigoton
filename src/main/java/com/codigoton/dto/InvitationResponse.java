package com.codigoton.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Camilo Hurtado
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationResponse {
    private String table;
    private String status;
    private List<String> listOfGuests;
}

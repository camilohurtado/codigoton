package com.codigoton.service.invitation;

import com.codigoton.dto.AccountDTO;
import com.codigoton.dto.ClientDTO;
import com.codigoton.dto.FilterDTO;
import com.codigoton.dto.InvitationResponse;
import com.codigoton.persistence.model.Account;
import com.codigoton.service.information.ClientInfoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvitationServiceImplTest {

    @Mock
    ClientInfoService clientInfoServiceMock;

    InvitationService sut;

    @BeforeEach
    void setUp() {
        sut = new InvitationServiceImpl(clientInfoServiceMock);
    }

    @Test
    void setupInvitationsNullFilter() {
        List<InvitationResponse> result = sut.setupInvitations(null);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void setupInvitationsEmptyFilter() {
        List<InvitationResponse> result = sut.setupInvitations(new ArrayList<>());
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void setupInvitationsLessThan4ClientsNotNullResult() {
        Mockito.when(clientInfoServiceMock.getAllClientsByFilter(Mockito.anyString(), Mockito.anyInt())).thenReturn(getLessThan4Clients());

        FilterDTO filterDTO1 = FilterDTO.builder()
                .tipoDeCliente(1)
                .rangoInicialBalance(1000)
                .rangoFinalBalance(2000)
                .codUbicacionGeo("01")
                .mesa("MESA01")
                .build();

        List<InvitationResponse> result = sut.setupInvitations(List.of(filterDTO1));
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void setupInvitationsLessThan4ClientsValidateResult() {
        Mockito.when(clientInfoServiceMock.getAllClientsByFilter(Mockito.anyString(), Mockito.anyInt())).thenReturn(getLessThan4Clients());

        FilterDTO filterDTO1 = FilterDTO.builder()
                .tipoDeCliente(1)
                .rangoInicialBalance(1000)
                .rangoFinalBalance(2000)
                .codUbicacionGeo("01")
                .mesa("MESA01")
                .build();

        List<InvitationResponse> result = sut.setupInvitations(List.of(filterDTO1));

        Assertions.assertThat(result)
                .contains(InvitationResponse.builder().status("CANCELADA").table("MESA01").build());
    }

    @Test
    void setupInvitationsMoreThan4ClientsNotNull() {
        Mockito.when(clientInfoServiceMock.getAllClientsByFilter(Mockito.anyString(), Mockito.anyInt())).thenReturn(getClients());

        FilterDTO filterDTO1 = FilterDTO.builder()
                .tipoDeCliente(1)
                .rangoInicialBalance(1000)
                .rangoFinalBalance(2000)
                .codUbicacionGeo("01")
                .mesa("MESA01")
                .build();

        List<InvitationResponse> result = sut.setupInvitations(List.of(filterDTO1));

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void setupInvitationsMoreThan4ClientsValidateResponse() {
        Mockito.when(clientInfoServiceMock.getAllClientsByFilter(Mockito.anyString(), Mockito.anyInt())).thenReturn(getClients());

        FilterDTO filterDTO1 = FilterDTO.builder()
                .tipoDeCliente(1)
                .rangoInicialBalance(1000)
                .rangoFinalBalance(2000)
                .codUbicacionGeo("01")
                .mesa("MESA01")
                .build();

        List<InvitationResponse> result = sut.setupInvitations(List.of(filterDTO1));

        //TODO - Finish unit test that validates business logic correctness

    }

    private List<ClientDTO> getLessThan4Clients(){

        AccountDTO accountDTO1 = AccountDTO.builder()
                .clientId(1)
                .balance(300)
                .id(1)
                .build();

        AccountDTO accountDTO2 = AccountDTO.builder()
                .clientId(1)
                .balance(1000)
                .id(2)
                .build();

        AccountDTO accountDTO3 = AccountDTO.builder()
                .clientId(2)
                .balance(500)
                .id(3)
                .build();


        AccountDTO accountDTO4 = AccountDTO.builder()
                .clientId(3)
                .balance(1900)
                .id(4)
                .build();


        AccountDTO accountDTO5 = AccountDTO.builder()
                .clientId(3)
                .balance(100)
                .id(5)
                .build();

        ClientDTO clientDTO1 = ClientDTO.builder()
                .id(1)
                .type(1)
                .male('1')
                .code("cod01")
                .company("comp01")
                .location("01")
                .accounts(List.of(accountDTO1, accountDTO2))
                .build();

        ClientDTO clientDTO2 = ClientDTO.builder()
                .id(2)
                .type(2)
                .male('0')
                .code("cod02")
                .company("comp01")
                .location("01")
                .accounts(List.of(accountDTO3))
                .build();

        ClientDTO clientDTO3 = ClientDTO.builder()
                .id(3)
                .type(1)
                .male('1')
                .code("cod03")
                .company("comp01")
                .location("02")
                .accounts(List.of(accountDTO4, accountDTO5))
                .build();

        return List.of(clientDTO1, clientDTO2, clientDTO3);
    }

    private List<ClientDTO> getClients(){
        AccountDTO accountDTO1 = AccountDTO.builder()
                .clientId(1)
                .balance(2000)
                .id(1)
                .build();


        ClientDTO clientDTO1 = ClientDTO.builder()
                .id(5)
                .type(1)
                .male('0')
                .code("cod05")
                .company("comp04")
                .location("01")
                .accounts(List.of(accountDTO1))
                .build();

        List<ClientDTO> clientDTOS = new ArrayList<>();
        clientDTOS.add(clientDTO1);
        clientDTOS.addAll(getLessThan4Clients());

        return clientDTOS;
    }
}
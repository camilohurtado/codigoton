package com.codigoton.service.invitation;

import com.codigoton.dto.AccountDTO;
import com.codigoton.dto.ClientDTO;
import com.codigoton.dto.FilterDTO;
import com.codigoton.dto.InvitationResponse;
import com.codigoton.persistence.model.Account;
import com.codigoton.service.information.ClientInfoService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectEnumerableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

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
    void setupInvitationsMoreThan4ClientsNotEmpty() {
        Mockito.when(clientInfoServiceMock.getAllClientsByFilter(Mockito.anyString(), Mockito.anyInt())).thenReturn(getClients());

        FilterDTO filterDTO1 = FilterDTO.builder()
                .tipoDeCliente(1)
                .rangoInicialBalance(1000)
                .rangoFinalBalance(2000)
                .codUbicacionGeo("01")
                .mesa("MESA01")
                .build();

        List<InvitationResponse> result = sut.setupInvitations(List.of(filterDTO1));

        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    void setupInvitationsMoreThan4ClientsValidateTablesInResponse() {
        Mockito.when(clientInfoServiceMock.getAllClientsByFilter(Mockito.anyString(), Mockito.anyInt())).thenReturn(getClients());

        FilterDTO filterDTO1 = FilterDTO.builder()
                .tipoDeCliente(1)
                .rangoInicialBalance(1000)
                .rangoFinalBalance(2000)
                .codUbicacionGeo("01")
                .mesa("MESA01")
                .build();

        List<FilterDTO> filters = List.of(filterDTO1);

        List<InvitationResponse> result = sut.setupInvitations(filters);

        Assertions.assertThat(result)
                        .extracting("table", String.class)
                        .contains((filters.stream().map(filterDTO -> filterDTO.getMesa()).collect(Collectors.toList()).toArray(new String[0])));
    }

    @Test
    void setupInvitationsMoreThan4ClientsValidateListOfGuests_expectedSize() {
        Mockito.when(clientInfoServiceMock.getAllClientsByFilter(Mockito.anyString(), Mockito.anyInt())).thenReturn(getClients());

        FilterDTO filterDTO1 = FilterDTO.builder()
                .tipoDeCliente(1)
                .rangoInicialBalance(1000)
                .rangoFinalBalance(2000)
                .codUbicacionGeo("01")
                .mesa("MESA01")
                .build();

        List<FilterDTO> filters = List.of(filterDTO1);

        List<InvitationResponse> result = sut.setupInvitations(filters);

        List<List<String>> expectedListOfGuests = List.of(List.of("cod11", "cod01", "cod03", "cod06", "cod05", "cod07", "cod09"));

        Assertions.assertThat(result)
                    .map(InvitationResponse::getListOfGuests)
                    .isEqualTo(expectedListOfGuests);

    }

    /**
     * Get less than 4 clients as follows (Client code = (account#:balance))
     *
     * cod01, male = (account1:300), (account2:1000)
     * cod02, women = (account3:500)
     * cod03, male = (account4:1900), (account5:100)
     *
     * @return List - {@link ClientDTO}
     */
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
                .company("comp02")
                .location("01")
                .accounts(List.of(accountDTO3))
                .build();

        ClientDTO clientDTO3 = ClientDTO.builder()
                .id(3)
                .type(1)
                .male('1')
                .code("cod03")
                .company("comp03")
                .location("02")
                .accounts(List.of(accountDTO4, accountDTO5))
                .build();

        return List.of(clientDTO1, clientDTO2, clientDTO3);
    }

    /**
     * Get more than 4 clients as follows (Client code = (account#:balance))
     *
     * cod01, male = (account1:300), (account2:1000)
     * cod02, women = (account3:500)
     * cod03, male = (account4:1900), (account5:100)
     * cod05, women = (account6:2000)
     * cod06, male = (account7:2000)
     * cod07, women = (account8:2000)
     * cod08, male = (account9:2000), (account10:2000)
     * cod09, women = (account11:2000)
     * cod10, women = (account12:100)
     * cod11, women = (account13:1000)
     *
     * @return List - {@link ClientDTO}
     */
    private List<ClientDTO> getClients(){
        AccountDTO accountDTO1 = AccountDTO.builder()
                .clientId(5)
                .balance(2000)
                .id(6)
                .build();

        AccountDTO accountDTO2 = AccountDTO.builder()
                .clientId(6)
                .balance(2000)
                .id(7)
                .build();

        AccountDTO accountDTO3 = AccountDTO.builder()
                .clientId(7)
                .balance(2000)
                .id(8)
                .build();

        AccountDTO accountDTO4 = AccountDTO.builder()
                .clientId(8)
                .balance(2000)
                .id(9)
                .build();

        AccountDTO accountDTO5 = AccountDTO.builder()
                .clientId(8)
                .balance(2000)
                .id(10)
                .build();

        AccountDTO accountDTO6 = AccountDTO.builder()
                .clientId(9)
                .balance(2000)
                .id(11)
                .build();

        AccountDTO accountDTO7 = AccountDTO.builder()
                .clientId(10)
                .balance(100)
                .id(12)
                .build();

        AccountDTO accountDTO8 = AccountDTO.builder()
                .clientId(11)
                .balance(1000)
                .id(13)
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

        ClientDTO clientDTO2 = ClientDTO.builder()
                .id(6)
                .type(1)
                .male('1')
                .code("cod06")
                .company("comp05")
                .location("01")
                .accounts(List.of(accountDTO2))
                .build();

        ClientDTO clientDTO3 = ClientDTO.builder()
                .id(7)
                .type(1)
                .male('0')
                .code("cod07")
                .company("comp06")
                .location("01")
                .accounts(List.of(accountDTO3))
                .build();

        ClientDTO clientDTO4 = ClientDTO.builder()
                .id(8)
                .type(1)
                .male('1')
                .code("cod08")
                .company("comp07")
                .location("01")
                .accounts(List.of(accountDTO4, accountDTO5))
                .build();

        ClientDTO clientDTO5 = ClientDTO.builder()
                .id(9)
                .type(1)
                .male('0')
                .code("cod09")
                .company("comp08")
                .location("01")
                .accounts(List.of(accountDTO6))
                .build();

        ClientDTO clientDTO6 = ClientDTO.builder()
                .id(10)
                .type(1)
                .male('0')
                .code("cod10")
                .company("comp09")
                .location("01")
                .accounts(List.of(accountDTO7))
                .build();

        ClientDTO clientDTO7 = ClientDTO.builder()
                .id(11)
                .type(1)
                .male('0')
                .code("cod11")
                .company("comp10")
                .location("01")
                .accounts(List.of(accountDTO8))
                .build();

        List<ClientDTO> clientDTOS = new ArrayList<>();
        clientDTOS.add(clientDTO1);
        clientDTOS.add(clientDTO2);
        clientDTOS.add(clientDTO3);
        clientDTOS.add(clientDTO4);
        clientDTOS.add(clientDTO5);
        clientDTOS.add(clientDTO6);
        clientDTOS.add(clientDTO7);

        clientDTOS.addAll(getLessThan4Clients());

        return clientDTOS;
    }
}
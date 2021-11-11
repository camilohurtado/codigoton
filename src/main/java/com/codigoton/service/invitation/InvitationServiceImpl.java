package com.codigoton.service.invitation;

import com.codigoton.dto.ClientDTO;
import com.codigoton.dto.FilterDTO;
import com.codigoton.dto.InvitationResponse;
import com.codigoton.helper.AccountBalanceRange;
import com.codigoton.helper.TotalBalanceCalculator;
import com.codigoton.service.information.ClientInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Camilo Hurtado
 */

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService{

    public final ClientInfoService clientInfoService;

    @Override
    public List<InvitationResponse> setupInvitations(List<FilterDTO> filter) {
        List<InvitationResponse> result = new ArrayList<>();

        if(filter != null && !filter.isEmpty()){
            filter.stream().forEach(filterDTO -> {

                List<ClientDTO> clients = clientInfoService.getAllClientsByFilter(filterDTO.getCodUbicacionGeo(), filterDTO.getTipoDeCliente());
                InvitationResponse invitationResponse = new InvitationResponse();

                if(clients != null && !clients.isEmpty() && clients.size() > 4){

                    Stream<ClientDTO> menStream = applyUniqueCompanyRule(applyToAllAccountsBalanceFilter(getEmployeesByGenderFromList(clients, '1'),
                            filterDTO.getRangoInicialBalance(), filterDTO.getRangoFinalBalance()));

                    Stream<ClientDTO> womenStream = applyUniqueCompanyRule(applyToAllAccountsBalanceFilter(getEmployeesByGenderFromList(clients, '0'),
                            filterDTO.getRangoInicialBalance(), filterDTO.getRangoFinalBalance()));

                    List<String> codes = Stream.concat(menStream, womenStream)
                            .sorted(compareTotalBalances)
                            .map(ClientDTO::getCode)
                            .limit(8)
                            .collect(Collectors.toList());

                    invitationResponse.setTable(filterDTO.getMesa());
                    if(codes.size() < 4){
                        invitationResponse.setStatus("CANCELADA");
                    }else{
                        invitationResponse.setListOfGuests(codes);
                    }
                }else{
                    invitationResponse.setTable(filterDTO.getMesa());
                    invitationResponse.setStatus("CANCELADA");
                }
                result.add(invitationResponse);
            });
        }
        return result;
    }

    private List<ClientDTO> getEmployeesByGenderFromList(List<ClientDTO> allClients, char gender){
        return allClients.stream().filter(clientDTO -> clientDTO.getMale() == gender).collect(Collectors.toList());
    }

    private Stream<ClientDTO> applyToAllAccountsBalanceFilter(List<ClientDTO> clients, double rangoInicialBalance, double rangoFinalBalance){
        return clients.stream()
                .filter(clientDTO -> accountBalanceIsInRange(clientDTO, rangoInicialBalance, rangoFinalBalance))
                .sorted(compareTotalBalances)
                .limit(8);
    }

    private boolean accountBalanceIsInRange(ClientDTO client, double rangoInicialBalance, double rangoFinalBalance){
        double totalBalance = totalBalanceCalculator.calculate(client.getAccounts());
        return totalAccountBalanceIsInRange.test(totalBalance, rangoInicialBalance, rangoFinalBalance);
    }

    /**
     *
     * @param clients
     * @return
     */
    private Stream<ClientDTO> applyUniqueCompanyRule(Stream<ClientDTO> clients){
         return clients
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ClientDTO::getCompany))), ArrayList::new))
                .stream();
    }

    /**
     *
     */
    AccountBalanceRange totalAccountBalanceIsInRange = (balance, initRange, finalRange) -> {
       boolean result = false;
        if(initRange > 0){
           result = balance >= initRange;
       }

       if(finalRange > 0){
           result = result && (balance <= finalRange);
       }

       return result;
    };

    /**
     *
     */
    TotalBalanceCalculator totalBalanceCalculator = (accounts) -> accounts
            .stream()
            .mapToDouble(value -> value.getBalance())
            .sum();

    /**
     *
     */
    Comparator<ClientDTO> compareTotalBalances = ((o1, o2) -> {
        double totalBalanceAcc1 = totalBalanceCalculator.calculate(o1.getAccounts());
        double totalBalanceAcc2 = totalBalanceCalculator.calculate(o2.getAccounts());

        if(totalBalanceAcc1 > totalBalanceAcc2){
            return 1;
        }else if(totalBalanceAcc1 == totalBalanceAcc2){
            return 0;
        }else {
            return -1;
        }
    });
}

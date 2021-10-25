package com.codigoton.service.information;

import com.codigoton.dto.AccountDTO;
import com.codigoton.dto.ClientDTO;
import com.codigoton.persistence.model.Account;
import com.codigoton.persistence.model.Client;
import com.codigoton.persistence.repository.AccountRepository;
import com.codigoton.persistence.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Camilo Hurtado
 */
@Service
@RequiredArgsConstructor
public class ClientInfoServiceImpl implements ClientInfoService{

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Override
    public ClientDTO getClientInformation(long clientId) throws Exception {

        ClientDTO clientDTO = clientRepository.findById(clientId)
                .map(Client::toDTO)
                .orElseThrow(() -> new Exception("The client does not exists."));

        List<AccountDTO> accountDTOList =
                accountRepository.findAllByClientId(clientId)
                .stream()
                .map(Account::toDTO)
                .collect(Collectors.toList());

        clientDTO.setAccounts(accountDTOList);

        return clientDTO;
    }
}

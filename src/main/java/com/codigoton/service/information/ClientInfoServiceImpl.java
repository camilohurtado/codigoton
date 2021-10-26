package com.codigoton.service.information;

import com.codigoton.consumer.CodeDecryptConsumer;
import com.codigoton.dto.AccountDTO;
import com.codigoton.dto.ClientDTO;
import com.codigoton.dto.FilterDTO;
import com.codigoton.persistence.model.Account;
import com.codigoton.persistence.model.Client;
import com.codigoton.persistence.repository.AccountRepository;
import com.codigoton.persistence.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final CodeDecryptConsumer codeDecryptConsumer;

    @Override
    public ClientDTO getClientInformation(long clientId) throws Exception {

        ClientDTO clientDTO = clientRepository.findById(clientId)
                .map(Client::toDTO)
                .orElseThrow(() -> new Exception("The client does not exists."));

        List<AccountDTO> accountDTOList =
                accountRepository.findByClientId(clientId)
                .stream()
                .map(Account::toDTO)
                .collect(Collectors.toList());

        clientDTO.setAccounts(accountDTOList);

        return clientDTO;
    }

    @Override
    public void setAccountInformation(ClientDTO clientDto) {
        if(clientDto != null){
            List<AccountDTO> accountDTOList =
                    accountRepository.findByClientId(clientDto.getId())
                            .stream()
                            .map(Account::toDTO)
                            .collect(Collectors.toList());

            clientDto.setAccounts(accountDTOList);
        }
    }

    @Override
    public List<ClientDTO> getAllClientsByFilter(String location, int clientType) {
        return clientRepository
                .findByLocationAndType(location, clientType)
                .stream()
                .map(Client::toDTO)
                .peek(clientDTO -> {
                    if(clientDTO.getEncrypt() == '1'){
                        String encryptedCode = clientDTO.getCode();
                        try {
                            clientDTO.setCode(codeDecryptConsumer.getDecryptedCode(encryptedCode));
                        } catch (Exception e) {
                            e.printStackTrace();
                            clientDTO.setCode("FAILED");
                        }
                    }
                })
                .peek(this::setAccountInformation)
                .collect(Collectors.toList());
    }
}

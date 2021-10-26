package com.codigoton.service.information;

import com.codigoton.dto.ClientDTO;
import com.codigoton.dto.FilterDTO;

import java.util.List;

public interface ClientInfoService {

    public ClientDTO getClientInformation(long clientId) throws Exception;
    public void setAccountInformation(ClientDTO clientDto);
    public List<ClientDTO> getAllClientsByFilter(String location, int clientType);

}

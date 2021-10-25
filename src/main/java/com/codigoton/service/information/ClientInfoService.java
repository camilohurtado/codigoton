package com.codigoton.service.information;

import com.codigoton.dto.ClientDTO;

public interface ClientInfoService {

    public ClientDTO getClientInformation(long clientId) throws Exception;

}

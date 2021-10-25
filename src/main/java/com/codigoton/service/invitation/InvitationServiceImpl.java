package com.codigoton.service.invitation;

import com.codigoton.dto.FilterDTO;
import com.codigoton.service.information.ClientInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Camilo Hurtado
 */

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService{

    public final ClientInfoService clientInfoService;

    @Override
    public void setupInvitations(FilterDTO filter) {
        
    }
}

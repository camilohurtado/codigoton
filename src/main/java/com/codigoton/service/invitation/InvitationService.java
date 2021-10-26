package com.codigoton.service.invitation;

import com.codigoton.dto.FilterDTO;
import com.codigoton.dto.InvitationResponse;

import java.util.List;

public interface InvitationService {
    public List<InvitationResponse> setupInvitations(List<FilterDTO> filter);
}

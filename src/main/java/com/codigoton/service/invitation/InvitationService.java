package com.codigoton.service.invitation;

import com.codigoton.dto.FilterDTO;

import java.util.List;
import java.util.Map;

public interface InvitationService {
    public Map<String, List<String>> setupInvitations(List<FilterDTO> filter);
}

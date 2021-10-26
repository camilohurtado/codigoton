package com.codigoton.controller;

import com.codigoton.dto.FilterDTO;
import com.codigoton.service.invitation.InvitationService;
import com.codigoton.service.reader.FileReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author Camilo Hurtado
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
public class InvitationController {

    private final InvitationService invitationService;
    private final FileReaderService fileReaderService;

    @GetMapping
    public ResponseEntity<?> getResult() {
        try {
            List<FilterDTO> filters = fileReaderService.getFiltersFromFile();
            return ResponseEntity.ok(invitationService.setupInvitations(filters));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

package com.controller;

import com.dto.AlertResponseDto;
import com.service.AlertService;
import com.utils.jwt.JwtUtils;
import com.utils.page.PagingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RestController
public class AlertController {

    private final AlertService alertService;
    private final JwtUtils jwtUtils;

    @GetMapping("/alerts")
    public ResponseEntity<PagingResponseDto> getAlertsOfUser(@RequestParam(value = "page", required = false) Integer page, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(alertService.getAlertOfUser(page, jwtUtils.getIdentifierFromJwt(jwt)));
    }

    @GetMapping("/alerts/{id}")
    public ResponseEntity<AlertResponseDto.Details> getAlertsDetails(@PathVariable("id") Long id, HttpServletRequest request) throws AccessDeniedException {
        String jwt = jwtUtils.getJwtFromRequest(request);

        return ResponseEntity.ok(alertService.getAlertsDetails(id, jwtUtils.getIdentifierFromJwt(jwt)));
    }

}

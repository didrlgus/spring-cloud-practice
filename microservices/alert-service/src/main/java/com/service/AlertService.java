package com.service;

import com.domain.Alert;
import com.domain.AlertRepository;
import com.dto.AlertResponseDto;
import com.exception.AlertIdentifierException;
import com.exception.EntityNotFoundException;
import com.mapper.AlertMapper;
import com.utils.page.PageUtils;
import com.utils.page.PagingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.exception.message.AlertExceptionMessage.INVALID_IDENTIFIER_VALUE;
import static com.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final PageUtils pageUtils;

    private static final int ALERT_PAGE_SIZE = 10;
    private static final int ALERT_SCALE_SIZE = 10;

    public PagingResponseDto getAlertOfUser(Integer page, String identifier) {

        Page<Alert> alertPage = alertRepository.findAllByIdentifier(identifier, pageUtils.getPageable(page, ALERT_PAGE_SIZE, Sort.Direction.DESC, "createdDate"));

        List<AlertResponseDto.Normal> alertResponseDtoList = alertPage.stream().map(AlertMapper.INSTANCE::alertToAlertNormalResponseDto).collect(Collectors.toList());

        return pageUtils.getCommonPagingResponseDto(alertPage, alertResponseDtoList, ALERT_SCALE_SIZE);
    }

    public AlertResponseDto.Details getAlertsDetails(Long id, String identifier) {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        if (!alert.getIdentifier().equals(identifier)) {
            throw new AlertIdentifierException(INVALID_IDENTIFIER_VALUE);
        }

        return AlertMapper.INSTANCE.alertToAlertDetailsResponseDto(alert);
    }
}

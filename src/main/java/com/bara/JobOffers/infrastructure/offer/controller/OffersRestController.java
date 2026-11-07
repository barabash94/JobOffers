package com.bara.JobOffers.infrastructure.offer.controller;

import com.bara.JobOffers.domain.offer.OfferFacade;
import com.bara.JobOffers.domain.offer.dto.JobOfferResponse;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import com.bara.JobOffers.infrastructure.offer.http.OfferHttpClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OffersRestController {

    private final OfferFacade  offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> fetchAllOffers() {
        List<OfferResponseDto> allOfferts = offerFacade.findAllOfferts();
        return ResponseEntity.ok(allOfferts);
    }
}

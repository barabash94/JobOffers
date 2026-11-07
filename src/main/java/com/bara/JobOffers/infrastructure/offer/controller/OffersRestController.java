package com.bara.JobOffers.infrastructure.offer.controller;

import com.bara.JobOffers.domain.offer.OfferFacade;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Offers", description = "Job offers API")
public class OffersRestController {

    private final OfferFacade  offerFacade;



    @Operation(summary = "Get all offers")
    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> fetchAllOffers() {
        List<OfferResponseDto> allOfferts = offerFacade.findAllOfferts();
        return ResponseEntity.ok(allOfferts);
    }
}

package com.bara.JobOffers.infrastructure.offer.controller;

import com.bara.JobOffers.domain.offer.Offer;
import com.bara.JobOffers.domain.offer.OfferFacade;
import com.bara.JobOffers.domain.offer.dto.OfferRequestDto;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/offers")
@Tag(name = "Offers", description = "Job offers API")
public class OffersRestController {

    private final OfferFacade offerFacade;


    @Operation(summary = "Get all offers")
    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> fetchAllOffers() {
        List<OfferResponseDto> allOfferts = offerFacade.findAllOfferts();
        return ResponseEntity.ok(allOfferts);
    }

    @Operation(summary = "Get offer by id")
    @GetMapping("/{id}")
    public ResponseEntity<OfferResponseDto> fetchOfferById(@PathVariable String id) {
        OfferResponseDto offerById = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offerById);
    }

@Operation(summary = "Save offer")
    @PostMapping
    public  ResponseEntity<OfferResponseDto>saveOffer(@RequestBody  @Valid OfferRequestDto offer){
        OfferResponseDto savedOffer = offerFacade.saveOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);

    }
}

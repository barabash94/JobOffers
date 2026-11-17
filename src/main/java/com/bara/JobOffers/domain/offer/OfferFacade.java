package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.OfferRequestDto;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public class OfferFacade {

    OfferRepository offerRepository;
    OfferService offerService;

    public OfferFacade(OfferRepository offerRepository, OfferService offerService) {
        this.offerRepository = offerRepository;
        this.offerService = offerService;
    }

    @Cacheable(cacheNames = "jobOffers")
    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAll().stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .toList();


    }

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromOfferToOfferDto).orElseThrow(() -> new OfferNotFoundException(id));
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        Offer offer = OfferMapper.mapFromOfferRequestDtoToOffer(offerRequestDto);
        Offer saved = offerRepository.save(offer);
        return OfferMapper.mapFromOfferToOfferDto(saved);
    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .toList();
    }
}



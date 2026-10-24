package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.JobOfferResponse;
import com.bara.JobOffers.domain.offer.dto.OfferRequestDto;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;

public class OfferMapper {
    public static OfferResponseDto mapFromOfferToOfferDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .position(offer.position())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    public static Offer mapFromJobOfferResponseToOffer(JobOfferResponse jobResponse) {
        return new Offer.OfferBuilder()
                .companyName(jobResponse.company())
                .offerUrl(jobResponse.offerUrl())
                .position(jobResponse.title())
                .salary(jobResponse.salary())
                .build();
    }


    public static Offer mapFromOfferRequestDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .companyName(offerRequestDto.companyName())
                .salary(offerRequestDto.salary())
                .position(offerRequestDto.position())
                .offerUrl(offerRequestDto.offerUrl())
                .build();
    }
}

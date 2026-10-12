package com.bara.JobOffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(String id,
                               String companyName,
                               String position,
                               String salary,
                               String offerUrl) {
}

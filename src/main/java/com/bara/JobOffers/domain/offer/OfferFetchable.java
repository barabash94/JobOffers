package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

public interface OfferFetchable {
    List<JobOfferResponse> fetchOffers();
}

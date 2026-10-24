package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.JobOfferResponse;

import java.util.ArrayList;
import java.util.List;

public class OfferFetchableTestImpl implements OfferFetchable {

    List<JobOfferResponse> jobOffer=   new ArrayList<>();

    public OfferFetchableTestImpl(List<JobOfferResponse> jobOffer) {
        this.jobOffer = jobOffer;
    }

    @Override
    public List<JobOfferResponse> fetchOffers() {
        return jobOffer;
    }
}

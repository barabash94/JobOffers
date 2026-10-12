package com.bara.JobOffers.domain.offer;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(String id) {
        super("Offer with id " + id + " not found");
    }
}

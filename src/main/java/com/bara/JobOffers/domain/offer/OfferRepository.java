package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    Offer saveOffer(Offer offer);

    Optional<Offer> findOfferById(String id);

    List<Offer> saveAll(List<Offer> offers);

    List<Offer> findAll();

    Optional<Offer> findByOfferUrl(String offerUrl);

    boolean existsByOfferUrl(String offerUrl);
}

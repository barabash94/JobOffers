package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {

    Optional<Offer> findByOfferUrl(String offerUrl);

    boolean existsByOfferUrl(String offerUrl);
}
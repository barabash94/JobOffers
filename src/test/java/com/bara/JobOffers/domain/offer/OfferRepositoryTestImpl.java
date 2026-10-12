package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class OfferRepositoryTestImpl implements OfferRepository {

    Map<String, Offer> database;

    public OfferRepositoryTestImpl() {
        this.database = new ConcurrentHashMap<>();
    }

    public OfferRepositoryTestImpl(Map<String, Offer> database) {
        this.database = new ConcurrentHashMap<>(database);
    }

    @Override
    public Offer saveOffer(Offer entity) {
        if (database.values().stream().anyMatch(offer -> offer.offerUrl().equals(entity.offerUrl()))) {
            throw new OfferDuplicateException(entity.offerUrl());
        }
        UUID id = UUID.randomUUID();
        Offer offer = Offer.builder()
                .id(id.toString())
                .offerUrl(entity.offerUrl())
                .companyName(entity.companyName())
                .position(entity.position())
                .salary(entity.salary())
                .build();
        database.put(id.toString(), offer);
        return offer;
    }

    @Override
    public Optional<Offer> findOfferById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::saveOffer)
                .toList();
    }

    @Override
    public List<Offer> findAll() {
        return database.values().stream().toList();
    }
    @Override
    public boolean existsByOfferUrl(String offerUrl) {
        long count = database.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(offerUrl))
                .count();
        return count == 1;
    }

    @Override
    public Optional<Offer> findByOfferUrl(String offerUrl) {
        return Optional.of(database.get(offerUrl));
    }

}

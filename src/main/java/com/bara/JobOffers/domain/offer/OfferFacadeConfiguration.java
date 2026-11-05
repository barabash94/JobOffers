package com.bara.JobOffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
@Configuration
public class OfferFacadeConfiguration {

    @Bean
    OfferRepository offerRepository() {
        return new OfferRepository() {
            @Override
            public Offer saveOffer(Offer offer) {
                return null;
            }

            @Override
            public Optional<Offer> findOfferById(String id) {
                return Optional.empty();
            }

            @Override
            public List<Offer> saveAll(List<Offer> offers) {
                return List.of();
            }

            @Override
            public List<Offer> findAll() {
                return List.of();
            }

            @Override
            public Optional<Offer> findByOfferUrl(String offerUrl) {
                return Optional.empty();
            }

            @Override
            public boolean existsByOfferUrl(String offerUrl) {
                return false;
            }

        };
    }

    @Bean
    OfferFacade offerFacade(
            OfferRepository repo,
            OfferFetchable offerFetchable) {

        OfferService offerService =
                new OfferService(repo, offerFetchable);

        return new OfferFacade(repo, offerService);
    }
}
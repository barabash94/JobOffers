package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.JobOfferResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OfferFacadeTestConfig {


    private final OfferFetchableTestImpl inMemoryFetcherTest;
    private final OfferRepositoryTestImpl offerRepository;

    OfferFacadeTestConfig() {
        this.inMemoryFetcherTest = new OfferFetchableTestImpl(
                List.of(
                        new JobOfferResponse("Junior Java Developer", "Allegro", "15000", "https://example.com/jobs/offer-1"),
                        new JobOfferResponse("Java Developer", "Sii", "18000", "https://example.com/jobs/offer-2"),
                        new JobOfferResponse("Junior Backend Developer", "Asseco", "14000", "https://example.com/jobs/offer-3"),
                        new JobOfferResponse("Spring Boot Developer", "Comarch", "16000", "https://example.com/jobs/offer-4"),
                        new JobOfferResponse("Junior Software Engineer", "Capgemini", "12000", "https://example.com/jobs/offer-5"),
                        new JobOfferResponse("Java Backend Developer", "EPAM", "20000", "https://example.com/jobs/offer-6")
                )
        );
        Map<String, Offer> offers = new HashMap<>();
        offers.put("1", new Offer( "1", "Allegro", "Junior Java Developer","10000" ,"https://example.com/jobs/offer-1" ));
        offers.put("2", new Offer( "2", "Sii", "Java Developer","79000" ,"https://example.com/jobs/offer-2" ));
        this.offerRepository = new OfferRepositoryTestImpl(offers);
    }

    OfferFacadeTestConfig(List<JobOfferResponse> remoteClientOffers) {
        this.inMemoryFetcherTest = new OfferFetchableTestImpl(remoteClientOffers);
        this.offerRepository = new OfferRepositoryTestImpl();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(offerRepository, new OfferService(offerRepository, inMemoryFetcherTest));
    }
}



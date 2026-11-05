package com.bara.JobOffers.domain.offer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class OfferService {
    OfferRepository offerRepository;
    OfferFetchable offerFetcher;

    public OfferService(OfferRepository offerRepository, OfferFetchable offerFetcher) {
        this.offerRepository = offerRepository;
        this.offerFetcher = offerFetcher;
    }

    List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> jobOffers = fetchOffers();
//        final List<Offer> offers = filterNotExistingOffers(jobOffers);
        try {
            return jobOffers;
        } catch (OfferDuplicateException duplicateKeyException) {
            throw new OfferSavingException(duplicateKeyException.getMessage());
        }
    }

    private List<Offer> filterNotExistingOffers(List<Offer> jobOffers) {
        return jobOffers.stream()
                .filter(offerDto -> !offerDto.offerUrl().isEmpty())
                .filter(offerDto -> !offerRepository.existsByOfferUrl(offerDto.offerUrl()))
                .collect(Collectors.toList());
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromJobOfferResponseToOffer)
                .toList();
    }
}


package com.bara.JobOffers.infrastructure.offer.scheduler;

import com.bara.JobOffers.domain.offer.OfferFacade;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@Log4j2
public class HttpOffersScheduler {


    private final OfferFacade offerFacade;

    public HttpOffersScheduler(OfferFacade offerFacade) {
        this.offerFacade = offerFacade;
    }

    @Scheduled(fixedDelayString = "${http.offers.scheduler.request.delay}")
    public List<OfferResponseDto>  fetchAllOffersAndSaveAllIfNotExists() {

        log.info("Started offers fetching at {}", LocalTime.now());
        final List<OfferResponseDto> addedOffers =
                offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info("Added new {} offers", addedOffers.size());
        log.info("Stopped offers fetching at {}", LocalTime.now());
        return addedOffers;
    }

}


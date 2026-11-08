package com.bara.JobOffers.infrastructure.offer.controller.error;

import com.bara.JobOffers.domain.offer.OfferNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class OfferControllerErrorHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    OfferErrorResponse offerNotFound(OfferNotFoundException exception) {
        String message = String.format("Offer with id " + exception.getOfferId() + " not found");
        return new OfferErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}

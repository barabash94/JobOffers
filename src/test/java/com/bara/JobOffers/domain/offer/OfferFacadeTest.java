package com.bara.JobOffers.domain.offer;

import com.bara.JobOffers.domain.offer.dto.OfferRequestDto;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OfferFacadeTest {

    OfferFacade testFacade = new OfferFacadeTestConfig().offerFacadeForTests();


    @Test
    void it_should_find_all_offers() {
        //given
        //when
        List<OfferResponseDto> allOffers = testFacade.findAllOffers();
        //then
        assertThat(allOffers.size()).isEqualTo(2);
    }


    @Test
    void it_should_find_offer_by_id() {
        //given
        String offerId = "1";
        //when
        OfferResponseDto offer = testFacade.findOfferById(offerId);
        //then
        assertThat(offer.companyName()).isEqualTo("Allegro");
        assertThat(offer.position()).isEqualTo("Junior Java Developer");
        assertThat(offer.salary()).isEqualTo("10000");
        assertThat(offer.offerUrl()).isEqualTo("https://example.com/jobs/offer-1");
    }

    @Test
    void it_should_throw_not_found_exception_when_offer_not_found() {
        //given
        String offerId = "7";
        //then
        assertThrows(OfferNotFoundException.class, () -> testFacade.findOfferById(offerId));
    }

    @Test
    void it_should_save_offer_to_database() {
        // given
        int sizeBeforeSave = testFacade.findAllOffers().size();

        OfferRequestDto newOffer = new OfferRequestDto(
                "Asseco",
                "Junior Backend Developer",
                "15000",
                "https://example.com/jobs/offer-3"
        );

        // when
        testFacade.saveOffer(newOffer);

        // then
        List<OfferResponseDto> offersAfterSave = testFacade.findAllOffers();

        assertThat(offersAfterSave).hasSize(sizeBeforeSave + 1);
        assertThat(offersAfterSave)
                .extracting(OfferResponseDto::offerUrl)
                .contains("https://example.com/jobs/offer-3");
    }

    @Test
    public void it_should_throw_duplicate_key_exception_when_offer_url_exists() {
        // given
        OfferFacade offerFacade =
                new OfferFacadeTestConfig(List.of()).offerFacadeForTests();
        String offerUrl = "https://example.com/jobs/offer";
        OfferResponseDto savedOffer = offerFacade.saveOffer(
                new OfferRequestDto("Asseco", "Junior Java", "20000", offerUrl)
        );

        assertThat(offerFacade.findOfferById(savedOffer.id()).id())
                .isEqualTo(savedOffer.id());

        // when
        Throwable thrown = catchThrowable(() ->
                offerFacade.saveOffer(
                        new OfferRequestDto("CX", "Java Developer", "15000", offerUrl))
        );
        // then
        assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    public void should_throw_not_found_exception_when_offer_not_found() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfig(List.of()).offerFacadeForTests();

        // when
        Throwable thrown = catchThrowable(() -> offerFacade.findOfferById("100"));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id 100 not found");
    }

    @Test
    public void should_skip_existing_offers_when_fetching_and_saving_offers() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfig().offerFacadeForTests();

        // when
        List<OfferResponseDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        // then
        assertThat(result).hasSize(4);
        assertThat(result)
                .extracting(OfferResponseDto::offerUrl)
                .containsExactlyInAnyOrder(
                        "https://example.com/jobs/offer-3",
                        "https://example.com/jobs/offer-4",
                        "https://example.com/jobs/offer-5",
                        "https://example.com/jobs/offer-6"
                );

    }
}


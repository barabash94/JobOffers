package com.bara.JobOffers.features;

import com.bara.JobOffers.BaseIntegrationTest;
import com.bara.JobOffers.SampleJobOfferResponse;
import com.bara.JobOffers.domain.offer.dto.OfferResponseDto;
import com.bara.JobOffers.infrastructure.offer.scheduler.HttpOffersScheduler;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.jsonpath.TypeRef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.core.type.TypeReference;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {


    @Autowired
    HttpOffersScheduler httpOffersScheduler;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {


        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));


        // step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        // given && when
        List<OfferResponseDto> newOffers = httpOffersScheduler.fetchAllOffersAndSaveAllIfNotExists();
        // then
        assertThat(newOffers).size().isEqualTo(0);


        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC


        //step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //given
        String offersUrl = "/offers";
        //when
        ResultActions perform = mockMvc.perform(get(offersUrl)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<OfferResponseDto> offers = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(offers).size().isEqualTo(0);


        //step 8: there are 2 new offers in external HTTP server
        //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000


        //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given
        int id = 9999;

// when
        ResultActions performGetOffersNotExistingId =
                mockMvc.perform(get(offersUrl + "/" + id));

// then
        performGetOffersNotExistingId
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                          "message": "Offer with id 9999 not found",
                          "status": "404 NOT_FOUND"
                        }
                        """));
        //step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //step 13: there are 2 new offers in external HTTP server
        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        //step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000

        //step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer as body and system returned CREATED(201) with saved offer
        //given&when
        ResultActions perform3 = mockMvc.perform(post("/offers").content("""
                          {
                                "companyName": "someCompany",
                                "position": "somePosition",
                                "salary": "7 000 - 9 000 PLN",
                                "offerUrl": "https://newoffers.pl/offer"
                                }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );

        String contentAsString3 = perform3.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OfferResponseDto addedOffer = objectMapper.readValue(contentAsString3, OfferResponseDto.class);
        String id1 = addedOffer.id();
        //then
        assertAll(
                () -> assertThat(addedOffer.offerUrl()).isEqualTo("https://newoffers.pl/offer"),
                () -> assertThat(addedOffer.companyName()).isEqualTo("someCompany"),
                () -> assertThat(addedOffer.salary()).isEqualTo("7 000 - 9 000 PLN"),
                () -> assertThat(addedOffer.position()).isEqualTo("somePosition"),
                () -> assertThat(id).isNotNull()
        );

        //step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 1 offer

        String offerSaved = mockMvc.perform(get(offersUrl).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        List<OfferResponseDto>savedOffers = objectMapper.readValue(offerSaved, new TypeReference<>() {
        });

        assertThat(savedOffers).size().isEqualTo(1);
    }

        @Test
        void getScenarioUserWantToSeeOffersIntegrationTest () {
        }
    }


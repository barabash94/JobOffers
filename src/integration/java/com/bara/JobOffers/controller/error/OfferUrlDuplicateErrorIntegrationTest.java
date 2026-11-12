package com.bara.JobOffers.controller.error;


import com.bara.JobOffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {


    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {

        System.out.println("WIREMOCK PORT = " + wireMockServer.getPort());
        registry.add("spring.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("offer.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("offer.http.client.config.port", wireMockServer::getPort);
    }

    @Test
    public void should_throw_duplicate_key_exception() throws Exception {
        //STEP 1
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                             {
                        "companyName": "someCompany",
                        "position": "somePosition",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "https://newoffers.pl/offer/1234"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));

        //then
        perform.andExpect(status().isCreated());

        //STEP 2
        ResultActions perform2 = mockMvc.perform(post("/offers")
                .content("""
                             {
                        "companyName": "someCompany",
                        "position": "somePosition",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "https://newoffers.pl/offer/1234"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));

        //then
        perform2.andExpect(status().isConflict());
    }


}

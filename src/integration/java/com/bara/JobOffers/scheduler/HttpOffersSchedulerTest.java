package com.bara.JobOffers.scheduler;

import com.bara.JobOffers.BaseIntegrationTest;
import com.bara.JobOffers.JobOffersApplication;
import com.bara.JobOffers.domain.offer.OfferFetchable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = JobOffersApplication.class, properties = "scheduling.enabled=true")
public class HttpOffersSchedulerTest extends BaseIntegrationTest {


    @MockitoSpyBean
    OfferFetchable remoteOfferClient;

    @Test
    public void should_run_http_client_offers_fetching_exactly_given_times() {
        await().
                atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(remoteOfferClient, times(1)).fetchOffers());
    }
}

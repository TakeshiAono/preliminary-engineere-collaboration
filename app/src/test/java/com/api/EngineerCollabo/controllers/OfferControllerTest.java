package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Offer;
import com.api.EngineerCollabo.entities.ResponseOffer;
import com.api.EngineerCollabo.repositories.OfferRepository;
import com.api.EngineerCollabo.services.OfferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferControllerTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    @Test
    void responseOffer_ReturnsOffer() {
        // 準備
        int offerId = 1;
        Offer offer = new Offer();
        offer.setId(offerId);

        ResponseOffer expectedResponse = new ResponseOffer();
        expectedResponse.setId(offerId);

        when(offerRepository.findById(offerId)).thenReturn(offer);
        when(offerService.changResponseOffer(offer)).thenReturn(expectedResponse);

        // 実行
        ResponseOffer actualResponse = offerController.responseOffer(Optional.of(offerId));

        // 検証
        assertEquals(expectedResponse, actualResponse);
    }
}

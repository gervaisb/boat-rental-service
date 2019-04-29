package com.github.gervaisb.boatrental.renting.rest;

import java.time.LocalDateTime;

import com.github.gervaisb.boatrental.renting.domain.BoatName;
import com.github.gervaisb.boatrental.renting.domain.Customer;
import com.github.gervaisb.boatrental.renting.domain.Rent;
import com.github.gervaisb.boatrental.renting.domain.RentId;
import com.github.gervaisb.boatrental.renting.domain.RentingService;
import com.github.gervaisb.boatrental.renting.domain.RentingTicket;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(RentingController.class)
public class RentingControllerTest {

    @Autowired
    private MockMvc http;

    @MockBean
    private RentingService service;

    @Test
    public void rent_must_create_the_rent_and_return_the_ticket() throws Exception {
        RentId rentId = new RentId();
        LocalDateTime start = LocalDateTime.of(2019, 04, 30, 15, 55, 00);
        given(service.rent(any(), any())).willReturn(new RentingTicket(
                new Rent(rentId, new Customer("Noah", "Salvator"), start),
                new BoatName("Ark")
        ));

        http.perform(post("/api/rents")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "  \"boat\": \"ark\",\n" +
                        "  \"customerFirstName\": \"Noah\",\n" +
                        "  \"customerLastName\": \"Salvator\"\n" +
                        "}"))

                .andExpect(jsonPath("boat_name").value("Ark"))
                .andExpect(jsonPath("customer_last_name").value("Salvator"))
                .andExpect(jsonPath("customer_first_name").value("Noah"))
                .andExpect(jsonPath("start").value("2019-04-30T15:55:00"))
                .andExpect(jsonPath("reference").value(rentId.value));
    }
}

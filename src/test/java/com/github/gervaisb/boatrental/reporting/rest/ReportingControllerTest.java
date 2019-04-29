package com.github.gervaisb.boatrental.reporting.rest;

import java.time.Duration;

import com.github.gervaisb.boatrental.reporting.domain.Report;
import com.github.gervaisb.boatrental.reporting.domain.ReportingService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ReportingController.class)
public class ReportingControllerTest {

    @Autowired
    private MockMvc http;

    @MockBean
    private ReportingService reporting;

    @Test
    public void getTodayReportAsJson_must_return_the_report() throws Exception {
        given(reporting.getTodayReport()).willReturn(new Report(3, Duration.ofSeconds(3600)));
        http.perform(
                get("/api/reports/today")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("completed_trips").value(3))
                .andExpect(jsonPath("average_duration_seconds").value(3600));
    }

    @Test
    public void getTodayReportAsHtml_must_() throws Exception {
    }
}

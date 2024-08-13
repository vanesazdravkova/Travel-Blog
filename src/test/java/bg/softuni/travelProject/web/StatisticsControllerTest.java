package bg.softuni.travelProject.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "testUserDataService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void testStatisticsEndpoint() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(view().name("statistics"))
                .andExpect(model().attributeExists("statistics"))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("allTrips", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("asianTrips", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("africanTrips", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("northAmericanTrips", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("southAmericanTrips", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("antarcticanTrips", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("europeanTrips", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("australianTrips",
                                org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("usersCount", org.hamcrest.Matchers.is(0L))))
                .andExpect(model().attribute("statistics",
                        org.hamcrest.Matchers.hasProperty("localDateTime",
                                org.hamcrest.Matchers.notNullValue())));
    }

}

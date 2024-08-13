package bg.softuni.travelProject.web;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import bg.softuni.travelProject.model.view.FutureTripViewModel;
import bg.softuni.travelProject.repository.UserRepository;
import bg.softuni.travelProject.service.FutureTripService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class FutureTripControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FutureTripService futureTripService;

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "testUserDataService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void testAllFortunes() throws Exception {
        FutureTripViewModel trip1 = new FutureTripViewModel().setId(1L).setDestination("Paris").setCompany("Rual")
                .setDays(5).setName("Spring in Paris").setPrice(1200);
        FutureTripViewModel trip2 = new FutureTripViewModel().setId(2L).setDestination("Rome").setCompany("Bohemia")
                .setDays(7).setName("Autumn in Rome").setPrice(2400);

        when(futureTripService.getAllFutureTrips()).thenReturn(Arrays.asList(trip1, trip2));

        mockMvc.perform(MockMvcRequestBuilders.get("/future-trips/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("futureTrips"))
                .andExpect(MockMvcResultMatchers.model().attribute("heading", "All future trips (2)"))
                .andExpect(MockMvcResultMatchers.view().name("all-future-trips"));
    }
}

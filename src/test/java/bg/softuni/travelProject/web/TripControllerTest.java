package bg.softuni.travelProject.web;

import bg.softuni.travelProject.model.cloudinary.CloudinaryImage;
import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.entity.TypeEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import bg.softuni.travelProject.service.CloudinaryService;
import bg.softuni.travelProject.utils.TestHelper;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TripControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestHelper testHelper;

    private UserEntity testUser, testAdmin;

    private TripEntity testUserTrip, testAdminTrip;

    @MockBean
    private CloudinaryService mockCloudinaryService;

    @BeforeEach
    void setUp() {
        testUser = testHelper.createUser("user");
        testAdmin = testHelper.createAdmin("admin");
        List<TypeEntity> testTypes = testHelper.createTypes();

        testUserTrip = testHelper.createTrip(testUser, testTypes);
        testAdminTrip = testHelper.createTrip(testAdmin, testTypes);
    }

    @AfterEach
    void tearDown() {
        testHelper.cleanUpDatabase();
    }

    @Test
    void testDeleteByAnonymousUserForbidden() throws Exception {
        mockMvc.perform(delete("/trips/delete/{id}", testUserTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(
            username = "admin",
            roles = {"ADMIN", "USER"})
    void testDeleteByAdmin() throws Exception {
        mockMvc.perform(delete("/trips/delete/{id}", testUserTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/trips/all"));
    }

    @WithMockUser(
            username = "user",
            roles = "USER")
    @Test
    void testDeleteByOwner() throws Exception {
        mockMvc.perform(delete("/trips/delete/{id}", testUserTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/trips/all"));
    }

    @WithMockUser(
            username = "user",
            roles = "USER")
    @Test
    public void testDeleteNotOwnedForbidden() throws Exception {
        mockMvc.perform(delete("/trips/delete/{id}", testAdminTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().isForbidden());
    }

    @Test
    void testAddTripByAnonymousUserForbidden() throws Exception {
        mockMvc.perform(get("/trips/add").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @WithUserDetails(value = "user",
            userDetailsServiceBeanName = "testUserDataService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void testAddTripByLoggedUserSuccess() throws Exception {

        when(mockCloudinaryService.uploadImage(any(MultipartFile.class)))
                .thenReturn(new CloudinaryImage().setUrl("mockUrl").setPublicId("mockPublicId"));

        long beforeCount = testHelper.getTripRepository().count();

        MockMultipartFile picture1 = new MockMultipartFile("pictureFiles", "file1.jpg", "image/jpeg",
                "fileContent1".getBytes());
        MockMultipartFile picture2 = new MockMultipartFile("pictureFiles", "file2.jpg", "image/jpeg",
                "fileContent2".getBytes());

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/trips/add")
                                .file(picture1)
                                .file(picture2)
                                // Add other parameters
                                .param("name", "Test Trip")
                                .param("landmarks", "Test landmarks")
                                .param("description", "Test description")
                                .param("pricingLevel", "LUXURY")
                                .param("videoUrl", "video://test.mp4")
                                .param("continent", "EUROPE")
                                .param("days", "6")
                                .param("countriesVisited", "2")
                                .param("types", "BEACH", "MOUNTAIN")
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        long afterCount = testHelper.getTripRepository().count();
        Assertions.assertEquals(beforeCount + 1, afterCount);

        String redirectedUrl = result.getResponse().getRedirectedUrl();
        mockMvc.perform(get("http://localhost" + redirectedUrl))
                .andExpect(status().isOk());

        String tripId = redirectedUrl.split("/")[redirectedUrl.split("/").length - 1];
        Assertions.assertTrue(
                testHelper.getTripRepository().existsById(Long.parseLong(tripId)));

        mockMvc.perform(get("/trips/details/{TripId}", tripId))
                .andExpect(status().isOk());

        Assertions.assertEquals(redirectedUrl, "/trips/details/" + tripId);
    }

    @Test
    void testAddTripByLoggedUserFail() throws Exception {

        when(mockCloudinaryService.uploadImage(any(MultipartFile.class)))
                .thenReturn(new CloudinaryImage().setUrl("mockUrl").setPublicId("mockPublicId"));

        long beforeCount = testHelper.getTripRepository().count();

        MockMultipartFile picture1 = new MockMultipartFile("pictureFiles", "file1.jpg", "image/jpeg",
                "fileContent1".getBytes());
        MockMultipartFile picture2 = new MockMultipartFile("pictureFiles", "file2.jpg", "image/jpeg",
                "fileContent2".getBytes());

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/trips/add")
                                .file(picture1)
                                .file(picture2)
                                // Add other parameters
                                .param("name", " ")
                                .param("landmarks", "Test landmarks")
                                .param("description", "Test description")
                                .param("pricingLevel", "LUXURY")
                                .param("videoUrl", "video://test.mp4")
                                .param("continent", "EUROPE")
                                .param("days", "6")
                                .param("countriesVisited", "2")
                                .param("types", "BEACH", "MOUNTAIN")
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        long afterCount = testHelper.getTripRepository().count();
        Assertions.assertEquals(beforeCount, afterCount);

        String redirectedUrl = result.getResponse().getRedirectedUrl();
        Assertions.assertEquals(redirectedUrl, "/trips/add");
    }

    @Test
    void testUpdateByAnonymousUserForbidden() throws Exception {
        mockMvc.perform(get("/trips/edit/{id}", testUserTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @WithUserDetails(value = "admin",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testUpdateByAdmin() throws Exception {
        mockMvc.perform(get("/trips/edit/{id}", testUserTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("trip-update"));
    }

    @WithUserDetails(value = "user",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testUpdateByOwner() throws Exception {
        mockMvc.perform(get("/trips/edit/{id}", testUserTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("trip-update"));
    }

    @WithUserDetails(value = "user",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    @Transactional
    void testUpdateTripByOwnerSuccess() throws Exception {

        MvcResult result = mockMvc.perform(
                        put("/trips/edit/{id}", testUserTrip.getId())
                                // Add other parameters
                                .param("name", "New trip name")
                                .param("landmarks", "New landmarks")
                                .param("description", "New description")
                                .param("pricingLevel", "LUXURY")
                                .param("videoUrl", "video://newVideo.mp4")
                                .param("continent", "ASIA")
                                .param("days", "3")
                                .param("countriesVisited", "4")
                                .param("types", "BEACH", "MOUNTAIN")
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();
        Assertions.assertEquals(redirectedUrl, "/trips/details/" + testUserTrip.getId());

        TripEntity updatedTrip = testHelper.getTripRepository()
                .findById(testUserTrip.getId()).orElse(null);
        Assertions.assertNotNull(updatedTrip);
        Assertions.assertEquals("New trip name", updatedTrip.getName());
        Assertions.assertEquals("New landmarks", updatedTrip.getLandmarks());
        Assertions.assertEquals("New description", updatedTrip.getDescription());
        Assertions.assertEquals(PricingLevelEnum.LUXURY, updatedTrip.getPricingLevel());
        Assertions.assertEquals("video://newVideo.mp4", updatedTrip.getVideoUrl());
        Assertions.assertEquals(ContinentEnum.ASIA, updatedTrip.getContinent());
        Assertions.assertEquals(3, updatedTrip.getDays());
        Assertions.assertEquals(4, updatedTrip.getCountriesVisited());
        Assertions.assertEquals(2, updatedTrip.getTypes().size());
        Assertions.assertEquals(TripTypeEnum.BEACH, updatedTrip.getTypes().get(0).getName());
    }

    @Test
    @Transactional
    void testUpdateTripsByOwnerFail() throws Exception {

        MvcResult result = mockMvc.perform(
                        put("/trips/edit/{id}", testUserTrip.getId())
                                .param("name", " ")
                                .param("landmarks", "New landmarks")
                                .param("description", "New description")
                                .param("pricingLevel", "LUXURY")
                                .param("videoUrl", "video://newVideo.mp4")
                                .param("continent", "AFRICA")
                                .param("days", "10")
                                .param("countriesVisited", "6")
                                .param("types", "SAFARI")
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();
        Assertions.assertEquals(redirectedUrl, "/trips/edit/" + testUserTrip.getId());

        TripEntity updatedTrip = testHelper.getTripRepository()
                .findById(testUserTrip.getId()).orElse(null);
        Assertions.assertNotNull(updatedTrip);
        Assertions.assertEquals("Paris", updatedTrip.getName());
        Assertions.assertEquals("Eiffel Tower", updatedTrip.getLandmarks());
        Assertions.assertEquals("My trip to Paris during Spring was fantastic.", updatedTrip.getDescription());
        Assertions.assertEquals(PricingLevelEnum.LUXURY, updatedTrip.getPricingLevel());
        Assertions.assertEquals("http//videoUrl", updatedTrip.getVideoUrl());
        Assertions.assertEquals(ContinentEnum.EUROPE, updatedTrip.getContinent());
        Assertions.assertEquals(6, updatedTrip.getDays());
        Assertions.assertEquals(1, updatedTrip.getCountriesVisited());
        Assertions.assertEquals(2, updatedTrip.getTypes().size());
        Assertions.assertEquals(TripTypeEnum.BEACH, updatedTrip.getTypes().get(0).getName());
    }

    @Test
    void testViewAllTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/all").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewAsianTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/asian").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewAfricanTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/african").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewNorthAmericanTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/north-american").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewSouthAmericanTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/south-american").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewAntarcticanTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/antarctican").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewEuropeanTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/european").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewAustralianTripsByAnonymousUserSuccess() throws Exception {
        mockMvc.perform(get("/trips/australian").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("all-trips"));
    }

    @Test
    void testViewTripDetails() throws Exception {
        mockMvc.perform(get("/trips/details/{id}", testUserTrip.getId()).
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(view().name("trip-details"));
    }
}
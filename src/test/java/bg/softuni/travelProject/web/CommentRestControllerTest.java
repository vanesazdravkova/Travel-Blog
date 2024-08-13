package bg.softuni.travelProject.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.softuni.travelProject.model.dto.CommentAddDto;
import bg.softuni.travelProject.model.entity.CommentEntity;
import bg.softuni.travelProject.model.entity.RoleEntity;
import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.repository.RoleRepository;
import bg.softuni.travelProject.repository.TripRepository;
import bg.softuni.travelProject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

    private static final String COMMENT_1 = "Comment1Comment1Comment1Comment1";
    private static final String COMMENT_2 = "Comment2Comment2Comment2Comment2";
    private static final String COMMENT_3 = "Comment3Comment3Comment3Comment3";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;
    private UserEntity admin;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        RoleEntity userRole = new RoleEntity();
        userRole.setRole(RoleNameEnum.USER);
        roleRepository.save(userRole);

        RoleEntity adminRole = new RoleEntity();
        adminRole.setRole(RoleNameEnum.ADMIN);
        roleRepository.save(adminRole);

        user = new UserEntity();
        user.setPassword("54321");
        user.setUsername("vanesaz");
        user.setFirstName("Vanesa");
        user.setLastName("Zdravkova");
        user.setEmail("vanesaz@gmail.com");
        user.setRoles(List.of(userRole));

        user = userRepository.save(user);

        admin = new UserEntity();
        admin.setPassword("54321");
        admin.setUsername("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setEmail("admin@gmail.com");
        admin.setRoles(List.of(adminRole));

        admin = userRepository.save(admin);
    }

    @AfterEach
    void tearDown() {
        tripRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetComments() throws Exception {
        TripEntity testTrip = initComments(initTrip());

        mockMvc.perform(get("/api/" + testTrip.getId() + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].message", is(COMMENT_1)))
                .andExpect(jsonPath("$.[0].user", is("Vanesa Zdravkova")))
                .andExpect(jsonPath("$.[1].message", is(COMMENT_2)))
                .andExpect(jsonPath("$.[1].user", is("Vanesa Zdravkova")))
                .andExpect(jsonPath("$.[2].message", is(COMMENT_3)))
                .andExpect(jsonPath("$.[2].user", is("Admin Adminov")));
    }

    private TripEntity initTrip() {
        TripEntity testTrip = new TripEntity();
        testTrip.setName("Testing trip")
                .setLandmarks("Testing landmarks")
                .setDescription("Testing description")
                .setPricingLevel(PricingLevelEnum.BUDGET)
                .setAuthor(userRepository.findByUsername("vanesaz").get())
                .setContinent(ContinentEnum.ANTARCTICA)
                .setDays(16)
                .setCountriesVisited(2);

        return testTrip = tripRepository.save(testTrip);
    }

    private TripEntity initComments(TripEntity testTrip) {
        CommentEntity comment1 = new CommentEntity();
        comment1.setAuthor(user);
        comment1.setCreated(LocalDateTime.now());
        comment1.setTextContent(COMMENT_1);
        comment1.setTrip(testTrip);

        CommentEntity comment2 = new CommentEntity();
        comment2.setAuthor(user);
        comment2.setCreated(LocalDateTime.now());
        comment2.setTextContent(COMMENT_2);
        comment2.setTrip(testTrip);

        CommentEntity comment3 = new CommentEntity();
        comment3.setAuthor(admin);
        comment3.setCreated(LocalDateTime.now());
        comment3.setTextContent(COMMENT_3);
        comment3.setTrip(testTrip);

        testTrip.setComments(List.of(comment1, comment2, comment3));

        return tripRepository.save(testTrip);
    }

    @Test
    @WithMockUser("vanesaz")
    void testCreateCommentsByLoggedUser() throws Exception {

        CommentAddDto testComment = new CommentAddDto();
        testComment.setMessage(COMMENT_1);

        TripEntity emptyTrip = initTrip();

        mockMvc.perform(post("/api/" + emptyTrip.getId() + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location",
                        MatchesPattern.matchesPattern("/api/" + emptyTrip.getId() + "/comments/" + "\\d+")))
                .andExpect(jsonPath("$.message").value(is(COMMENT_1)))
                .andExpect(jsonPath("$.user", is("Vanesa Zdravkova")));
    }

    @Test
    void testCreateCommentsByAnonymousUser() throws Exception {

        CommentAddDto testTrip = new CommentAddDto();
        testTrip.setMessage(COMMENT_1);

        TripEntity emptyTrip = initTrip();

        mockMvc.perform(post("/api/" + emptyTrip.getId() + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTrip))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void deleteCommentByAnonymousUser() throws Exception {

        TripEntity testTrip = initComments(initTrip());

        mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/api/" + testTrip.getId() + "/comments/" + testTrip.getComments().get(0).getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("vanesaz")
    void deleteCommentByAuthor() throws Exception {

        TripEntity testTrip = initComments(initTrip());

        mockMvc.perform(MockMvcRequestBuilders.delete(
                                "/api/" + testTrip.getId() + "/comments/" + testTrip.getComments().get(0).getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(COMMENT_1)))
                .andExpect(jsonPath("$.user", is("Vanesa Zdravkova")));
    }

    @Test
    @WithMockUser("vanesaz")
    void deleteCommentByNotAuthor() throws Exception {

        TripEntity testTrip = initComments(initTrip());

        mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/api/" + testTrip.getId() + "/comments/" + testTrip.getComments().get(0).getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("admin")
    void deleteCommentByAdmin() throws Exception {

        TripEntity testTrip = initComments(initTrip());

        mockMvc.perform(MockMvcRequestBuilders.delete(
                                "/api/" + testTrip.getId() + "/comments/" + testTrip.getComments().get(0).getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(COMMENT_1)))
                .andExpect(jsonPath("$.user", is("Vanesa Zdravkova")));
    }
}
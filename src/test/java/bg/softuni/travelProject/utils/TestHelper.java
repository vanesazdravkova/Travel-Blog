package bg.softuni.travelProject.utils;

import bg.softuni.travelProject.model.dto.TripSearchDto;
import bg.softuni.travelProject.model.entity.*;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import bg.softuni.travelProject.repository.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestHelper {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TripRepository tripRepository;
    private final TypeRepository typeRepository;
    private final PictureRepository pictureRepository;

    public TestHelper(
            UserRepository userRepository,
            RoleRepository roleRepository,
            TripRepository tripRepository,
            TypeRepository typeRepository,
            PictureRepository pictureRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tripRepository = tripRepository;
        this.typeRepository = typeRepository;
        this.pictureRepository = pictureRepository;
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            RoleEntity adminRole = new RoleEntity().setRole(RoleNameEnum.ADMIN);
            RoleEntity userRole = new RoleEntity().setRole(RoleNameEnum.USER);

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }

    public UserEntity createAdmin(String username) {
        initRoles();

        UserEntity admin = new UserEntity().
                setUsername(username).
                setEmail("admin@example.com").
                setFirstName("Admin").
                setLastName("Adminov").
                setRoles(roleRepository.findAll())
                .setPassword("54321");

        return userRepository.save(admin);
    }

    public UserEntity createUser(String username) {
        initRoles();

        UserEntity user = new UserEntity().
                setUsername(username).
                setEmail("user@example.com").
                setFirstName("User").
                setLastName("Userov").
                setRoles(roleRepository.
                        findAll().stream().
                        filter(r -> r.getRole() != RoleNameEnum.ADMIN).
                        toList())
                .setPassword("54321");

        return userRepository.save(user);
    }

    public TripEntity createTrip(UserEntity author, List<TypeEntity> types) {
        var testTrip = new TripEntity()
                .setName("Paris")
                .setLandmarks("Eiffel Tower")
                .setDescription("My trip to Paris during Spring was fantastic.")
                .setPricingLevel(PricingLevelEnum.LUXURY)
                .setAuthor(author)
                .setContinent(ContinentEnum.EUROPE)
                .setTypes(types)
                .setDays(6)
                .setCountriesVisited(1)
                .setVideoUrl("http//videoUrl")
                .setPictures(new ArrayList<>());

        testTrip = tripRepository.save(testTrip);
        testTrip.getPictures().add(createPicture(author, testTrip));
        return tripRepository.save(testTrip);
    }

    public List<TypeEntity> createTypes() {
        var typeEntityFirst = new TypeEntity().
                setName(TripTypeEnum.BEACH);

        var typeEntitySecond = new TypeEntity().
                setName(TripTypeEnum.MOUNTAIN);

        List<TypeEntity> types = new ArrayList<>();

        types.add(typeRepository.save(typeEntityFirst));
        types.add(typeRepository.save(typeEntitySecond));

        return types;
    }

    public PictureEntity createPicture(UserEntity author, TripEntity trip) {
        var pictureEntity = new PictureEntity().
                setAuthor(author).
                setTrip(trip).
                setPublicId("testPublicId").
                setUrl("testUrl").
                setTitle("testTitle");

        return pictureRepository.save(pictureEntity);
    }

    public void cleanUpDatabase() {
        pictureRepository.deleteAll();
        tripRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        typeRepository.deleteAll();
    }

    public TripRepository getTripRepository() {
        return tripRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PictureRepository getPictureRepository() {
        return pictureRepository;
    }

    public TripSearchDto createTripSearchDto(String name) {
        TripSearchDto tripSearchDto = new TripSearchDto();
        tripSearchDto.setName(name);
        tripSearchDto.setPricingLevel(PricingLevelEnum.LUXURY);
        tripSearchDto.setTypes(List.of(TripTypeEnum.BEACH));
        tripSearchDto.setContinent(ContinentEnum.EUROPE);
        tripSearchDto.setMinDays(3);
        tripSearchDto.setMaxDays(15);

        return tripSearchDto;
    }
}

package bg.softuni.travelProject.scheduling;

import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.service.EmailService;
import bg.softuni.travelProject.service.TripService;
import bg.softuni.travelProject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private TripService tripService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserService userService;
    @InjectMocks
    private ReportService reportService;

    @Test
    void testGenerateDailyReport() {
        LocalDate today = LocalDate.now();
        Instant todayMidnight = today.atStartOfDay(ZoneOffset.UTC).toInstant();

        when(tripService.findCountByContinent(ContinentEnum.ASIA)).thenReturn(1L);
        when(tripService.findCountByContinent(ContinentEnum.AFRICA)).thenReturn(2L);
        when(tripService.findCountByContinent(ContinentEnum.NORTH_AMERICA)).thenReturn(3L);
        when(tripService.findCountByContinent(ContinentEnum.SOUTH_AMERICA)).thenReturn(4L);
        when(tripService.findCountByContinent(ContinentEnum.ANTARCTICA)).thenReturn(5L);
        when(tripService.findCountByContinent(ContinentEnum.EUROPE)).thenReturn(6L);
        when(tripService.findCountByContinent(ContinentEnum.AUSTRALIA)).thenReturn(7L);
        when(tripService.findCountAll()).thenReturn(28L);

        List<String> adminsEmails = Arrays.asList("admin1@example.com", "admin2@example.com");
        when(userService.getAdminsEmails()).thenReturn(adminsEmails);

        reportService.generateDailyReport();

        String expectedReport = String.format("""
            Report on date: %s
                Asian trips: %d
                African trips: %d
                North American trips: %d
                South American trips: %d
                Antarctican trips: %d
                European trips: %d
                Australian trips: %d
                All trips: %d
            """, todayMidnight,
                1, 2, 3, 4, 5, 6, 7, 28
        );

        verify(emailService).sendSimpleMessage("admin1@example.com",
                "This is autogenerated message from your application.",
                expectedReport
        );
        verify(emailService).sendSimpleMessage("admin2@example.com",
                "This is autogenerated message from your application.",
                expectedReport
        );
    }
}

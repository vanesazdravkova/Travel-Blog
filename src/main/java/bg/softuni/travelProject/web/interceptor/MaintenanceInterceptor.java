package bg.softuni.travelProject.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@Configuration
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        var requestURI = request.getRequestURI();
        if (!requestURI.equals("/maintenance")) {
            LocalTime now = LocalTime.now();
            if (now.isAfter(LocalTime.of(2, 0)) && now.isBefore(LocalTime.of(3, 0))) {
                response.sendRedirect("/maintenance");
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

package bg.softuni.travelProject.web.advice;

import bg.softuni.travelProject.web.exception.InvalidTokenException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidTokenAdvice {

    @ExceptionHandler(InvalidTokenException.class)
    public String handleInvalidTokenException(InvalidTokenException ex, Model model) {

        ex.printStackTrace();
        model.addAttribute("message", ex.getMessage());
        return "message";
    }
}

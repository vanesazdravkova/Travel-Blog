package bg.softuni.travelProject.web.advice;

import bg.softuni.travelProject.web.exception.InvalidFileException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidFileUploadAdvice {

    @ExceptionHandler(InvalidFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidFileException(InvalidFileException ex, Model model) {

        ex.printStackTrace();
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/invalidPictureUploadError";
    }
}

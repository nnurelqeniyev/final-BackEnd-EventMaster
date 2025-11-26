package az.edu.itbrains.final_evenmaster.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(NoHandlerFoundException ex) {
        return "error/404"; // templates/error/404.html
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handle403(AccessDeniedException ex) {
        return "error/403"; // templates/error/403.html
    }
}
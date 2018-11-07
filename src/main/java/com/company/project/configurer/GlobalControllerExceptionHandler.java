//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import javax.validation.ConstraintViolationException;
//import java.util.NoSuchElementException;
//
//@Slf4j
//@RestControllerAdvice
//public class GlobalControllerExceptionHandler {
//    @ExceptionHandler(value = { ConstraintViolationException.class })
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiErrorResponse constraintViolationException(ConstraintViolationException ex) {
//        return new ApiErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                5001, ex.getMessage()
//        );
//    }
//
//    @ExceptionHandler(value = { NotFoundException.class })
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiErrorResponse noFoundException(Exception ex) {
//        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), 4041, ex.getMessage());
//    }
//
//    @ExceptionHandler(value = { NoHandlerFoundException.class })
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiErrorResponse noHandlerFoundException(Exception ex) {
//        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), 4041, ex.getMessage());
//    }
//
//    @ExceptionHandler(value = { Exception.class })
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiErrorResponse unknownException(Exception ex) {
//        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                5002,
//                ex.getMessage());
//    }
//
//    @ExceptionHandler(value = {NoSuchElementException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiErrorResponse noSuchElementException(NoSuchElementException ex){
//        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
//                4041,
//                ex.getMessage());
//    }
//}

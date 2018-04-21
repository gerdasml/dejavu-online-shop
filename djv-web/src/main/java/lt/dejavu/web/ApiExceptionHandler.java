package lt.dejavu.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lt.dejavu.auth.exception.ApiSecurityException;
import lt.dejavu.auth.exception.UserNotFoundException;
import lt.dejavu.product.exception.CategoryAlreadyExistException;
import lt.dejavu.product.exception.CategoryNotFoundException;
import lt.dejavu.product.exception.IllegalRequestDataException;
import lt.dejavu.product.exception.ProductAlreadyExistException;
import lt.dejavu.product.exception.ProductNotFoundException;
import lt.dejavu.payment.exception.PaymentException;
import lt.dejavu.storage.image.exception.FileNotFoundException;
import lt.dejavu.storage.image.exception.StorageException;
import lt.dejavu.storage.image.exception.UnsupportedImageFormatException;
import lt.dejavu.web.exception.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Date;

@ControllerAdvice
@RestController
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<ExceptionDetails> handleProductNotFoundException(ProductNotFoundException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public final ResponseEntity<ExceptionDetails> handleUserNotFoundException(ProductAlreadyExistException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public final ResponseEntity<ExceptionDetails> handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public final ResponseEntity<ExceptionDetails> handleCategoryNotFoundException(CategoryAlreadyExistException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    public final ResponseEntity<ExceptionDetails> handleApiSecurityException(IllegalRequestDataException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiSecurityException.class)
    public final ResponseEntity<ExceptionDetails> handleApiSecurityException(ApiSecurityException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ExceptionDetails> handleStorageException(FileNotFoundException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedImageFormatException.class)
    public final ResponseEntity<ExceptionDetails> handleStorageException(StorageException ex, WebRequest req) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentException.class)
    public final ResponseEntity<ExceptionDetails> handlePaymentException(PaymentException ex, WebRequest req) {
        return buildResponse(ex, ex.getResponseStatus());
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ExceptionDetails> handleIOException(IOException ex, WebRequest req) {
        log.error("An IOException occurred. ", ex);
        IOException exc = new IOException("Please contact the admins.");
        return buildResponse(exc, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionDetails> handleGenericException(Exception ex, WebRequest req) {
        log.error("An Exception occurred. ", ex);
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private <T extends Exception> ResponseEntity<ExceptionDetails> buildResponse(T ex, HttpStatus status) {
        ExceptionDetails details = new ExceptionDetails();
        details.setTimestamp(new Date());
        details.setMessage(ex.getMessage());
        details.setType(getExceptionType(ex));
        return new ResponseEntity<>(details, status);
    }

    private String getExceptionType(Exception e) {
        String name = e.getClass().getSimpleName();
        int idx = name.lastIndexOf("Exception");
        return name.substring(0, idx);
    }
}

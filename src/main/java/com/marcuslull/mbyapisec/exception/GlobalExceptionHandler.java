package com.marcuslull.mbyapisec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOError;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handleUserRegistrationExceptions(Exception exception) {
        // UserRegistrationException - extends RuntimeException - Custom Exception to handle any user registration issues

        // Also handling: UsernameAlreadyExistsException InvalidRegistrationFormatException. These exceptions will fall through to here.

        // TODO: Logging the exception message

        System.out.println(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({NumberFormatException.class, HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleBadRequest(Exception exception) {
        // NumberFormatException - extends IllegalArgumentException > RuntimeException - Thrown to indicate that the
        // application has attempted to convert a string to one of the numeric types, but that the string does not have
        // the appropriate format.

        // MethodArgumentTypeMismatchException - extends MethodArgumentResolutionException > RuntimeException -
        // Exception that indicates that a method argument has not the expected type.

        // HttpMessageNotReadableException - extends HttpMessageConversionException > RuntimeException - Thrown by
        // HttpMessageConverter implementations when the HttpMessageConverter.read(java.lang.Class<? extends T>,
        // org.springframework.http.HttpInputMessage) method fails.

        // TODO: Logging the exception message

        System.out.println(exception);
        return new ResponseEntity<>("The path variable or request body is not formatted correctly", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(Exception exception) {
        // NoSuchElementException - extends RuntimeException - Thrown by various accessor methods to indicate that the
        // element being requested does not exist.

        // TODO: Logging the exception message

        System.out.println(exception);
        return new ResponseEntity<>("An object or object ID in your request does not exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class, SecurityException.class})
    public ResponseEntity<String> handleAuthenticationException(Exception exception) {
        // AuthenticationException - extends RuntimeException - Abstract superclass for all exceptions related to an
        // Authentication object being invalid for whatever reason.

        // SecurityException - extends RuntimeException - Thrown by the security manager to indicate a security violation.

        // Also handling: BadCredentialsException, UsernameNotFoundException. They will fall through to here.

        // TODO: Logging the exception message

        System.out.println(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(Exception exception) {
        // RuntimeException - extends Exception - RuntimeException is the superclass of those exceptions that can be
        // thrown during the normal operation of the Java Virtual Machine.

        // Also handling: NullPointerException, UnsupportedOperationException, InvalidPathException, IllegalArgumentException, JwtEncodingException. They will fall through to here.

        // TODO: Logging the exception message

        System.out.println(exception);
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException (Exception exception) {
        // Exception - extends Throwable - The class Exception and its subclasses are a form of Throwable
        // that indicates conditions that a reasonable application might want to catch.

        // Also handling: ServletException, IOException. They will fall through to here.

        // TODO: Logging the exception message

        System.out.println(exception);
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOError.class)
    public ResponseEntity<String> handleIOError(Exception exception) {
        // IOError - extends Error > Throwable - Thrown when a serious I/O error has occurred.

        // TODO: Logging the exception message

        System.out.println(exception);
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.marcuslull.mbyapisec.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // NULL - This will probably end up being several custom exceptions

    // Tiers of Exceptions (more specific > less specific):
    // JwtEncodingException - extends JwtException > RuntimeException - This exception is thrown when an error occurs while attempting to encode a JSON Web Token (JWT).

    // UsernameNotFoundException - extends AuthenticationException > RuntimeException - Thrown if an UserDetailsService implementation cannot locate a User by its username.
    // BadCredentialsException - extends AuthenticationException > RuntimeException - Thrown if an authentication request is rejected because the credentials are invalid. For this exception to be thrown, it means the account is neither locked nor disabled.

    // HttpMessageNotReadableException - extends HttpMessageConversionException > RuntimeException - Thrown by HttpMessageConverter implementations when the HttpMessageConverter.read(java.lang.Class<? extends T>, org.springframework.http.HttpInputMessage) method fails.
    // MethodArgumentTypeMismatchException - extends MethodArgumentResolutionException > RuntimeException - Exception that indicates that a method argument has not the expected type.

    // NumberFormatException - extends IllegalArgumentException > RuntimeException - Thrown to indicate that the application has attempted to convert a string to one of the numeric types, but that the string does not have the appropriate format.

    // NoSuchElementException - extends RuntimeException - Thrown by various accessor methods to indicate that the element being requested does not exist.
    // NullPointerException - extends RuntimeException - Thrown when an application attempts to use null in a case where an object is required.
    // AuthenticationException - extends RuntimeException - Abstract superclass for all exceptions related to an Authentication object being invalid for whatever reason.
    // IllegalArgumentException - extends RuntimeException - Thrown to indicate that a method has been passed an illegal or inappropriate argument.

    // IOException - extends Exception - Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
    // ServletException - extends Exception - Defines a general exception a servlet can throw when it encounters difficulty.

    // Exception - extends Throwable - The class Exception and its subclasses are a form of Throwable that indicates conditions that a reasonable application might want to catch.

}

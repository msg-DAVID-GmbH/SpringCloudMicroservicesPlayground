package mafoe.remoting;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.NestedExceptionUtils;

/**
 * Transforms exceptions that contain non-serializable classes so that the client can still display them without
 * getting {@link ClassNotFoundException}s.
 */
public class CustomExceptionHandler {

    public RuntimeException transform(Exception e) {

        //ideally, the server should convert some exceptions and not others. Also, the server should convert some
        // exceptions with a lot more logic. Method Validation exceptions are javax.validation.ConstraintViolationExceptions
        // with - for some reason - terrible message handling. These should be properly transformed to make them
        // readable on server and client side, without any references to server-side-only classes.

        Throwable mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(e);
        String stackTrace = ExceptionUtils.getStackTrace(mostSpecificCause);
        return new RuntimeException(stackTrace);
    }
}

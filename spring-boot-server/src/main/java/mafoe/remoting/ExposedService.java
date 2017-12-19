package mafoe.remoting;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Exposes a bean via Spring HTTP invoker.
 *
 * @see ExposeServicePostProcessor see ExposeServicePostProcessor for more details
 */
@Service
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ExposedService {
}

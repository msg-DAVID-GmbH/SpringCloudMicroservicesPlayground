package mafoe.remoting;

import org.springframework.remoting.support.DefaultRemoteInvocationExecutor;
import org.springframework.remoting.support.RemoteInvocation;

import java.lang.reflect.InvocationTargetException;

/**
 * Custom RemoteInvocationExecutor to handle exceptions.
 */
public class CustomRemoteInvocationExecutor extends DefaultRemoteInvocationExecutor {

    @Override
    public Object invoke(RemoteInvocation invocation, Object targetObject)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Object result;
        try {
            result = super.invoke(invocation, targetObject);
        } catch (Exception e) {
            throw new CustomExceptionHandler().transform(e);
        }

        return result;
    }
}

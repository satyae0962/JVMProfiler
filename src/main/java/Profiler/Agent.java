package Profiler;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by satyanarayana on 21/03/19.
 */
public class Agent {

    static AtomicReference<Boolean> isActive = new AtomicReference<Boolean>(true);

    private Agent(){}

    public static void agentmain(final String args, final Instrumentation instrumentation){
        premain(args,instrumentation);
    }

    public static void premain(final String args,final Instrumentation instrumentation){
        Arguments arguments = Arguments.parseArguments(args);


    }


    private static <T> T initiate(final Class<T> clazz, Class<?>[] paramType,Object... initArgs){
        try{
            Constructor<T> constructor = clazz.getConstructor(paramType);
            return constructor.newInstance(initArgs);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    return  null;
    }


}

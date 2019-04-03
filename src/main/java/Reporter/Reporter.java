package Reporter;

import Profiler.Arguments;
import com.google.common.base.Preconditions;

/**
 * Created by satyanarayana on 03/04/19.
 */
public abstract class Reporter<T> {
    public static final Class<?>[] CONSTRUCTOR_PARAM_TYPES = new Class<?>[]{Arguments.class};

    protected T client;

    public Reporter(Arguments arguments) {
        Preconditions.checkNotNull(arguments);
        handleArguments(arguments);
        client = createClient(arguments.server, arguments.port, arguments.prefix);
    }

    public abstract void handleArguments(Arguments arguments);

    protected abstract T createClient(String server, int port, String prefix);
}

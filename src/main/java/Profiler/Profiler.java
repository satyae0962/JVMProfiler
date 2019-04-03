package Profiler;

import Reporter.Reporter;
import com.google.common.base.Preconditions;

/**
 * Created by satyanarayana on 23/03/19.
 */
public abstract class Profiler {

    public static final Class<?>[] CONSTRUCTOR_PARAM_TYPES  = new Class<?>[]{Reporter.class,Arguments.class};

    private final Reporter<?> reporter;

    private long recordedStats = 0;

    public Profiler(Reporter reporter, Arguments arguments){
        Preconditions.checkNotNull(reporter);
        this.reporter=reporter;
        handleArgs(arguments);

    }

    protected abstract void handleArgs(Arguments arguments);

    protected abstract void recordGaugeValues(String key, double value);

    public abstract void flushData();

    public abstract void getPeriod();

    public abstract void profile();

}

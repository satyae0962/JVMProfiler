package Reporter;

import java.util.Map;

/**
 * Created by satyanarayana on 03/04/19.
 */
public interface ReporterInf {

    void report(String profilerName, Map<String,Object> metrics);

    void close();

}

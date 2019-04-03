package Reporter;

import Profiler.Arguments;
import org.apache.spark.sql.kafka010.JsonUtils;

import java.util.Map;

/**
 * Created by satyanarayana on 03/04/19.
 */
public class ConsoleReporter implements ReporterInf {

    public void report(String profilerName, Map<String, Object> metrics) {
        System.out.println(String.format("Console Output Reporter - %s %s", profilerName, Utils.JsonUtils.serialize(metrics)));
    }

    public void close() {

    }
}

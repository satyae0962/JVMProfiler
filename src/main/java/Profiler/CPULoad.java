package Profiler;

import Reporter.Reporter;
import com.google.common.collect.ImmutableMap;
import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Map;

/**
 * Created by satyanarayana on 03/04/19.
 */

public class CPULoad extends Profiler {

    private AttributeList list;
    private static final Map<String,String> ATTRIBUTES_MAP = ImmutableMap.of("ProcessCpuLoad","cpu.jvm","SystemCpuLoad","cpu.system");

    public CPULoad(Reporter reporter, Arguments arguments) {
        super(reporter, arguments);
        try{
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = ObjectName.getInstance("java.lang:type=OperatingSystem");
            list = mBeanServer.getAttributes(objectName,ATTRIBUTES_MAP.keySet().toArray(new String[ATTRIBUTES_MAP.size()]));
        }
        catch (MalformedObjectNameException e) {
            list = null;
        }
        catch (InstanceNotFoundException e){
            list = null;
        }
        catch (ReflectionException e){
            list = null;
        }
    }

    @Override
    protected void handleArgs(Arguments arguments) {
        /* NO ARGUMENTS NEEDED FOR THIS PROFILER*/
    }

    @Override
    protected void recordGaugeValues(String key, double value) {

    }

    @Override
    public void flushData() {
        recordStats();
    }

    @Override
    public void getPeriod() {

    }

    @Override
    public void profile() {
        recordStats();
    }

    private void recordStats(){
        if(list == null){
            return;
        }

        Attribute attribute;
        Double value;
        String metric;

        for(Object obj: list){
            attribute = (Attribute) obj;
            value = (Double) attribute.getValue();

            metric = ATTRIBUTES_MAP.get(attribute.getName());

            value = ((int) (value*1000))/ 10.0d;
            recordGaugeValues(metric,value);
        }

    }
}

package Profiler;

import Reporter.Reporter;
import com.google.common.base.*;

import java.util.*;

/**
 * Created by satyanarayana on 21/03/19.
 */
public class Arguments {

    private static final String SERVER = "server";
    private static final String PORT = "port";
    private static final String PROFILERS = "profiler";
    private static final String REPORTERS = "reporter";
    private static final String METRICS_PREFIX = "prefix";

    public String server;
    public int port;
    public String prefix;
    public Set<Class<? extends Profiler>> profiler;
    public Class<? extends Reporter<?>> reporter;

    private Arguments(Map<String, String> parsedArgs) {
        server = parsedArgs.get(SERVER);
        port = Integer.parseInt(parsedArgs.get(PORT));
        prefix = com.google.common.base.Optional.fromNullable(parsedArgs.get(METRICS_PREFIX)).or("JVM PROFILERS")
        profiler = ProfilerArgs(parsedArgs.get(PROFILERS));
        reporter = ReporterArgs(parsedArgs.get(REPORTERS));

        System.out.println("Clearing the Map after variable assignment");

        parsedArgs.remove(SERVER);
        parsedArgs.remove(PORT);
        parsedArgs.remove(PROFILERS);
        parsedArgs.remove(REPORTERS);

    }

    public static Arguments parseArguments(String args) {
        Map<String, String> parsedArgs = new HashMap<String, String>();
        for (String parser : args.split(",")) {
            String[] tokens = args.split("=");
            if (tokens.length != 2)
                throw new IllegalArgumentException("JVM Profiler takes a comma-delimited list of arguments " +
                        "in key=value form");
            parsedArgs.put(tokens[0], tokens[1]);

        }

        return new Arguments(parsedArgs);
    }

    private static Class<? extends Reporter<?>> ReporterArgs(String reporterArgs) {
        if (reporterArgs == null)
            return null;
        else {
            try {
                return (Class<? extends Reporter<?>>) Class.forName(reporterArgs);
            } catch (ClassNotFoundException e) {
                try {
                    return (Class<? extends Reporter<?>>) Class.forName("Profiler.Reporter");
                } catch (ClassNotFoundException o) {
                    throw new IllegalArgumentException("Reporter" + reporterArgs + " not found");
                }
            }

        }
    }

    private static Set<Class<? extends Profiler>> ProfilerArgs(String profilerArgs){
        Set<Class<? extends  Profiler>> profilerParser = new HashSet<Class<? extends Profiler>>();
        if(profilerArgs == null) {
            System.out.println("Run some default profilers here");
        }
        else{
            for(String p: profilerArgs.split(":")){
                try{
                    profilerParser.add((Class<? extends Profiler>) Class.forName(p) );
                }
                catch (ClassNotFoundException e){
                    try {
                        profilerParser.add((Class<? extends Profiler>) Class.forName("Profiler.Profiler"));
                        }
                        catch(ClassNotFoundException o){
                            throw new IllegalArgumentException("Profiler " + p + " not found");
                        }
                    }
                }
            }
            if(profilerParser.isEmpty()){
            throw new IllegalArgumentException("Need atleast 1 profiler to be mentioned");
            }
            return profilerParser;
        }

    }



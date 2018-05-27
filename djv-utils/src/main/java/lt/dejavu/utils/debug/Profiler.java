package lt.dejavu.utils.debug;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class Profiler {
    private final static Logger log = LogManager.getLogger(Profiler.class);

    public static void time(String operationName, Runnable runnable) {
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        log.debug(String.format("%s\t%s ms", operationName, Duration.between(start, end).toMillis()));
    }

    public static <T> T time(String operationName, Supplier<T> prod) {
        Instant start = Instant.now();
        T result = prod.get();
        Instant end = Instant.now();
        log.debug(String.format("%s\t%s ms", operationName, Duration.between(start, end).toMillis()));
        return result;
    }
}

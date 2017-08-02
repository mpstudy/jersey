package mps.study.utils;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class TaskExecutor implements Executor {
    private static Logger log = LoggerFactory.getLogger(TaskExecutor.class);
    private final Executor delegate = Executors.newFixedThreadPool(4);

    @Override
    public void execute(Runnable command) {
        delegate.execute(command);
    }
}

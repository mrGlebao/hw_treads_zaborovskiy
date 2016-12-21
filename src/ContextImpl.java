/**
 * Created by zabor on 04.12.2016.
 */
public class ContextImpl implements Context {
    private Thread[] threadPool;
    ExecutionManager executor;

    public ContextImpl(ExecutionManager exec, Thread... pool) {
        this.threadPool = pool;
        this.executor = exec;
    }

    @Override
    public int getCompletedTaskCount() {
        int completedTasks = 0;
        for (Thread t : threadPool) {
            if (t.getState().equals(Thread.State.TERMINATED)) {
                completedTasks += 1;
            }
        }
        return completedTasks;
    }

    @Override
    public int getFailedTaskCount() {
        return executor.getFailedTasksCount();
    }

    @Override
    public int getInterruptedTaskCount() {
        int interruptedTasks = 0;
        for (Thread t : threadPool) {
            if (t.isInterrupted()) {
                interruptedTasks += 1;
            }
        }
        return interruptedTasks;
    }

    @Override
    public void interrupt() {
        for (Thread t : threadPool) {
            if (t.getState().equals(Thread.State.NEW)) {
                t.interrupt();
            }
        }
    }

    @Override
    public boolean isFinished() {
        int goodThreads = 0;
        for (Thread t : threadPool) {
            if (t.isInterrupted() || t.getState().equals(Thread.State.TERMINATED)) {
                goodThreads+=1;
            }
        }
        return goodThreads == threadPool.length;
    }
}

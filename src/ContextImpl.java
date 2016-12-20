/**
 * Created by zabor on 04.12.2016.
 */
public class ContextImpl implements Context {
    private Thread[] threadPool;

    public ContextImpl(Thread... pool) {
        this.threadPool = pool;
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
        int notFailed = 0;
        for (Thread t : threadPool) {
            Thread.State state = t.getState();
            if (state.equals(Thread.State.NEW)
                    || state.equals(Thread.State.TERMINATED)
                    || state.equals(Thread.State.BLOCKED)
                    || state.equals(Thread.State.RUNNABLE)
                    || state.equals(Thread.State.TIMED_WAITING)
                    || state.equals(Thread.State.WAITING)) {
                notFailed += 1;
            }
        }
        return threadPool.length - notFailed;
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

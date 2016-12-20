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
        for (Thread r : threadPool) {
            if (r.getState().equals(Thread.State.TERMINATED)) {
                completedTasks += 1;
            }
        }
        return completedTasks;
    }

    @Override
    public int getFailedTaskCount() {
        int notFailed = 0;
        for (Thread r : threadPool) {
            Thread.State state = r.getState();
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
        for (Thread r : threadPool) {
            if (r.isInterrupted()) {
                interruptedTasks += 1;
            }
        }
        return interruptedTasks;
    }

    @Override
    public void interrupt() {
        for (Thread r : threadPool) {
            if (r.getState().equals(Thread.State.NEW)) {
                r.interrupt();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

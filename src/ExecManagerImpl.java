/**
 * Created by zabor on 04.12.2016.
 */
public class ExecManagerImpl implements ExecutionManager {
    private Thread[] threadPool;
    private Runnable callback;
    private Context context;
    int failedThreads;


    private Thread manager = new Thread() {
        @Override
        public void run() {
            manageThreads(callback, threadPool);
        }
    };

    private void manageThreads(Runnable callback, Thread... tasks) {
        for (Thread t : tasks) {
            if (!t.isInterrupted()) {
                try {
                    t.run();
                } catch (Exception e) {
                    failedThreads += 1;
                }
            }
        }
        callback.run();
    }

    @Override
    public Context execute(Runnable callback, Runnable... pool) {
        this.threadPool = new Thread[pool.length];
        for (int i = 0; i < pool.length; i++) {
            this.threadPool[i] = new Thread(pool[i]);
        }
        this.callback = callback;
        this.context = new ContextImpl(threadPool);
        manager.run();
        return context;
    }

}

/**
 * Created by zabor on 04.12.2016.
 */
public class ExecManagerImpl implements ExecutionManager {
    private Runnable[] pool;
    private Runnable callback;
    private Context context;
    int failedThreads;


    private Thread manager = new Thread() {
        @Override
        public void run() {
            manageThreads(callback, pool);
        }
    };

    private void manageThreads(Runnable callback, Runnable... tasks) {
        for (Runnable r : pool) {
            try {
                r.run();
            } catch (Exception e) {
                failedThreads += 1;
            }
        }
        callback.run();
    }

    @Override
    public Context execute(Runnable callback, Runnable... pool) {
        this.context = new ContextImpl(callback, pool);
        this.pool = pool;
        this.callback = callback;
        manager.run();
        return context;
    }

}

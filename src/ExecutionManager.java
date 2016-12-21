/**
 * Created by zabor on 04.12.2016.
 */
public interface ExecutionManager {

    Context execute(Runnable callback, Runnable... tasks);

    int getFailedTasksCount();
}

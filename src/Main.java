/**
 * Created by zabor on 16.12.2016.
 */
public class Main {
    static Runnable callback = new Thread() {

        @Override
        public void run() {
            System.out.println("Callback!");
        }
    };

    static Runnable run1 = new Thread() {

        @Override
        public void run() {
            System.out.println("run1!");
        }
    };

    static Runnable run2 = new Thread() {

        @Override
        public void run() {
            System.out.println("run2!");
        }
    };

    static Runnable run3 = new Thread() {

        @Override
        public void run() {
            System.out.println("run3!");
            throw new NullPointerException();
        }
    };

    public static void main(String[] args) {
        ExecutionManager exec = new ExecManagerImpl();
        Context c = exec.execute(callback, run1, run2, run3);
        System.out.println("!" + c.getFailedTaskCount());
    }
}

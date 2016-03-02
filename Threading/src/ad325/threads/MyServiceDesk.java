package ad325.threads;

/**
 * Created by nate on 3/1/16.
 */
public class MyServiceDesk {
    public static void main(String[] args) {
        // make the basic simulation
        final ServiceDesk help = new ServiceDeskBase();

        // create three customers
        new Thread(
                new Runnable() {
                    public void run() {
                        Customer one = new Customer('*');
                        help.desk1(one);
                    }
                }).start();

        new Thread(
                new Runnable() {
                    public void run() {
                        Customer two = new Customer('&');
                        help.desk2(two);
                    }
                }).start();

        new Thread(
                new Runnable() {
                    public void run() {
                        Customer three = new Customer('@');
                        help.desk3(three);
                    }
                }).start();
    }
}

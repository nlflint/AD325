package ad325.threads;

/**
 * Created by nate on 3/1/16.
 */
public class MyServiceDesk extends ServiceDeskBase {
    private boolean desk1Running;
    private boolean desk2Running;
    private boolean desk3Running;

    public static void main(String[] args) {
        // make the basic simulation
        final ServiceDesk help = new MyServiceDesk();

        // create three customers
        Customer one = new Customer('*');
        Customer two = new Customer('&');
        Customer three = new Customer('@');

        help.desk1(one);
        help.desk1(two);
        help.desk1(three);

        help.desk2(two);
        help.desk2(one);
        help.desk2(three);

        help.desk3(three);
        help.desk3(one);
        help.desk3(two);
    }

    /**
     * The basic actions for the first service desk,
     * the green one.
     */
    @Override
    public void desk1(Customer c) {
        enqueue1(c);

        if (desk1Running)
            return;

        desk1Running = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!peek1().equals("")) {
                    process1(peek1());
                }
                desk1Running = false;

            }
        }).start();

    }

    public void desk2(Customer c) {
        enqueue2(c);

        if (desk2Running)
        return;

        desk2Running = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!peek2().equals("")) {
                    process2(peek2());
                }
                desk2Running = false;

            }
        }).start();
    }

    public void desk3(Customer c) {
        enqueue3(c);

        if (desk3Running)
            return;

        desk3Running = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!peek3().equals("")) {
                    process3(peek3());
                }
                desk3Running = false;

            }
        }).start();
    }
}

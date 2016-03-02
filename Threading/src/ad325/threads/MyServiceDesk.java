package ad325.threads;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Created by nate on 3/1/16.
 */
public class MyServiceDesk extends ServiceDeskBase {
    private HashMap<Character, HashMap<Desk, ProcessFunction>> deskOrderByCustomer;
    final DeskController desk1;
    final DeskController desk2;
    final DeskController desk3;

    public static void main(String[] args) {
        // make the basic simulation
        final ServiceDesk help = new MyServiceDesk();

        // create three customers
        Customer one = new Customer('*');
        Customer two = new Customer('&');
        Customer three = new Customer('@');

        // Kick start the process for each customer
        help.desk1(one);
        help.desk2(two);
        help.desk3(three);
    }

    public MyServiceDesk() {
        super();
        deskOrderByCustomer = createDeskOrderByCustomer();

        desk1 = new DeskController(this::enqueue1
            , this::peek1
            , () -> isEmpty(queue1)
            , this::process1
            , Desk.Desk1);

        desk2 = new DeskController(this::enqueue2
                , this::peek2
                , () -> isEmpty(queue2)
                , this::process2
                , Desk.Desk2);

        desk3 = new DeskController(this::enqueue3
                , this::peek3
                , () -> isEmpty(queue3)
                , this::process3
                , Desk.Desk3);
    }

    private HashMap<Character, HashMap<Desk, ProcessFunction>> createDeskOrderByCustomer() {
        HashMap<Desk, ProcessFunction> customerOneDeskOrder = new HashMap<>();
        customerOneDeskOrder.put(Desk.Desk1, this::desk3);
        customerOneDeskOrder.put(Desk.Desk3, this::desk2);
        customerOneDeskOrder.put(Desk.Desk2, this::done);

        HashMap<Character, HashMap<Desk, ProcessFunction>> deskOrderByCustomer = new HashMap<>();
        deskOrderByCustomer.put('*', customerOneDeskOrder);

        HashMap<Desk, ProcessFunction> customerTwoDeskOrder = new HashMap<>();
        customerTwoDeskOrder.put(Desk.Desk2, this::desk1);
        customerTwoDeskOrder.put(Desk.Desk1, this::desk3);
        customerTwoDeskOrder.put(Desk.Desk3, this::done);
        deskOrderByCustomer.put('&', customerTwoDeskOrder);

        HashMap<Desk, ProcessFunction> customerThreeDeskOrder = new HashMap<>();
        customerThreeDeskOrder.put(Desk.Desk3, this::desk2);
        customerThreeDeskOrder.put(Desk.Desk2, this::desk1);
        customerThreeDeskOrder.put(Desk.Desk1, this::done);
        deskOrderByCustomer.put('@', customerThreeDeskOrder);

        return deskOrderByCustomer;
    }

    private void done(Customer c) {/* does nothing */};

    private void queueNextDesk(Customer c, Desk completedDesk) {
        deskOrderByCustomer.get(c.id).get(completedDesk).process(c);
    }

    /**
     * The basic actions for the first service desk,
     * the green one.
     */
    @Override
    public void desk1(Customer c) {
        desk(desk1, c);
    }

    private void desk(DeskController desk, Customer c) {
        desk.enqueue(c);

        if (desk.isRunning())
            return;

        desk.setRunning(true);

        new Thread(() -> {
            while(!desk.isEmpty()) {
                Customer customer = desk.peek();
                desk.process(customer);
                queueNextDesk(customer, desk.getDeskEnum());
            }
            desk.setRunning(false);
        }).start();

    }

    public void desk2(Customer c) {
        desk(desk2, c);
    }

    public void desk3(Customer c) {
        desk(desk3, c);
    }

    private boolean isEmpty(String queue) {
        return queue.length() < 1;
    }
}

enum Desk {
    Desk1,
    Desk2,
    Desk3
}

@FunctionalInterface
interface CustomerHandler{
    void run(Customer c, String desk);
}

@FunctionalInterface
interface EnqueueFunction{
    void enqueue(Customer c);
}

@FunctionalInterface
interface PeekFunction{
    Customer peek();
}

@FunctionalInterface
interface IsQueueEmptyFunction{
    boolean isEmpty();
}

@FunctionalInterface
interface ProcessFunction{
    void process(Customer c);
}

class DeskController {
    private final EnqueueFunction enqueueFunc;
    private final PeekFunction peekFunc;
    private final IsQueueEmptyFunction isEmptyFunc;
    private final ProcessFunction processFunc;
    private final Desk desk;
    private boolean deskIsRunning;

    DeskController(EnqueueFunction enqueueFunc
            , PeekFunction peekFunc
            , IsQueueEmptyFunction isEmptyFunc
            , ProcessFunction processFunc
            , Desk desk) {
        this.enqueueFunc = enqueueFunc;
        this.peekFunc = peekFunc;
        this.isEmptyFunc = isEmptyFunc;
        this.processFunc = processFunc;
        this.desk = desk;
    }

    public boolean isRunning() {
        return deskIsRunning;
    }

    public void setRunning(boolean runningStatus) {
        this.deskIsRunning = runningStatus;
    }

    public Customer peek() {
        return peekFunc.peek();
    }

    public void enqueue(Customer c) {
        enqueueFunc.enqueue(c);
    }

    public boolean isEmpty() {
        return isEmptyFunc.isEmpty();
    }

    public void process(Customer c) {
        processFunc.process(c);
    }

    public Desk getDeskEnum() {
        return desk;
    }
}

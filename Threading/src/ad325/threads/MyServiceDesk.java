package ad325.threads;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by nate on 3/1/16.
 */
public class MyServiceDesk extends ServiceDeskBase {
    private Map<Character, Queue<DeskController>> deskVisitOrderPerCustomer;
    final DeskController desk1;
    final DeskController desk2;
    final DeskController desk3;

    public static void main(String[] args) {
        // make the basic simulation
        final MyServiceDesk help = new MyServiceDesk();

        // Kick start the process for each customer
        help.queueNextVisitFor(new Customer('*'));
        help.queueNextVisitFor(new Customer('&'));
        help.queueNextVisitFor(new Customer('@'));
    }

    public MyServiceDesk() {
        super();
        desk1 = new DeskController(this::enqueue1
                , this::peek1
                , () -> isEmpty(queue1)
                , this::process1);

        desk2 = new DeskController(this::enqueue2
                , this::peek2
                , () -> isEmpty(queue2)
                , this::process2);

        desk3 = new DeskController(this::enqueue3
                , this::peek3
                , () -> isEmpty(queue3)
                , this::process3);

        deskVisitOrderPerCustomer = createDeskVisitOrderPerCustomer();
    }

    private boolean isEmpty(String queue) {
        return queue.length() < 1;
    }

    private Map<Character, Queue<DeskController>> createDeskVisitOrderPerCustomer() {
        Map<Character, Queue<DeskController>> deskVisitOrderPerCustomer = new HashMap<>();
        deskVisitOrderPerCustomer.put('*', createVisitOrderFrom(desk1, desk3, desk2));
        deskVisitOrderPerCustomer.put('&', createVisitOrderFrom(desk2, desk1, desk3));
        deskVisitOrderPerCustomer.put('@', createVisitOrderFrom(desk3, desk2, desk1));

        return deskVisitOrderPerCustomer;
    }

    private Queue<DeskController> createVisitOrderFrom(DeskController... deskControllers) {
        return new ArrayDeque<>(Arrays.asList(deskControllers));
    }

    public void queueNextVisitFor(Customer c) {
        Queue<DeskController> deskControllerQueue = deskVisitOrderPerCustomer.get(c.id);

        if (deskControllerQueue.peek() == null)
            return;

        DeskController desk = deskControllerQueue.remove();
        queueCustomerOnDesk(c, desk);
    }

    private void queueCustomerOnDesk(Customer c, DeskController deskController) {
        deskController.enqueue(c);

        if (!deskController.isRunning())
            runServiceDesk(deskController);
    }

    private void runServiceDesk(DeskController controller) {
        controller.setRunning(true);

        new Thread(() -> {
            while(!controller.isEmpty()) {
                Customer customer = controller.peek();
                controller.process(customer);
                queueNextVisitFor(customer);
            }
            controller.setRunning(false);
        }).start();
    }
}

class DeskController {
    private final Consumer<Customer> enqueueFunc;
    private final Supplier<Customer> peekFunc;
    private final BooleanSupplier isEmptyFunc;
    private final Consumer<Customer> processFunc;
    private boolean deskIsRunning;

    DeskController(Consumer<Customer> enqueueFunc
            , Supplier<Customer> peekFunc
            , BooleanSupplier isEmptyFunc
            , Consumer<Customer> processFunc) {
        this.enqueueFunc = enqueueFunc;
        this.peekFunc = peekFunc;
        this.isEmptyFunc = isEmptyFunc;
        this.processFunc = processFunc;
    }

    public boolean isRunning() {
        return deskIsRunning;
    }

    public void setRunning(boolean runningStatus) {
        this.deskIsRunning = runningStatus;
    }

    public Customer peek() {
        return peekFunc.get();
    }

    public void enqueue(Customer c) {
        enqueueFunc.accept(c);
    }

    public boolean isEmpty() {
        return isEmptyFunc.getAsBoolean();
    }

    public void process(Customer c) { processFunc.accept(c); }
}

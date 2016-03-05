package ad325.threads;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *  This class process customers on services desk in concurrent processes using threads.
 *  The desk and customers are displayed visually in a JFrame.
 *
 *  @author Nathan Flint
 *  Level: Plus
 */
public class MyServiceDesk extends ServiceDeskBase {
    // A map of queues. Each queue is the desk visit order for a customer. The Map is by customer symbol.
    private Map<Character, Queue<DeskController>> deskVisitOrderPerCustomer;
    // All the data needs to manipulate and examine desk1
    final DeskController desk1;
    // All the data needs to manipulate and examine desk2
    final DeskController desk2;
    // All the data needs to manipulate and examine desk3
    final DeskController desk3;

    /**
     * Starts the help desk process.
     * @param args command line args. None accepted.
     */
    public static void main(String[] args) {
        // make the basic simulation
        final MyServiceDesk help = new MyServiceDesk();

        // Kick start the process for each customer
        help.desk1(new Customer('*'));
        help.desk2(new Customer('&'));
        help.desk3(new Customer('@'));
    }

    // Constructor. Builds the desk objects.
    public MyServiceDesk() {
        super();
        desk1 = new DeskController(this::enqueue1
                , this::peek1
                , () -> isEmpty(queue1)
                , this::process1
                , this::desk1);

        desk2 = new DeskController(this::enqueue2
                , this::peek2
                , () -> isEmpty(queue2)
                , this::process2
                , this::desk2);

        desk3 = new DeskController(this::enqueue3
                , this::peek3
                , () -> isEmpty(queue3)
                , this::process3
                , this::desk3);

        deskVisitOrderPerCustomer = createDeskVisitOrderPerCustomer();
    }

    // Identifies if the queue for the given desk is empty
    private boolean isEmpty(String queue) {
        return queue.length() < 1;
    }

    // Used by constructor to setup the visit order for each customer
    private Map<Character, Queue<DeskController>> createDeskVisitOrderPerCustomer() {
        Map<Character, Queue<DeskController>> deskVisitOrders = new HashMap<>();
        deskVisitOrders.put('*', createVisitOrderOf(desk2, desk3));
        deskVisitOrders.put('&', createVisitOrderOf(desk1, desk3));
        deskVisitOrders.put('@', createVisitOrderOf(desk2, desk1));

        return deskVisitOrders;
    }

    // Helper to create a queue from the given array.
    private Queue<DeskController> createVisitOrderOf(DeskController... deskControllers) {
        return new ArrayDeque<>(Arrays.asList(deskControllers));
    }

    // Finds the next desk for the given customer starts the customer on that desk.
    private void queueNextVisitFor(Customer c) {
        Queue<DeskController> deskVisitOrderQueue = deskVisitOrderPerCustomer.get(c.id);

        if (deskVisitOrderQueue.peek() == null)
            return;

        DeskController deskController = deskVisitOrderQueue.remove();


        deskController.deskfunc(c);
    }

    /**
     * Enqueues the given customer on desk1.
     * @param c The customer to queue
     */
    @Override
    public void desk1(Customer c) {
        enqueueCustomerOnDesk(c, desk1);
    }

    /**
     * Enqueues the given customer on desk2.
     * @param c The customer to queue
     */
    @Override
    public void desk2(Customer c) {
        enqueueCustomerOnDesk(c, desk2);
    }

    /**
     * Enqueues the given customer on desk3.
     * @param c The customer to queue
     */
    @Override
    public void desk3(Customer c) {
        enqueueCustomerOnDesk(c, desk3);
    }

    // Enqueues the customer on the given desk. Then runs the desk if it's not already running.
    private void enqueueCustomerOnDesk(Customer c, DeskController deskController) {
        deskController.enqueue(c);

        if (deskController.isRunning())
            return;

        deskController.setRunning(true);

        new Thread(() -> {
            while(!deskController.isEmpty()) {
                Customer customer = deskController.peek();
                deskController.process(customer);
                queueNextVisitFor(customer);
            }
            deskController.setRunning(false);
        }).start();
    }

    private class DeskController {
        // the enqueue function for this desk
        private final Consumer<Customer> enqueueFunc;
        // The desk function that puts a customer on this desk
        private final Consumer<Customer> deskFunc;
        // The peek function that peek as the queue for this desk
        private final Supplier<Customer> peekFunc;
        // Checks if the queue is emptry for this desk
        private final BooleanSupplier isEmptyFunc;
        // Function that initiates customer service desk for this desk
        private final Consumer<Customer> processFunc;
        // identifies if this service desk is processing a customer
        private boolean deskIsRunning;

        /**
         *
         * @param enqueueFunc enqueue functino for this desk
         * @param peekFunc peek functino for this desk
         * @param isEmptyFunc function to identify any customers are queued on this desk
         * @param processFunc function that start service on this desk
         * @param deskFunc // The desk function that puts a customer on this desk
         */
        DeskController(Consumer<Customer> enqueueFunc
                , Supplier<Customer> peekFunc
                , BooleanSupplier isEmptyFunc
                , Consumer<Customer> processFunc
                , Consumer<Customer> deskFunc) {
            this.enqueueFunc = enqueueFunc;
            this.peekFunc = peekFunc;
            this.isEmptyFunc = isEmptyFunc;
            this.processFunc = processFunc;
            this.deskFunc = deskFunc;
        }

        /**
         * identifies if this desk is running
         * @return if the desk is running
         */
        public boolean isRunning() {
            return deskIsRunning;
        }

        /**
         * Sets the running status for this desk
         * @param runningStatus true if the desk is running
         */
        public void setRunning(boolean runningStatus) {
            this.deskIsRunning = runningStatus;
        }

        /**
         * Peeks at the next customer on the desk queue
         * @return The next customer
         */
        public Customer peek() {
            return peekFunc.get();
        }

        /**
         * Enqueues the given customer on this desk
         * @param c the customer to enqueue
         */
        public void enqueue(Customer c) { enqueueFunc.accept(c); }

        /**
         * Identifies if this desk has any customers enqueued
         * @return true if the desk has no customers enqueued
         */
        public boolean isEmpty() {
            return isEmptyFunc.getAsBoolean();
        }

        /**
         * The desk function that enqueues customer and starts processing if its not running
         * @param c The customer to enqueue
         */
        public void deskfunc(Customer c) {
            deskFunc.accept(c);
        }

        /**
         * Starts the UI process with the given customer
         * @param c customer to start processing.
         */
        public void process(Customer c) { processFunc.accept(c); }
    }
}




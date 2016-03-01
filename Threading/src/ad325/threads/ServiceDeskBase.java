package ad325.threads;

import java.awt.*;
import javax.swing.*;

public class ServiceDeskBase extends JPanel implements ServiceDesk {
    
    // set up the shared data for the simulation
    // the window
    JFrame win;
    // values for the first service desk
    int desk1x;
    int desk1y;
    char desk1sym;
    String queue1;
    // values for the second service desk
    int desk2x;
    int desk2y;
    char desk2sym;
    String queue2;
    // values for the third service desk
    int desk3x;
    int desk3y;
    char desk3sym;
    String queue3;
    
    // a display font
    public static final Font DISPLAY = new Font("Arial", Font.BOLD, 18);
    
    /**
     * The constructor for the simulation.
     */
    public ServiceDeskBase() {
        // set up the window
        win = new JFrame("Service Desk");
        win.setSize(400, 300);
        win.setLocation(25, 25);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win.add(this);
        
        // set up the desk data
        desk1x = 35;
        desk1y = 30;
        desk1sym = ' ';
        queue1 = "";
        desk2x = 155;
        desk2y = 30;
        desk2sym = ' ';
        queue2 = "";
        desk3x = 275;
        desk3y = 30;
        desk3sym = ' ';
        queue3 = "";
        
        // make the window visible
        win.setVisible(true);
    }

    /**
     * The application, the starting point
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        // make the basic simulation
        ServiceDesk help = new ServiceDeskBase();

        // create three customers
        Customer one = new Customer('*');
        help.desk1(one);

        Customer two = new Customer('&');
        help.desk2(two);

        Customer three = new Customer('@');
        help.desk3(three);
    }

    /**
     * The basic actions for the first service desk,
     * the green one.
     */
    public void desk1(Customer c) {
        enqueue1(c);
        process1(c);
    }
    
    // put the customer in the queue
    final void enqueue1(Customer c) {
        queue1 += c;
        win.repaint();
    }
    // peek at the first customer in the queue
    final Customer peek1() {
        return new Customer(queue1.charAt(0));
    }
    // remove the customer from the queue, when finished
    final void dequeue1() {
        queue1 = queue1.substring(1);
        desk1sym = ' ';
        win.repaint();
    }
    // show the work done at the service desk
    final void process1(Customer cust) {
        // start (35, 30);
        int x = 35;
        int y = 30;
        char c = cust.toString().charAt(0);
        draw1(x, y, c, 500);
        for(; y < 100; y += 2)
            draw1(x, y, c, 40);
        for(; x < 200; x += 2)
            draw1(x, y, c, 40);
        for(; y < 230; y += 2)
            draw1(x, y, c, 40);
        for(; x > 100; x -= 2)
            draw1(x, y, c, 40);
        for(; y > 75; y -= 2)
            draw1(x, y, c, 40);
        for(; x > 35; x -= 2)
            draw1(x, y, c, 40);
        x = 35;
        draw1(x, y, c, 40);
        for(; y > 30; y -= 2)
            draw1(x, y, c, 40);
        y = 30;
        draw1(x, y, c, 500);
        dequeue1();
        draw1(35, 30, ' ', 10);
    }
    // helper method to draw the service desk work
    final void draw1(int x, int y, char c, int pause) {
        desk1x = x;
        desk1y = y;
        desk1sym = c;
        win.repaint();
        try {
            Thread.sleep(pause);
        } catch(InterruptedException e) {
        }
    }
    
    public void desk2(Customer c) {
        enqueue2(c);
        process2(c);
    }
    
    // put the customer in the queue
    final void enqueue2(Customer c) {
        queue2 += c;
        win.repaint();
    }
    // peek at the first customer in the queue
    final Customer peek2() {
        return new Customer(queue2.charAt(0));
    }
    // remove the customer from the queue, when finished
    final void dequeue2() {
        queue2 = queue2.substring(1);
        desk2sym = ' ';
        win.repaint();
    }
    // show the work done at the service desk
    final void process2(Customer cust) {
        int x = 155;
        int y = 30;
        char c = cust.toString().charAt(0);
        draw2(x, y, c, 500);
        for(int i = 0; i < 45; i++) {
            x += 3;
            y += 2;
            draw2(x, y, c, 70);
        }
        for(; x > 30; x -= 2)
            draw2(x, y, c, 50);
        for(int i = 0; i < 55; i++) {
            x += 3;
            y += 2;
            draw2(x, y, c, 70);
        }
        for(; x > 155; x -= 2)
            draw2(x, y, c, 50);
        desk2x = 155;
        draw2(x, y, c, 70);
        for(; y > 30; y -= 2)
            draw2(x, y, c, 50);
        y = 30;
        draw2(x, y, c, 500);
        dequeue2();
        draw2(155, 30, ' ', 10);
    }
    // helper method to draw the service desk work
    final void draw2(int x, int y, char c, int pause) {
        desk2x = x;
        desk2y = y;
        desk2sym = c;
        win.repaint();
        try {
            Thread.sleep(pause);
        } catch(InterruptedException e) {
        }
    }
    
    public void desk3(Customer c) {
        enqueue3(c);
        process3(c);
    }
    
    // put the customer in the queue
    final void enqueue3(Customer c) {
        queue3 += c;
        win.repaint();
    }
    // peek at the first customer in the queue
    final Customer peek3() {
        return new Customer(queue3.charAt(0));
    }
    // remove the customer from the queue, when finished
    final void dequeue3() {
        queue3 = queue3.substring(1);
        desk3sym = ' ';
        win.repaint();
    }
    // show the work done at the service desk
    final void process3(Customer cust) {
        // start (275, 30);
        int x = 275;
        int y = 30;
        char c = cust.toString().charAt(0);
        draw3(x, y, c, 500);
        for(; y < 90; y += 5)
            draw3(x, y, c, 40);
        for(; x > 20; x -= 5)
            draw3(x, y, c, 40);
        for(; y < 140; y += 5)
            draw3(x, y, c, 40);
        for(; x < 350; x += 5)
            draw3(x, y, c, 40);
        for(; y < 180; y += 5)
            draw3(x, y, c, 40);
        for(; x > 20; x -= 5)
            draw3(x, y, c, 40);
        for(; y < 230; y += 5)
            draw3(x, y, c, 40);
        for(; x < 275; x += 5)
            draw3(x, y, c, 40);
        desk3x = 275;
        draw3(x, y, c, 40);
        for(; y > 100; y -= 5)
            draw3(x, y, c, 40);
        for(; y > 30; y -= 2)
            draw3(x, y, c, 40);
        desk3y = 30;
        draw3(x, y, c, 500);
        dequeue3();
        draw3(275, 30, ' ', 10);
    }
    // helper method to draw the service desk work
    final void draw3(int x, int y, char c, int pause) {
        desk3x = x;
        desk3y = y;
        desk3sym = c;
        win.repaint();
        try {
            Thread.sleep(pause);
        } catch(InterruptedException e) {
        }
    }

    /**
     * Override to display the service desks and their work
     * @param g The Graphics object that does the rendering
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Serving:", 25, 20);
        g.drawString("Serving:", 145, 20);
        g.drawString("Serving:", 265, 20);
        g.setFont(DISPLAY);
        g.drawString(queue1,  72, 20);
        g.drawString(queue2, 192, 20);
        g.drawString(queue3, 312, 20);
        drawSymbol(g, desk1x, desk1y, desk1sym, Color.green);
        drawSymbol(g, desk2x, desk2y, desk2sym, Color.yellow);
        drawSymbol(g, desk3x, desk3y, desk3sym, Color.cyan);
    }
    
    // helper method to draw the service desk worker
    void drawSymbol(Graphics g, int x, int y, char c, Color rgb) {
        g.setColor(rgb);
        g.fillRect(x, y, 22, 22);
        g.setColor(Color.black);
        g.drawRect(x, y, 22, 22);
        if(rgb == Color.green) {
            g.setColor(Color.magenta);
        } else if(rgb == Color.yellow) {
            g.setColor(Color.blue);
        } else if(rgb == Color.cyan) {
            g.setColor(Color.red);
        }
        switch(c) {
            case '$':
                g.drawString("" + c, x+7, y+18);
                break;
            case '*':
                g.drawString("" + c, x+9, y+20);
                break;
            case '@':
                g.drawString("" + c, x+2, y+16);
                break;
            case '%':
                g.drawString("" + c, x+2, y+18);
                break;
            default:
                g.drawString("" + c, x+5, y+18);
                break;
        }
    }
    
}


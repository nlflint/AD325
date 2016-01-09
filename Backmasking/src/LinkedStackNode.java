public class LinkedStackNode {
    
    public double value;
    
    public LinkedStackNode next;
    
    public LinkedStackNode(double v) {
        value = v;
        next = null;
    }
    
    public LinkedStackNode(double v, LinkedStackNode n) {
        value = v;
        next = n;
    }
    
}
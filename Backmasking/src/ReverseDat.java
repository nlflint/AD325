/**
 * This program reads a .dat file and reverses it.
 * @author Dan Jinguji
 * @version 4 January 2016
 */
public class ReverseDat {
    
    /**
     * The application method for the ReverseDat program
     * @param args The command-line arguments
     */
    public static void main(String[] args) {

        // checking a command-line arguments
        if(args.length != 3) {
            exitMsg("Usage: java ReverseDat <type> <input> <output>");
        }
        // create the stack for storage
        NumberStack stack;
        if(args[0].equals("array")) {
            stack = new ArrayStack();
        } else if(args[0].equals("linked")) {
            stack = new LinkedStack();
        } else {
            exitMsg("Type must be \"array\" or \"linked\"; saw " +
                    args[0] + " instead.");
            return;
        }
        // open the file for reading
        java.util.Scanner fileIn;
        try {
            fileIn = new java.util.Scanner(new java.io.File(args[1]));
        } catch(java.io.FileNotFoundException e) {
            exitMsg("Error opening input file: " + args[1]);
            return;
        }
        // open the file for writing
        java.io.PrintWriter fileOut;
        try {
            fileOut = new java.io.PrintWriter(new java.io.FileWriter(args[2]));
        } catch(java.io.IOException e) {
            exitMsg("Error opening output file: " + args[2]);
            return;
        }
        
        // read and store the data from the input file
        int sampleRate = readInputFile(fileIn, stack);
        if(sampleRate == 0) {
            exitMsg("Error reading input file: " + args[1]);
        }
        // close the input file
        fileIn.close();
        
        // write out the reverse data to the output file
        writeOutputFile(fileOut, stack, sampleRate);
        // close the input file
        fileOut.close();
        
    }
    
    // read displacement values from the dat file access by fileIn and store those 
    // values in the stack
    private static int readInputFile(java.util.Scanner fileIn, NumberStack stack) {
        
        // save the sampling rate
        // throw away the semicolon
        fileIn.next();
        // throw away Sample
        fileIn.next();
        // throw away Rate
        fileIn.next();
        // save the numerical sampling rate
        int rate = 0;
        if(fileIn.hasNextInt()) {
            rate = fileIn.nextInt();
        } else {
            exitMsg("Error Sampling Rate line");
        }
        // throw away the line terminator
        fileIn.nextLine();
        
        // throw away the channels line
        fileIn.nextLine();

        // read and store the rest of the data
        while(fileIn.hasNext()) {
            // throw away the time
            fileIn.next();
            // save the displacement
            if(fileIn.hasNextDouble()) {
                stack.push(fileIn.nextDouble());
            } else {
                exitMsg("File format error");
            }
        }

        // return the sampling rate
        return rate;
    }
    
    // write the reversed dat file into fileOut, using the sample rate
    // the displacement values are taken from the stack
    private static void writeOutputFile(java.io.PrintWriter fileOut, 
                                        NumberStack stack, 
                                        int rate) {
        // write out the structural data
        fileOut.printf("; Sample Rate %d\n", rate);
        fileOut.println("; Channels 1");
        
        // the data stored in the stack
        // keep count to calculate the time
        int counter = 0;
        double sampleRate = rate;
        while( ! stack.isEmpty()) {
            fileOut.printf("%12.8f %10.6f\n", 
                           counter / sampleRate, stack.pop());
            counter ++;
        }
        
    }
    
    // a helper method for error handling
    private static void exitMsg(String msg) {
        // print out the error message
        System.err.println(msg);
        // exit the VM
        System.exit(1);
    }
        
}

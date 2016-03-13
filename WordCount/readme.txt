Nathan Flint
AD325
3/13/2016
Assignment 7: Word Count

1) How did you go about starting this project?
I approached this assignment by reading all the requirements, and then deciding to implement the three versions from Minimum, to Check, the Challenge. The levels didn't have much overlap, and naturally built on each other.

2) What works and what doesn’t?
All the requirements have been met. I unit tested as much as I could, but the static method made if difficult to test the interactions between it and the main method. To test that, I just ran the program from the command line. Everything should be working as far as I can tell.

3) What are the test cases you created for this assignment?
I broke up the program into three sections: One section interprets the command line arguments into a structured format, the next section counts the tokens and returns a map with the counts, and the last section takes the map and builds a report which returns a collection of strings to be printed to the screen or saved to a file. I wrote tests for each of these sections. To test the glue that connects them all, I tested the program from the command line.

4) Did these test cases help you verify the correctness of your results?
Absolutely. It help me find regressions fast. There were a few cases were I tried to make a simple change, but broke a bunch of existing behavior. I immediately know I broke the behavior when running the tests to verify my change. The tests also made me think about the behavior before I even wrote a line of code. I had to consider the inputs and outputs which forces me to really think about the problem.

5) Surprises or problems you encountered while implementing this application
There were no surprises, but I did consider the given static method a challenge to workaround. I really wanted to refactor this method, but formulated a way to work around it and still test the core behavior of the program.

6) The most important thing(s) you learned from this assignment?
I learned that sometimes I may want to change code to make it more testable, but that sometimes that is not an option, and there are still way to unti test my code.

7) What you would do differently next time?
Nothing. I was very happy with my approach and the result.

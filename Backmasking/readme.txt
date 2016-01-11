Nathan Flint
Professor Jinguji
AD 325
10 January 2016
Backmasking

1. How did you test your implementations of NumberStack?
I used a test-first approach to develop the stack implementations, so I created tests as I developed. I completed tests for ArrayStack first, and was then able to reuse all those tests for LinkedStack. I did that by refactoring the ArrayStack test class into an abstract NumberStackTests class with 1 abstract factory method that created a number stack used by each unit test. Then I inherited from the the test class, once for ArrayStack tests, and again for LinkedStack tests, and implemented the abstract method to return the correct Stack ADP.

2. Use sox and ReverseDat to reverse the three sound clips, movie1, movie2, and movie3.
Movie1 - Pay no attention to that man behind the curtain.
Movie2 - Help me Obi-Wan Kenobi, you're  my only hope.
Movie3 - I'm sorry dave. I'm afraid I can't do that.

3. What did you enjoy the most about this assignment?
I enjoyed the test-first approach. Writing a single test and then making it pass gives a little feeling of accomplishment with each passing test.

4. What was the most challenging aspect of this assignment for you?
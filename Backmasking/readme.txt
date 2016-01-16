Nathan Flint
Professor Jinguji
AD 325
10 January 2016
Backmasking

1. How did you test your implementations of NumberStack?
I used a test-first approach to develop the stack implementations, so I created tests as I developed. I have several tests for each function, including toString, that verify the code behaves correctly. I completed tests for ArrayStack first, and was then able to reuse all those tests for LinkedStack. I did that by refactoring the ArrayStack test class into an abstract NumberStackTests class with 1 abstract factory method that created a number stack used by each unit test. Then I inherited from the the test class, once for ArrayStack tests, and again for LinkedStack tests, and implemented the abstract method to return the correct Stack ADT.

I also tested my code by converting the movie wav clips and making sure the audio was intelligible. 

2. Use sox and ReverseDat to reverse the three sound clips, movie1, movie2, and movie3.
Movie1 - Pay no attention to that man behind the curtain.
Movie2 - Help me Obi-Wan Kenobi, you're  my only hope.
Movie3 - I'm sorry dave. I'm afraid I can't do that.

3. What did you enjoy the most about this assignment?
I enjoyed the test-first approach. Writing a single test and then making it pass gives a little feeling of accomplishment with each passing test.

4. What was the most challenging aspect of this assignment for you?
The toString method the most challenging. 

For ArrayStack, I used the new stream ADT in Java8 to write the ArrayStack toString method, and that took a bit of reading oracle's website to figure out how to use.

For LinkedStack, I found it a little more difficult because I had to use fence posting to format the string correctly. Here, unit tests were helpful because I was making a lot of changes and they kept me from regressing progress as I tried a few ideas.

5. Given that the initial size of the underlying array in ArrayStack is 10 elements, how many time would the array need to grow to accommodate a .dat file with one million lines? one billion? one trillion? Explain your answer.

My implementation grows 2x at each resize, so here is a table showing capacity after a given number of resizes:

Growths - Resulting Capacity:
0 		- 10
1 		- 20
2 		- 40
3 		- 80
...

I figured out the following formula get capacity given number of growths: Y = (10 * 2^X). But I need to reverse this equation which gives: X = log base 2 (Y/10). I can use this equation to answer each question by rounding the answer up to the next integer:
One million - 17 growths
One billion - 27 growths
One trillion - 37 growths

6. Generally, all the methods of an interface must be overridden in a class that implements that interface. Explain why the toString method could be completely ignored in the minus version implementations of NumberStack.

It can be ignored, because the toString method is inherited from "object", the base class of all object. My stack classes get the method with default behavior from the base "object" class.

7. Assume that you also implement the NumberQueue ADT. Can you implement this ADT using both the partially-filled array and linked list data structures? Give pseudo-code for the implementation of the four core methods. Omit toString.

Yes.

LinkedVersion:
Add
	If isEmpty
		MakeValueHeadAndTail
	else
		AddItemToHead
		MakeNewValueTheHead
	
Remove
	if isEmpty throw exception
	SaveTailValue
	UpdateTailReference
	ReturnSavedTailValue

Peek
	ReturnTailValue

IsEmpty
	Return tail == null


8. How could the NumberQueue ADT change the implementation of ReverseDat?
If the stack was simply replaced with a queue, then the data would not be reversed in the current implementation. ReverseDat would need some other mechanism to reverse the data, and ReverseDat would need this functionality added to it. That is how it would need to be changed.

9. Compare your implementations of NumberStack and NumberQueue in regard to efficiency? Which are more effecient? Which are less? Explain your answer.

I chose to implement the linked version of the queue. I think it is just as efficient as the LinkedStack. It has an extra step because it keeps track of a head and a tail, but, in Big-O notation that kind of extra step is inconsequential.

10. How would you redesign this homework assignment, if you had the chance?

I'd like to compare the performance of the two stacks I implemented (linked vs array). From what I understand, there are different kinds of performance, so it's not always so easy to just say another algorithm is more efficient. It might be more efficient in time, but less in memory. It would be interested to compare and contrast the different kinds of performance between two algorithms that I implemented.






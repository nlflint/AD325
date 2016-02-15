Nathan Flint
AD325
14 February 2016

Assignmnet 4: Heaps

How did you go about approaching this assignment.
I approached this assignment in stanges from Minus requirements, all the way to Plus Extra. I completely implemented each step before moving to the next (except comments), and at each step I implemented the functionality one step at a time.

What works? What doesn't work?
Everything should work. All my tests are passing and I think I am testing all the requirements, and more.

How did you test your implementation? Include testing results.
I used JUnit for all my tests. I created each test before implementing any new behavior. For the minus version, I took a nieve approach to implementation to see where the process would take me. My first implementation was a sorted array, and not a heap. After my tests were all passing for the nieve approach, I refactored the code to use a Heap. I didn't actually create tests for the heap, by relied on the tests being written purely from the interface perspective. The tests do not care about the underlying implementation.

Results: all tests pass. See JUnit test method names in Jar file for test setup and expected results.

What problems did you encounter as you worked on this assignment?
Two things took were a bit challenging. First, I introduced a bug (that my unit test found immediately) when converting from a 2-ary to 4-ary heap. It took me awhile to find the bug because I had to debug the heao mutations in minute detail. 

Second, I had a hard time figuring out how to do the generic comparator. I had to do a little reading and experimenting with the interface to figure out what I needed to do.  

What did you learn from this work? How will you approach the next programming assignment differently?
I think the biggest lesson I learned is that Test First does not work well to arrive at a prescribed implementation, i.e. using a heap to implement a priority queue. As I wrote the tests, I arrived at a simple sorted array implementation. It does work well to arrive at a simple solution, and does a good job of verify required black box behavior. The difference in behavior between these implementations (heap vs sorted list) is only in performance, and I did not want to go down the path of steering the implementation by creating performance unit tests.

[Check version] Comparison of the binary to 4-ary tree for the heap structure.
I ran performance tests on the 2-ary and 4-ary queues, and my results indicate that there is not discernable difference in adding items to the queue. However, there is a significant difference in removing items from the queue.

Test Types: I used two unit tests to do the testing: 1) Add All only, and 2) Add All then Remove All. I manually changed the nAry constant between runs.

Test Data: I created arrays of 30,000,000 elements, randomly shuffled each time. I did additional tests to characterize how the construction of the random data was affecting test time, and it was only taking ~1 second so I disregarded it. Test times include construction of the test data.

Each test was run > 5 times to verify standard deviation was relatively low.
Add Only (not significantly different):
2-ary = ~10s
4-ary = ~11s

Add and Remove:
2-ary = ~51s
4-ary = ~35s

It makes no sense to combine the boolean and Comparator parameters into a single constructor. Why?
Both parameters are doing the same thing. They are defining if the heap will be min or max. They are both not needed because each parameter, alone, is enough to define the heap as min or max. Additionally, having both parameters opens up potential problems like the settings contradicting each other.
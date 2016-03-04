Nathan Flint
AD325
March 3, 2016

Assignment 6: Threading

1) How did you go about approaching this assignment.
I apprached this assignment from top to bottom. I completed the minus version first, then then check version, and lastly the plus version. At each step, I made changes I thought would work, and then just ran the program and compared the results to the videos, and corrected issues as they cropped up.
 
2) What works? What doesn't work?
Everything should work.

3) How did you test your implementation? Include testing results.
I tested this by running the program and comparing to the provided videos. I ran the video side by side as the program ran and followed each customer to make sure they moved exactly like the video. All testing passes.

4) What problems did you encounter as you worked on this assignment?
The most difficult problem was getting the Plus version working. Since only a single instance of customer could appear at a time, I had to think of a way to make the customer appear on the next desk only AFTER if finished process the previous desk. This led me to a path where I needed some kind of event to trigger everytime a customer finishes a desk, and that event handler would be responsible for queueing the next desk. I didn't actually use eventing, but just put the functionality in a function at the end of a thread. Then that functionality could trigger another thread on a different desk if required. 

5) What did you learn from this work? How will you approach the next programming assignment differently?
I have experience with threading, I didn't learn anything know about it specifically. However, there was a problem that I learned a lot from. I had three desk functions (desk1, desk2, and desk3) that were almost identical, and wanted to refactor them into a single function. I decided to learn about functional interfaces so I could pass the functions as variables in a class. I've used functions like this in other languages like Lisp and C#, but this ability was introduced in Java8 and I have never learned it before. I had a really fun time reading the documentation and then using what I learned.

Approaching the assignment from minus, to check, to plus worked really well. I wouldn't change anything about how I approached this.
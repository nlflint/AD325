Nathan Flint
Professor Jinguji
AD 325
21 January 2016

Assignment 2: Binary Search Trees

1. How did you go about approaching this assignment.
I used a test first approach to this assignment. I looked at the interface, and thought about how the methods should work together. Then I wrote one test at a time, and implented just enough functionality to make the test pass, before creating another test. This got harder to do with the remove method. I could not just think about the interface, but had to think about how remove() was implemented. I had to consider the difference cases, like removes with left, right, or both children.

2. What works? What doesn't work?
As far as I can tell, everthing works. I created 40+ tests to thoroughly test all the functionality. 

3. How did you test your implementation? Include testing results.
I used junit to write unit tests, and Intellij to run the tests. Here are all the unit test names and their results.

Result - Test Name
---------------------------------------------------------
BSTSet Tests:
PASS - add_AddingNullString_ThrowsException
PASS - add_WhenAddingStringASecondTime_ThenStringIsNotAdded
PASS - add_WhenAddingStringToNewSet_ThenStringIsAdded
PASS - add_WhenAddingTwoDifferentString_ThenStringsAreAdded
PASS - clear_WhenClearingAnEmptySet_ThenSetIsEmpty
PASS - clear_WhenClearingSetThatContainsItems_ThenSetDoesNotContainItems
PASS - contains_DoesNotRelyOnReferenceEquality
PASS - contains_WhenCallingContainsWithNullString_ThenExceptionIsThrown
PASS - contains_WhenManyStringsAdded_ThenSetContainsAllTheStrings
PASS - contains_WhenRemovingANullString_ThenExceptionIsThrown
PASS - contains_WhenStringAdded_ThenSetContainsString
PASS - contains_WhenTwoStringsAdded_ThenSetContainsBothString
PASS - isEmpty_GivenNewSet_ThenSetIsEmpty
PASS - isEmpty_WhenRemovingLastItem_ThenSetIsEmpty
PASS - isEmpty_WhenSetContainsAnItem_ThenSetIsNotEmpty
PASS - remove_RemovingAnItemThatDoesNotExist_ReturnsFalse
PASS - remove_WhenRemovingItem_ThenRemoveDoesNotDependOnReferenceEquality
PASS - remove_WhenRemovingItemThatCausesCascadingRemove_ThenOnlyOneItemIsRemoved
PASS - remove_WhenRemovingItemWithOnlyLeftChild_ThenItemIsRemovedAndSetContainsChild
PASS - remove_WhenRemovingItemWithOnlyRightChild_ThenItemIsRemovedAndSetContainsChild
PASS - remove_WhenRemovingItemWithTwoChildren_ThenItemIsRemovedAndSetContainsChildren
PASS - remove_WhenRemovingLeaf_ThenSetDoesNotContainLeaf
PASS - remove_WhenRemovingRandomItemFromHugeSet_ThenOnlyRandomItemIsRemoved
PASS - remove_WhenRemovingRootNodeWithOneChild_ThenOnlyRootNodeIsRemoved
PASS - remove_WhenRemovingRootNodeWithTwoChildren_ThenOnlyRootNodeIsRemoved
PASS - size_WhenClearingSet_ThenSizeIsZero
PASS - size_WhenRemovingAnItem_ThenSizeIsDecremented
PASS - size_WhenSetContains100Items_ThenSizeIs100
PASS - toStringInOrder_WhenGettingInOrderString_ThenItemsAreInAlphabeticalOrder
PASS - toStringInOrder_WhenSetIsEmpty_ThenEmptyStringReturned
PASS - toStringPostOrder_WhenGettingPostOrderString_ThenItemsAreInPostOrder
PASS - toStringPostOrder_WhenSetIsEmpty_ThenEmptyStringReturned
PASS - toStringPreOrder_WhenGettingPreOrderString_ThenItemsAreInPreOrder
PASS - toStringPreOrder_WhenSetIsEmpty_ThenEmptyStringReturned

In-Order Iterator Tests
PASS - callingNextWhenIteratorHasNoItems_ThrowsIllegalStateException
PASS - whenTraversingIterator_ThenItemsAreInProperOrder

Post-Order Iterator Tests
PASS - callingNextWhenIteratorHasNoItems_ThrowsIllegalStateException
PASS - whenTraversingIterator_ThenItemsAreInProperOrder

Pre-Order Iterator Tests:
PASS - callingNextWhenIteratorHasNoItems_ThrowsIllegalStateException
PASS - whenTraversingIterator_ThenItemsAreInProperOrder

4. What problems did you encounter as you worked on this assignment?
The most difficult part of the assignment was implmenting the Remove() method. The iterators were also quite difficult to implement. 

The Remove method has a lot of steps and I had a hard time keeping the whole process in my head at once. I got some cases working, and then got to more complicated cases, and was forced to rethink how I was doing it. Having the previous tests were a very helpful because they let me know if I regressed behavior while trying to implement the more advances cases. When I re-ran previous tests, I could see immediately that I broken something while my mind was still thinking about that functionality. I actually took a break for a day because the Remove() method was taking draining my mental capacity for the day.

The iterators were challenging because I had to devise a way to keep state of the current position and were I had been in the tree. Using stacks worked really well but it was complicated to come up with a stacking strategy. I used a lot of pencil drawings to very my strategy would work and it took a while to come up with a solution.

5. What did you learn from this work? How will you approach the next programming assignment differently?

I learned a lot about binary search trees, and how sets actually work. I've been using the test first approach for previous assignments and I will be continuing to use it going forward. I would like to say what I learned from the last assignment: read the instructions carefully. I lost a lot of points because I just did read the assignment instructions with a fine toothed comb. This time I did and found a few errors. I will be doing that going forward.
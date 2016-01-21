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
I used junit to write unit tests for all behaviors. All tests are passing:

Result - Test Name
---------------------------------------------------------
PASS - BSTSetTests.add_AddingNullString_ThrowsException
PASS - BSTSetTests.add_WhenAddingStringASecondTime_ThenStringIsNotAdded
PASS - BSTSetTests.add_WhenAddingStringToNewSet_ThenStringIsAdded
PASS - BSTSetTests.add_WhenAddingTwoDifferentString_ThenStringsAreAdded
PASS - BSTSetTests.clear_WhenClearingAnEmptySet_ThenSetIsEmpty
PASS - BSTSetTests.clear_WhenClearingSetThatContainsItems_ThenSetDoesNotContainItems
PASS - BSTSetTests.contains_DoesNotRelyOnReferenceEquality
PASS - BSTSetTests.contains_WhenCallingContainsWithNullString_ThenExceptionIsThrown
PASS - BSTSetTests.contains_WhenManyStringsAdded_ThenSetContainsAllTheStrings
PASS - BSTSetTests.contains_WhenStringAdded_ThenSetContainsString
PASS - BSTSetTests.contains_WhenTwoStringsAdded_ThenSetContainsBothString
PASS - BSTSetTests.isEmpty_GivenNewSet_ThenSetIsEmpty
PASS - BSTSetTests.isEmpty_WhenRemovingLastItem_ThenSetIsEmpty
PASS - BSTSetTests.isEmpty_WhenSetContainsAnItem_ThenSetIsNotEmpty
PASS - BSTSetTests.remove_RemovingAnItemThatDoesNotExist_ReturnsFalse
PASS - BSTSetTests.remove_WhenRemovingItem_ThenRemoveDoesNotDependOnReferenceEquality
PASS - BSTSetTests.remove_WhenRemovingItemThatCausesCascadingRemove_ThenOnlyOneItemIsRemoved
PASS - BSTSetTests.remove_WhenRemovingItemWithOnlyLeftChild_ThenItemIsRemovedAndSetContainsChild
PASS - BSTSetTests.remove_WhenRemovingItemWithOnlyRightChild_ThenItemIsRemovedAndSetContainsChild
PASS - BSTSetTests.remove_WhenRemovingItemWithTwoChildren_ThenItemIsRemovedAndSetContainsChildren
PASS - BSTSetTests.remove_WhenRemovingLeaf_ThenSetDoesNotContainLeaf
PASS - BSTSetTests.remove_WhenRemovingRandomItemFromHugeSet_ThenOnlyRandomItemIsRemoved
PASS - BSTSetTests.remove_WhenRemovingRootNodeWithOneChild_ThenOnlyRootNodeIsRemoved
PASS - BSTSetTests.remove_WhenRemovingRootNodeWithTwoChildren_ThenOnlyRootNodeIsRemoved
PASS - BSTSetTests.size_WhenClearingSet_ThenSizeIsZero
PASS - BSTSetTests.size_WhenRemovingAnItem_ThenSizeIsDecremented
PASS - BSTSetTests.size_WhenSetContains100Items_ThenSizeIs100
PASS - BSTSetTests.toStringInOrder_WhenGettingInOrderString_ThenItemsAreInAlphabeticalOrder
PASS - BSTSetTests.toStringInOrder_WhenSetIsEmpty_ThenEmptyStringReturned
PASS - BSTSetTests.toStringPostOrder_WhenGettingPostOrderString_ThenItemsAreInPostOrder
PASS - BSTSetTests.toStringPostOrder_WhenSetIsEmpty_ThenEmptyStringReturned
PASS - BSTSetTests.toStringPreOrder_WhenGettingPreOrderString_ThenItemsAreInPreOrder
PASS - BSTSetTests.toStringPreOrder_WhenSetIsEmpty_ThenEmptyStringReturned
PASS - InOrderIteratorTests.callingNextWhenIteratorHasNoItems_ThrowsIllegalStateException
PASS - InOrderIteratorTests.whenRemovingAnItem_ThenItemIsRemovedFromSet
PASS - InOrderIteratorTests.whenTraversingIterator_ThenItemsAreInProperOrder
PASS - PostOrderIteratorTests.callingNextWhenIteratorHasNoItems_ThrowsIllegalStateException
PASS - PostOrderIteratorTests.whenRemovingAnItem_ThenItemIsRemovedFromSet
PASS - PostOrderIteratorTests.whenTraversingIterator_ThenItemsAreInProperOrder
PASS - PreOrderIteratorTests.callingNextWhenIteratorHasNoItems_ThrowsIllegalStateException
PASS - PreOrderIteratorTests.whenRemovingAnItem_ThenItemIsRemovedFromSet
PASS - PreOrderIteratorTests.whenTraversingIterator_ThenItemsAreInProperOrder

4. What problems did you encounter as you worked on this assignment?
The most difficult part of the assignment was implmenting the Remove() method. The iterators were also quite difficult to implement. 

The Remove method has a lot of steps and I had a hard time keeping the whole process in my head at once. I got some cases working, and then got to more complicated cases, and was forced to rethink how I was doing it. Having the previous tests were a very helpful because they let me know if I regressed behavior while trying to implement the more advances cases. When I re-ran previous tests, I could see immediately that I broken something while my mind was still thinking about that functionality. I actually took a break for a day because the Remove() method was taking draining my mental capacity for the day.

The iterators were challenging because I had to devise a way to keep state of the current position and were I had been in the tree. Using stacks worked really well but it was complicated to come up with a stacking strategy. I used a lot of pencil drawings to very my strategy would work and it took a while to come up with a solution.

5. What did you learn from this work? How will you approach the next programming assignment differently?

I learned a lot about binary search trees, and how sets actually work. I've been using the test first approach for previous assignments and I will be continuing to use it going forward. I would like to say what I learned from the last assignment: read the instructions carefully. I lost a lot of points because I just did read the assignment instructions with a fine toothed comb. This time I did and found a few errors. I will be doing that going forward.
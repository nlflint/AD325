Nathan Flint
AD325
2 February 2016

Assignmnet 3: AVLSet

How did you go about approaching this assignment?
I approached the assignment from minimum to plus. I work first on the minus version, then move to the check version, then move to the plus version. Lastly I come back through the whole assignment with a fine tooth comb, and make sure every requirement has been met. It's a multi-pass approach. I also work in a test first manner, so I write one test with expected behavior, then change the code to make the test pass, and then refactor the code to remove duplication. This process is repeated until I am satisfied I have implemented the assignment.

What works? What doesn't work?
I found it very difficult to implement the double rotations because I did not understand them. I attmepted to use a test first approach, but coming up with expected results was impossible without first knowing how the double rotations were supposed to work. This forced me to really learn what is going on, and after wrapping my head around them, I understand them very well now.

Again, unit tests really worked well. I love being able to refactor code without fear of missing something. I just have to re-run all my tests and I can tell immediatley if I broke anything.

How did you test your implementation? Include testing results.
I reused all my unit tests from BSTSet, and create a bunch of new ones for AVLSet. One difficulty was verifying the balance of the tree. There was no easy way to represent the structure of the tree, so I created a simple method using parenthesis. Then created a toString function that would create this string representation of the tree. The representation represents a node with three numbers surround by parenthesis like so (4 5 6). This represents a single node. The first number is the value of the node, the second number is the left node and the third number is the right node. Null nodes are an underscore. So a root node with no children is (4 _ _). To show a nested node, a node is put with in the first node. For example: (10 (5 1 _) (20 14 24)). In this string the root node is 10, on the left is node 5. Left of 5 is a 1. Right of 5 is null. Then right of the root is 20. Left of 20 is 14, and right of 20 is 24. I used this representation in my unit tests to verify the structure of the tree.

All Tests Pass
--------------
add_AddingNullString_ThrowsException
add_WhenAddingStringASecondTime_ThenStringIsNotAdded
add_WhenAddingStringToNewSet_ThenStringIsAdded
add_WhenAddingTwoDifferentString_ThenStringsAreAdded
clear_WhenClearingAnEmptySet_ThenSetIsEmpty
clear_WhenClearingSetThatContainsItems_ThenSetDoesNotContainItems
contains_DoesNotRelyOnReferenceEquality
contains_WhenCallingContainsWithNullString_ThenExceptionIsThrown
contains_WhenManyStringsAdded_ThenSetContainsAllTheStrings
contains_WhenStringAdded_ThenSetContainsString
contains_WhenTwoStringsAdded_ThenSetContainsBothString
hugeTreeIsBalancedAfterRandomlyAdding
hugeTreeIsBalancedAfterRandomlyRemoving
isEmpty_GivenNewSet_ThenSetIsEmpty
isEmpty_WhenRemovingLastItem_ThenSetIsEmpty
isEmpty_WhenSetContainsAnItem_ThenSetIsNotEmpty
rebalance_WhenAddingValueToTheLeftLeftChild_ThenOverWeightNodeIsRotatedRight
rebalance_WhenAddingValueToTheLeftRightChild_ThenOverWeightNodeIsRotateRightLeft
rebalance_WhenAddingValueToTheRightLeftChild_ThenOverWeightNodeIsRotateLeftRight
rebalance_WhenAddingValueToTheRightRightChild_ThenOverWeightNodeIsRotatedLeft
rebalance_WhenRemoveLeavesLeftLeftAndLeftRightChildrenEquallyWeighted_ThenOverWeightNodeIsRotatedLeft
rebalance_WhenRemoveLeavesLeftLeftOverWeight_ThenOverWeightNodeIsRotatedRight
rebalance_WhenRemoveLeavesLeftRightOverWeight_ThenOverWeightNodeIsRotatedLeftRight
rebalance_WhenRemoveLeavesRightLeftAndRighRightChildrenEquallyWeighted_ThenOverWeightNodeIsRotatedRight
rebalance_WhenRemoveLeavesRightLeftOverWeight_ThenOverWeightNodeIsRotatedRightLeft
rebalance_WhenRemoveReplaceLeavesImbalanceAtReplacingNode_ThenLocalImbalanceIsCorrected
rebalance_WhenRemovingLeavesRightRightOverWeight_ThenOverWeightNodeIsRotatedLeft
remove_RemovingAnItemThatDoesNotExist_ReturnsFalse
remove_WhenRemovingItem_ThenRemoveDoesNotDependOnReferenceEquality
remove_WhenRemovingItemThatCausesCascadingRemove_ThenOnlyOneItemIsRemoved
remove_WhenRemovingItemWithOnlyLeftChild_ThenItemIsRemovedAndSetContainsChild
remove_WhenRemovingItemWithOnlyRightChild_ThenItemIsRemovedAndSetContainsChild
remove_WhenRemovingItemWithTwoChildren_ThenItemIsRemovedAndSetContainsChildren
remove_WhenRemovingLeaf_ThenSetDoesNotContainLeaf
remove_WhenRemovingRandomItemFromHugeSet_ThenOnlyRandomItemIsRemoved
remove_WhenRemovingRootNodeWithOneChild_ThenOnlyRootNodeIsRemoved
remove_WhenRemovingRootNodeWithTwoChildren_ThenOnlyRootNodeIsRemoved
size_WhenClearingSet_ThenSizeIsZero
size_WhenRemovingAnItem_ThenSizeIsDecremented
size_WhenSetContains100Items_ThenSizeIs100
toStringInOrder_WhenGettingInOrderString_ThenItemsAreInAlphabeticalOrder
toStringInOrder_WhenSetIsEmpty_ThenEmptyStringReturned
toStringPostOrder_WhenGettingPostOrderString_ThenItemsAreInPostOrder
toStringPostOrder_WhenSetIsEmpty_ThenEmptyStringReturned
toStringPreOrder_WhenGettingPreOrderString_ThenItemsAreInPreOrder
toStringPreOrder_WhenSetIsEmpty_ThenEmptyStringReturned
TreeAsString
pn2rpn_GivenNestedNotation_ConvertsToReversePolishNotation
pn2rpn_GivenPolishExamplesFromAssignment_ThenReverseNotationFromExamplesIsGenerated
pn2rpn_GivenSimplePolishNotation_ConvertsToReversePolishNotation
rpn2pn_GivenNestedReverseNotation_ConvertsToPolishNotation
rpn2pn_GivenReversePolishExamplesFromAssignment_ThenPolishNotationFromExamplesIsGenerated
rpn2pn_GivenSimpleReversePolishNotation_ConvertsToPolishNotation

What problems did you encounter as you worked on this assignment?
The hardest problem was figuring out how the rotations actually worked, and then implementing the double rotations. It was difficult for me to keep track of the state properly and I had lots of issue making sure heights were kept up to date.


What did you learn from this work? How will you approach the next programming assignment differently?
I learned that when I haven't wrapped my head around a concept, like double rotations, that I really need to put in the time to understand it on pen and paper first, before digging into it. If this happens again, I will make sure I fully understand the concept before I start trying to write unit tests.

What are some ways to test the well-formedness of the input string for the Lukasiewicz methods?
The only idea I can come up with is to do the verification as the tree is being constructed. For example, if the recursion returns to the root node, and there are items left in the scanner, then there was a problem. Or, if the scanner runs out of items before returning to the root node, then there was a problem.


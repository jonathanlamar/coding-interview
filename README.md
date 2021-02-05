# coding-interview

Storing solutions to common coding interview problems

## Google Interview (March 2020)

How much can I learn in one month? I know I'm not going to get significantly
better at problem solving in one month (although I should practice), so I am
going to spend a portion of my time doing some targeted memorization. As they
come up:

### Searches

Binary search. This is very simple to implement. In words: You find an element
x in a list L of integers of length N by comparing L[N/2] to x. If x > L[N/2],
then x is in the back half of the list, else front half. Then recurse on the
back half of the list and add N/2 to the result. Runtime is O(log(N)). Space
is O(log(N)) unless you use a loop/accumulator tail recursion method, in which
case space is O(1).

### Sorting

MANY different sorting algorithms

**TODO:** Implement some classic ones

It is commonly stated that sorting is O(n\*log(n)). TOOD: Learn why this is.

### Hash tables

Always keep hash tables in mind, as they are useful in a wide variety of
problems. This is mainly due to their O(1) lookup time. So you can loop
through an array and hash the array elements in to some information that will be
useful. Very important to keep in mind: Because lookup is O(1), so is
checkExists and delete.

Seems like using hash tables to disambiguate can often use a Set instead. I
didn't think that was an actual data structure, but it is..?

### Graph search algorithms

These actually come up, although I didn't realize it at first. Study odd-even
jump problem!! This was way over my head. Solution 2 involved something called
a "TreeMap". This is getting away from the topic of graph search, but may be
worth looking up later.

Depth first search

Breadth first search

### Two Pointers Methods

#### Sliding window method

Common approach for problems of the form "find the optimal subarray which
satisfies a condition." Some examples:

1. Find the longest substring with no repeating characters.
2. Find the longest substring with no more than 2 distinct characters.
3. More complicated form of above: Find the longest substring with no more than
   k distinct characters. These all often use hashmaps to store information.

When finding the largest subarray, we usually start both ends at 0 and
increment the right endpoint first, then left. These questions also use other
optimizations, and this will affect how the sliding window traverses the array.
For "minimum" and related optimizations (i.e., those which favor shorter
solutions), we usually begin at opposite ends and shrink the subarray. For
example:

1. Find the largest container of water. No hash map here.
2. Find the shortest subarray whose sum is greater than k.

#### Tortoise and Hare

In certain situations, we can move one pointer twice as fast as the other. This
is a well known algorithm for finding cycles in a graph. (Floyd's Tortoise and
Hare). A basic application of this algorithm is the following problem.

1. Given an array A of length n whose elements are all positive integers between
   1 and n, write a function which determines if the array contains a repeated
   element. This function must compute in O(n) time and O(1) space.

## More general advice

Gleaned from solving or failing to solve problems. Also some advice I've read
in CTCI and Leetcode posts:

- Open the doc/coderpad 30 minutes before the interview and implement a couple
  easy algorithms to warm up: reverse a linked list, reverse a string in place,
  implement a merge sort, etc. You'd be surprised how rusty you are typing code
  without IDE help.

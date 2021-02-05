from typing import List

# Find the "pivot" of an array. This is the index where the sums of the numbers
# to the left and to the right are equal.  If no such index exists, return -1.
# If multiple, return leftmost.
def findPivot(arr: List[int]) -> int:
    n = len(arr)
    S = sum(arr) # O(n) time complexity
    acc = 0

    for i in range(n):
        if acc == S - acc - arr[i]:
            return i
        else:
            acc += arr[i]

    return -1

# Find whether the largest element in the array is at least twice as much as
# every other number.  If so, return its index, otherwise return -1
def dominantIndex(nums: List[int]) -> int:
    # Slow solution - O(n)
    largest = 0
    largestInd = 0

    for i, x in enumerate(nums):
        if x > largest:
            largest = x
            largestInd = i

    for i, x in enumerate(nums):
        if i == largestInd:
            continue

        if 2*x > largest:
            return -1

    return largestInd

# Given a non-empty array of digits representing a non-negative integer, plus
# one to the integer.  The digits are stored such that the most significant
# digit is at the head of the list, and each element in the array contain a
# single digit.  You may assume the integer does not contain any leading zero,
# except the number 0 itself.
def plusOne(digits: List[int]) -> List[int]:
    # O(n) - Slow?
    n = len(digits)
    carry = 1

    for i in range(len(digits)):
        digits[n-1-i] += carry
        carry = 0
        if digits[n-1-i] == 10:
            carry = 1
            digits[n-1-i] -= 10

    if carry == 0:
        return digits
    else:
        return [1] + digits # What is the cost of this operation?

# Given a matrix of M x N elements (M rows, N columns), return all elements of
# the matrix in diagonal order as shown in the below image.
# Input:
# [
#  [ 1, 2, 3 ],
#  [ 4, 5, 6 ],
#  [ 7, 8, 9 ]
# ]

# Output:  [1,2,4,7,5,3,6,8,9]
def findDiagonalOrder(matrix: List[List[int]]) -> List[int]:
    pass


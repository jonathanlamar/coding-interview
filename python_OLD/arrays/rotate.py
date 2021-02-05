# A left rotation operation on an array shifts each of the array's elements
# 1 unit to the left. For example, if 2 left rotations are performed on array
# [1, 2, 3, 4, 5], then the array would become [3, 4, 5, 1, 2].

# Given an array arr of n integers and a number, d, perform d left rotations on
# the array.

def brute_force(arr, d):
    # O(n) time and space complexity.  We can't improve the runtime, but it is
    # possible to do this in place with O(1) space.
    n = len(arr)
    newArr = []

    for i in range(n):
        ind = (i + d) % n
        newArr.append(arr[ind])

    return newArr


# This solution applies a cyclic rotation in place by holding one element in
# memory.
def optimal_solution(arr, d):
    n = len(arr)

    tmp = arr[0]
    i = 0
    iNext = d
    for _ in range(n):

        if iNext == 0:
            arr[i] = tmp
        else:
            arr[i] = arr[iNext]

        i = iNext
        iNext = (iNext + d) % n

    return arr

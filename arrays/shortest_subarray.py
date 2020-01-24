from IPython import embed

# Given an array, arr, of positive numbers, find the length of the shortest
# contiguous subarray whose sum is greater than or equal to n.  If no such
# subarray exists, return 0.
# For example, if arr = [1, 2, 3, 4, 1, 3] and S = 5, then f(arr, n) = 2.

# The easy solution is to loop over beginning and endpoints independently and
# test for the conditions.  Meanwhile we update a tracker variable only when
# we find a better solution.
def brute_force(arr, S):
    # My brute force solution.  This is pretty bad, and has time complexity
    # O(n^2), where n is the length of arr.

    hasSolution = False
    bestLen = len(arr)

    # This should handle edge cases automatically.
    for leftEndpoint in range(len(arr)):
        for rightEndpoint in range(leftEndpoint, len(arr)):
            tmpSum = sum(arr[leftEndpoint:rightEndpoint+1])

            if tmpSum >= S:
                hasSolution = True
                if rightEndpoint - leftEndpoint + 1 < bestLen:
                    bestLen = rightEndpoint - leftEndpoint + 1

    if hasSolution:
        return bestLen
    else:
        return 0

# The solution can be improved by noting that in the brute force solution, we
# start with short subarrays and increase length.  Also, if we are looking at a
# subarray whose sum is already greater than or equal to S, then there is no
# need to increase the right endpoint.  This hints at a better solution: that
# we can iterate the right endpoint until we find a subarray whose sum is >= S,
# then iterate the left endpoint from 0 until we fail to satisfy the sum
# property.  Keeping trak of the minimum length achieved, we then proceed in
# alternating steps, increasing each enpoint.  This will involve at worst 2*n
# steps, hence has runtim O(n).
def optimal_solution(arr, S):

    hasSolution = False
    bestLen = len(arr) # O(1)

    # Edge cases
    if len(arr) == 0:
        return 0
    elif len(arr) == 1:
        if arr[0] >= S:
            return 1
        else:
            return 0

    # This is a one-pass search, and we will keep a moving sum to cut down on
    # runtime.  Initially, leftEndpoint = 0 and rightEndpoint = 0
    movingSum = 0
    leftEndpoint = 0

    for rightEndpoint in range(len(arr)):
        movingSum += arr[rightEndpoint]

        if movingSum < S:
            # Do nothing until condition is met
            continue

        # Trigger that a solution exists.
        hasSolution = True

        # Once condition is met, update length
        conditionMet = True
        while conditionMet:
            tmpLen = rightEndpoint - leftEndpoint + 1

            if tmpLen < bestLen:
                bestLen = tmpLen

            # Update length, moving sum, and conditionMet
            movingSum -= arr[leftEndpoint]
            leftEndpoint += 1
            if movingSum < S:
                conditionMet = False

    if hasSolution:
        return bestLen
    else:
        return 0


# A very similar problem common to ask is: What is the length of the longest
# substring of a string with no repeated characters.  I model the solution
# after the above optimal solution.  The analogy is that strings are simply
# arrays, and we are merely finding the longest contiguous subarray subject
# to a condition.
def longest_nonrepeating_substring(S):

    def debug(debugStr):
        print('--------------------------------------------------------')
        print(debugStr)
        print('--------------------------------------------------------')
        print('rightEndpoint = %d\nleftEndpoint = %d\ncharCounts = %s\n'
              'bestLen = %d\nconditionMet = %s\ncurrent length = %d\n'
              % (rightEndpoint, leftEndpoint, str(charCounts), bestLen,
                 str(conditionMet), rightEndpoint-leftEndpoint+1))

    # Starting from 0 now because we're looking for the longest subarray.
    bestLen = 0

    # Edge cases
    if len(S) == 0:
        return 0
    elif len(S) == 1:
        return 1

    charCounts = {}
    conditionMet = True

    leftEndpoint = 0
    for rightEndpoint in range(len(S)):
        # Update hash map
        if S[rightEndpoint] not in charCounts or charCounts[S[rightEndpoint]] == 0:
            charCounts[S[rightEndpoint]] = 1
            if rightEndpoint - leftEndpoint + 1 > bestLen:
                bestLen = rightEndpoint - leftEndpoint + 1

            debug('Updating rightEndpoint')
            continue
        else:
            charCounts[S[rightEndpoint]] += 1
            conditionMet = False

        debug('Updating rightEndpoint')

        while not conditionMet:
            # Do stuff
            charCounts[S[leftEndpoint]] -= 1
            leftEndpoint += 1

            # This destroys O(n) runtime.
            # Actually no.  charCounts.values() has length <= total number of characters...
            # Still seems ugly
            if all([x <= 1 for x in charCounts.values()]):
                conditionMet = True

                # This should not be necessary, but it only adds O(1) runtime...
                if rightEndpoint - leftEndpoint + 1 > bestLen:
                    bestLen = rightEndpoint - leftEndpoint + 1

            debug('Updating leftEndpoint')

    return bestLen



if __name__ == '__main__':
    arr = [1, 2, 1, 5, 1, 3, 1, 1, 1]
    S = 9
    print(brute_force(arr, S))
    print(optimal_solution(arr, S))

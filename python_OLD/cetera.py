from typing import List

def twoSum(nums: List[int], target: int) -> List[List[int]]:
    r"""
    Given a list nums and a target value target, return the indices of two
    elements of nums which sum to target.
    """
    D = {x : None for x in nums}
    N = len(nums)

    solutionList = []

    for i in range(N):
        val = nums[i]

        if D[val] is not None:
            solutionList.append([val, D[val], -target])

        else:
            D[target - val] = val

    return solutionList


def reverseList(nums: List[int]) -> List[int]:
    r""" Reverse the elements of a list in place.  """

    N = len(nums)
    x = 0
    y = N - 1

    while x < y:
        z = nums[x]
        nums[x] = nums[y]
        nums[y] = z
        x += 1
        y -= 1

    return nums

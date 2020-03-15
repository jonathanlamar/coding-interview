from typing import List
from queue import Queue, LifoQueue

# My stuff
from binary_tree import BinaryTreeNode

def iterativeBinarySearch(sortedList: List[int], target: int) -> int:
    r""" Binary search of a list of integers for a target.  """
    N = len(sortedList)

    if N == 0:
        return -1

    L = 0
    R = N - 1

    while L < R:
        m = int((R+L)/2)
        x = sortedList[m]

        print('------Diagnostic-----')
        print('R = %d, L = %d, m = %d, x = %d' % (R, L, m, x))
        print('Searching the following list:')
        print(sortedList[L : R+1])

        if x < target:
            if L == m:
                print('Caught in loop.  Shortcutting.')
                break
            else:
                L = m
        elif x > target:
            if R == m:
                print('Caught in loop.  Shortcutting.')
                break
            else:
                R = m
        else:
            return m

    print('Checking endpoints.')
    if sortedList[R] == target:
        return R
    elif sortedList[L] == target:
        return L
    else:
        return -1


def recursiveBinarySearch(sortedList: List[int], target: int) -> int:
    r""" Binary search of a list of integers for a target.  """

    N = len(sortedList)

    if N == 0:
        return -1

    L = 0
    R = N - 1

    m = int((L + R) / 2)

    if sortedList[m] == target:
        return m
    elif sortedList[m] < target:
        return m + 1 + recursiveBinarySearch(sortedList[m+1:], target)
    else:
        return recursiveBinarySearch(sortedList[:m], target)


def depthFirstSearch(tree: BinaryTreeNode) -> List[int]:
    r"""
    Depth first search of a binary tree.

    Returns the list of nodes in the order traversed.
    """
    visitStack = []
    adjStack = LifoQueue()

    # NOTE: For more general DFS of a graph, we need to use a hash or
    # something to track visited nodes in O(1) time.  There is no risk of
    # collision in a tree though.

    adjStack.put(tree)
    while not adjStack.empty():
        subTree = adjStack.get()
        visitStack.append(subTree.root)
        if subTree.left is not None:
            adjStack.put(subTree.left)
        if subTree.right is not None:
            adjStack.put(subTree.right)

    return visitStack


def breadthFirstSearch(tree: BinaryTreeNode) -> List[int]:
    r"""
    Breadth first search of a binary tree.

    Returns the list of nodes in the order traversed.
    """
    visitStack = []
    adjStack = Queue()

    # NOTE: For more general BFS of a graph, we need to use a hash or
    # something to track visited nodes in O(1) time.  There is no risk of
    # collision in a tree though.

    adjStack.put(tree)
    while not adjStack.empty():
        subTree = adjStack.get()
        visitStack.append(subTree.root)
        if subTree.left is not None:
            adjStack.put(subTree.left)
        if subTree.right is not None:
            adjStack.put(subTree.right)

    return visitStack


def inOrderTraversal(tree: BinaryTreeNode, visitStack: List[int]) -> List[int]:
    r""" in-order traversal of a binary tree.  """

    if visitStack is None:
        visitStack = []

    if tree is None:
        return visitStack
    else:
        if tree.left is not None:
            visitStack = inOrderTraversal(tree.left, visitStack)

        visitStack.append(tree.root)

        if tree.right is not None:
            visitStack = inOrderTraversal(tree.right, visitStack)

        return visitStack

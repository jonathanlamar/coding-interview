from typing import List

class BinaryTreeNode:
    def __init__(self, root: int):
        self.root = root
        self.left = None
        self.right = None

    def insertInOrder(self, num: int):
        if num < self.root:
            if self.left is None:
                self.left = BinaryTreeNode(num)
            else:
                self.left.insertInOrder(num)
        else:
            if self.right is None:
                self.right = BinaryTreeNode(num)
            else:
                self.right.insertInOrder(num)

    def __str__(self):
        if self.left is None:
            S = ''
        else:
            S = str(self.left) + ', '

        if self.right is None:
            T = ''
        else:
            T = ', ' + str(self.right)

        return S + str(self.root) + T


def insertInOrder(tree: BinaryTreeNode, num: int) -> BinaryTreeNode:
    r"""
    Inserts a node into a binary tree "in-order," i.e., goes left if less than
    head, goes right if greater than head until it gets to a leaf node.  If the
    algorithm encounters a head equal to num, then it will default right.
    """
    if tree is None:
        tree = BinaryTreeNode(num)
    elif num < tree.root:
        tree.left = insertInOrder(num, tree.left)
    else:
        tree.right = insertInOrder(num, tree.right)

    return tree


def buildBinaryTreeFromList(nums: List[int]) -> BinaryTreeNode:
    r"""
    Builds a binary tree from a list of numbers.  The elements will be sorted
    in-order, i.e., printing with an in-order traversal will print a sorted list
    of numbers.
    """

    tree = None

    for x in nums:
        if tree is None:
            tree = BinaryTreeNode(x)
        else:
            tree.insertInOrder(x)

    return tree

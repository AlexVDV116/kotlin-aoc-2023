// Recursive data type
class Node(var key: String, var left: Node? = null, var right: Node? = null) {

    /** Find a node with a given value, if no node is found, return null
     * If the value is smaller than the current node, search the left subtree
     * If the value is larger than the current node, search the right subtree
     * If the value is equal to the current node, return the current node
     * @param value
     */
    private fun find(value: String): Node? = when {
        this.key > value -> left?.find(value)
        this.key < value -> right?.find(value)
        else -> this
    }

    /** Insert a node with a given value into the tree at the correct position (left or right)
     * If the value is smaller than the current node, insert it into the left subtree
     * If the value is larger than the current node, insert it into the right subtree
     * If the value is equal to the current node, do nothing
     * If the left or right subtree is null, create a new node with the given value and insert it into the tree
     * If the left or right subtree is not null, call the insert function on the subtree
     * @param value
     */
    private fun insert(value: String) {
        if (this.key > value) {
            if (left == null) {
                left = Node(value)
            } else {
                left?.insert(value)
            }
        } else {
            if (right == null) {
                right = Node(value)
            } else {
                right?.insert(value)
            }
        }
    }

    /**
     * Delete the value from the given tree. If the tree does not contain the value, the tree remains unchanged.
     * @param value
     */
    fun delete(value: String) {
        when {
            value > key -> scan(value, this.right, this)
            value < key -> scan(value, this.left, this)
            else -> removeNode(this, null)
        }
    }

    /** Remove the given node from the tree
     * If the node has no children, remove it from the tree
     * If the node has one child, replace it with its child
     * If the node has two children, replace it with its successor
     * @param node
     */
    private fun scan(value: String, node: Node?, parent: Node?) {
        if (node == null) return
        when {
            value > node.key -> scan(value, node.right, node)
            value < node.key -> scan(value, node.left, node)
            else -> removeNode(node, parent)
        }
    }

    /**
     * Remove the node.
     *
     * Removal process depends on how many children the node has.
     *
     * @param node node that is to be removed
     * @param parent parent of the node to be removed
     */
    private fun removeNode(node: Node, parent: Node?) {
        node.left?.let { leftChild ->
            run {
                node.right?.let {
                    removeTwoChildNode(node)
                } ?: removeSingleChildNode(node, leftChild)
            }
        } ?: run {
            node.right?.let { rightChild -> removeSingleChildNode(node, rightChild) } ?: removeNoChildNode(node, parent)
        }


    }

    /**
     * Remove the node without children.
     * @param node
     * @param parent
     */
    private fun removeNoChildNode(node: Node, parent: Node?) {
        parent?.let { p ->
            if (node == p.left) {
                p.left = null
            } else if (node == p.right) {
                p.right = null
            }
        } ?: throw IllegalStateException(
            "Can not remove the root node without child nodes")

    }

    /**
     * Remove a node that has two children.
     *
     * The process of elimination is to find the biggest key in the left subtree and replace the key of the
     * node that is to be deleted with that key.
     */
    private fun removeTwoChildNode(node: Node) {
        val leftChild = node.left!!
        leftChild.right?.let {
            val maxParent = findParentOfMaxChild(leftChild)
            maxParent.right?.let {
                node.key = it.key
                maxParent.right = null
            } ?: throw IllegalStateException("Node with max child must have the right child!")

        } ?: run {
            node.key = leftChild.key
            node.left = leftChild.left
        }

    }

    /**
     * Return a node whose right child contains the biggest value in the given sub-tree.
     * Assume that the node n has a non-null right child.
     *
     * @param n
     */
    private fun findParentOfMaxChild(n: Node): Node {
        return n.right?.let { r -> r.right?.let { findParentOfMaxChild(r) } ?: n }
            ?: throw IllegalArgumentException("Right child must be non-null")

    }

    /**
     * Remove a parent that has only one child.
     * Removal is effectively is just coping the data from the child parent to the parent parent.
     * @param parent Node to be deleted. Assume that it has just one child
     * @param child Assume it is a child of the parent
     */
    private fun removeSingleChildNode(parent: Node, child: Node) {
        parent.key = child.key
        parent.left = child.left
        parent.right = child.right
    }

    fun visit(): Array<String> {
        val a = left?.visit() ?: emptyArray()
        val b = right?.visit() ?: emptyArray()
        return a + arrayOf(key) + b
    }
}
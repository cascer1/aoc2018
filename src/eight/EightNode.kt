package eight


class EightNode {
    var children = ArrayList<EightNode>()
    var metadata = ArrayList<Int>()

    fun addChild(child: EightNode) {
        if (!children.contains(child)) {
            children.add(child)
        }
    }

    fun addMetaData(metadata: Int) {
        this.metadata.add(metadata)
    }


    /*
    The value of a node depends on whether it has child nodes.

    If a node has no child nodes, its value is the sum of its metadata entries.
    So, the value of node B is 10+11+12=33, and the value of node D is 99.

    However, if a node does have child nodes, the metadata entries become indexes
    which refer to those child nodes. A metadata entry of 1 refers to the first child
    node, 2 to the second, 3 to the third, and so on. The value of this node is the
    sum of the values of the child nodes referenced by the metadata entries. If a
    referenced child node does not exist, that reference is skipped. A child node
    can be referenced multiple time and counts each time it is referenced. A metadata
    entry of 0 does not refer to any child node.
     */

    fun getValue(): Int {
        // If node has no children, value is sum of meta data
        if (children.size == 0) {
            return getMetaSum()
        }

        if (metadata.size == 0) {
            return 0
        }

        // Otherwise, value is sum of values of children referenced by meta data

        var value = 0
        metadata.forEach metaloop@{
            if (it > children.size || it == 0) {
                return@metaloop
            } else {
                value += children[it - 1].getValue()
            }
        }

        return value
    }

    fun getMetaSum(): Int {
        var returned = 0

        metadata.forEach {
            returned += it
        }

        return returned
    }
}
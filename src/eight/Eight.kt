package eight

import utils.FileUtils


object Eight {

    private val numbers = ArrayList<Int>()
    private var rootNode = EightNode()
    private val nodesWithoutTree = ArrayList<EightNode>()
    private var index = 0

    @JvmStatic
    fun main(ags: Array<String>) {
        println("Answer one: \t\t ${getAnswerOne()}")
        println()
        println("Answer two: \t\t ${getAnswerTwo()}")

    }


    private fun parseFile() {
        val input = FileUtils.loadFileAsString("eight.txt")

        numbers.clear()
        index = 0

        input.split(" ").forEach { number ->
            numbers.add(number.toInt())
        }
    }

    private fun buildNodes() {
        // NODE FORMAT
        // childCount metaCount [childs] [metas]
        rootNode = buildNode()
    }

    private fun buildNode(): EightNode {
        val childCount = numbers[index++]
        val metaCount = numbers[index++]

        val node = EightNode()

        if (childCount > 0) {
            for (j in 0 until childCount) {
                node.addChild(buildNode())
            }
        }

        if (metaCount > 0) {
            for (j in 0 until metaCount) {
                node.addMetaData(numbers[index++])
            }
        }

        nodesWithoutTree.add(node)
        return node
    }

    // What is the sum of all metadata entries?
    private fun getAnswerOne(): Int {
        parseFile()
        buildNodes()

        var metaTotal = 0

        nodesWithoutTree.filter { it.metadata.size > 0 }.forEach { node ->
            metaTotal += node.getMetaSum()
        }

        return metaTotal
    }

    // What is the value of the root node?
    private fun getAnswerTwo(): Int {
        parseFile()
        buildNodes()


        return rootNode.getValue()
    }
}
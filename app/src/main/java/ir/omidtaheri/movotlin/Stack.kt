package ir.omidtaheri.movotlin

class Stack<T>(private var root: StackNode<T>? = null) {

    var size: Int = 0

    class StackNode<T>(val data: T) {
        var next: StackNode<T>? = null
    }

    fun isEmpty(): Boolean {
        return root == null
    }

    fun push(data: T) {
        size++
        val newNode = StackNode(data)

        if (root == null) {
            root = newNode
        } else {
            val temp = root
            root = newNode;
            newNode.next = temp
        }
    }

    fun pop(): T? {
        var item = root?.let {
            size--
            val currentData = root!!.data
            root = root!!.next
            currentData
        } ?: null
        return item
    }

    fun peek(): T? {
        return root?.let {
            it.data
        } ?: null
    }
}
package seamcarving.datastructure

import java.util.*
import kotlin.Comparator
import kotlin.collections.HashSet

/**
 * This class is a priority set. It delegates a priorityQueue as well as a HashSet.
 * The goal is the efficient add/remove in a priorityQueue while avoiding duplicate values.
 */
class PrioritySet<E>(comparator: Comparator<E>) {
    private val queue = PriorityQueue(comparator)
    private val set = HashSet<E>()

    fun add(item: E) {
        if (!set.contains(item)) {
            queue.add(item)
            set.add(item)
        }
    }

    fun remove(): E {
        val item = queue.remove()
        set.remove(item)
        return item
    }

    fun isNotEmpty() = set.isNotEmpty()

}
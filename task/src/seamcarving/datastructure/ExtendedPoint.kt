package seamcarving.datastructure

/**
 * This class intended to be used by the shortest path algorithm.
 * It extends (logically) Point class
 */
data class ExtendedPoint(
    val x: Int,
    val y: Int,
    val energy: Double,
    val color: RGB,
    var visited: Boolean = false,
    var shortestPath: Double = Double.MAX_VALUE,
    var prevPoint: ExtendedPoint? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExtendedPoint

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x ushr 10
        result += y
        return result
    }
}

package seamcarving.service

import seamcarving.datastructure.EnergyGraph
import seamcarving.datastructure.ExtendedPoint
import seamcarving.datastructure.Point
import seamcarving.datastructure.PrioritySet
import seamcarving.datastructure.SeamImage

/**
 * This class provides seam calculation for a given image og type SeamImage
 */
class SeamCalculator(private val image: SeamImage) {

    fun removeSeam(count: Int): Array<Array<Point>> {
        var energyGraph = EnergyGraph(image)
        energyGraph.points[energyGraph.width - 1][energyGraph.height - 1]
        repeat(count) {
            val seam = calculateSeam(energyGraph)
            energyGraph = EnergyGraph.getRemovedSeam(energyGraph, seam)
        }
        return Array(energyGraph.width) { row ->
            Array(energyGraph.height - 2) { col ->
                val extendedPoint = energyGraph.points[row][col + 1]
                Point(extendedPoint.x, extendedPoint.y - 1, extendedPoint.color)
            }
        }
    }

    fun calculateSeam(): List<Point> {
        val energyGraph = EnergyGraph(image)
        val seam = calculateSeam(energyGraph)
        return seam.map { image.getPoint(it!!.x, it.y - 1) }
    }

    private fun calculateSeam(energyGraph: EnergyGraph): Array<ExtendedPoint?> {
        val source = energyGraph.points[0][0]
        val set = PrioritySet<ExtendedPoint> { e1, e2 -> e1.energy.compareTo(e2.energy) }
        source.visited = true
        source.shortestPath = 0.0
        set.add(source)
        while (set.isNotEmpty()) {
            val point = set.remove()
            energyGraph.neighbors(point)
                .filter { !it.visited }
                .forEach {
                    val candidatePathCost = point.shortestPath + it.energy
                    if (candidatePathCost < it.shortestPath) {
                        it.shortestPath = candidatePathCost
                        it.prevPoint = point
                    }
                    set.add(it)
                }
            point.visited = true
        }
        val seam = arrayOfNulls<ExtendedPoint>(energyGraph.height)
        var pointer: ExtendedPoint? = energyGraph.points[energyGraph.width - 1][energyGraph.height - 1]
        while (pointer?.prevPoint != null) {
            seam[pointer.y] = pointer
            pointer = pointer.prevPoint
        }
        return seam
    }



}




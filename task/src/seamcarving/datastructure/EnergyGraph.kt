package seamcarving.datastructure

import seamcarving.service.EnergyCalculator

/**
 * This data-structure is intended to make a graph of a given image in which
 * a first 0-energy row and last 0-energy row is added. It also represents edge in which
 * each pixel(x,y) is adjacent to three pixels (x-1, y+1), (x, y+1), and (x+1, y+1).
 * The pixels of first and last row are additionally adjacent to the next and previous ones.
 */
class EnergyGraph(val points: Array<Array<ExtendedPoint>>) {
    val width = points.size
    val height = points[0].size

    constructor(image: SeamImage) : this(initializePoints(image))

    companion object {
        /**
         * The method makes an object out of an object of SeamImage
         */
        private fun initializePoints(image: SeamImage): Array<Array<ExtendedPoint>> {
            val width = image.width
            val height = image.height + 2
            val energyCalculator = EnergyCalculator(image)
            return Array(width) { x ->
                Array(height) { y ->
                    val energy: Double
                    val color: RGB
                    if (y == 0 || y == height - 1) {
                        energy = 0.0
                        color = RGB.BLACK_COLOR
                    } else {
                        val point = image.getPoint(x, y - 1)
                        energy = energyCalculator.calcEnergy(point)
                        color = point.rgb
                    }
                    ExtendedPoint(x, y, energy, color)
                }
            }
        }

        /**
         * This method removes a seam from the energy graph by shifting pixels in the right hand of the seam pixel
         * in each row to the left
         */
        fun getRemovedSeam(energyGraph: EnergyGraph, seam: Array<ExtendedPoint?>): EnergyGraph {
            val points = Array(energyGraph.width - 1) { x ->
                Array(energyGraph.height) { y ->
                    val xIndex = seam[y]!!.x
                    val extendedPoint = if (x > xIndex) energyGraph.points[x + 1][y] else energyGraph.points[x][y]
                    ExtendedPoint(x, y, extendedPoint.energy, extendedPoint.color)
                }
            }
            return EnergyGraph(points)
        }
    }


    /**
     * Get neighbors of a pixel based on the definition
     */
    fun neighbors(point: ExtendedPoint): List<ExtendedPoint> {
        val fromX = maxOf(point.x - 1, 0)
        val toX = minOf(point.x + 1, width - 1)
        val fromY = if (point.y == 0 || point.y == height - 1) point.y else point.y + 1
        val toY = minOf(point.y + 1, height - 1)
        val list = ArrayList<ExtendedPoint>()
        for (x in fromX..toX) {
            for (y in fromY..toY) {
                if (x == point.x && y == point.y) {
                    continue
                }
                list.add(points[x][y])
            }
        }
        return list
    }

}
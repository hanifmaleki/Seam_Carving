package seamcarving.service

import seamcarving.datastructure.Point
import seamcarving.datastructure.SeamImage
import kotlin.math.sqrt

/**
 * This class contains energy calculations for a given image of type SeamImage
 */
class EnergyCalculator(private val image: SeamImage) {

    fun calcMaxEnergy(): Double {
        val width = image.width
        val height = image.height
        var maxEnergy = Double.MIN_VALUE
        for (x in 0 until width) {
            for (y in 0 until height) {
                val energy = calcEnergy(image.getPoint(x, y))
                if (energy > maxEnergy) {
                    maxEnergy = energy
                }
            }
        }
        return maxEnergy
    }

    fun calcEnergy(point: Point): Double {
        val deltaX = calculateDeltaX(point)
        val deltaY = calculateDeltaY(point)
        return sqrt(deltaX + deltaY)
    }

    private fun calculateDeltaX(point: Point): Double {
        val x = point.x
        val y = point.y
        if (x == 0) {
            return calcDelta(image.getPoint(x, y), image.getPoint(x + 2, y)).toDouble()
        }
        if (x == image.width - 1) {
            return calcDelta(image.getPoint(x, y), image.getPoint(x - 2, y)).toDouble()
        }
        return calcDelta(image.getPoint(x - 1, y), image.getPoint(x + 1, y)).toDouble()
    }

    private fun calculateDeltaY(point: Point): Double {
        val x = point.x
        val y = point.y
        if (y == 0) {
            return calcDelta(image.getPoint(x, y), image.getPoint(x, y + 2)).toDouble()
        }
        if (y == image.height - 1) {
            return calcDelta(image.getPoint(x, y), image.getPoint(x, y - 2)).toDouble()
        }
        return calcDelta(image.getPoint(x, y - 1), image.getPoint(x, y + 1)).toDouble()
    }


    private fun calcDelta(prev: Point, next: Point): Int {
        val dR = (next.rgb.red - prev.rgb.red)
        val dG = (next.rgb.green - prev.rgb.green)
        val dB = (next.rgb.blue - prev.rgb.blue)
        return dR * dR + dG * dG + dB * dB
    }

}
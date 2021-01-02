package seamcarving.service

import seamcarving.datastructure.Point
import seamcarving.datastructure.RGB
import seamcarving.datastructure.SeamImage

/**
 * This class contains all necessary operations of the application on the Image data structure
 */
object DefaultImageManager : ImageManager {

    override fun negativeImage(image: SeamImage): SeamImage {
        val negativeMap: (color: Point) -> Point = {
            val color = it.rgb
            val red = 255 - (color.red)
            val green = 255 - (color.green)
            val blue = 255 - (color.blue)
            Point(it.x, it.y, RGB(red, green, blue))
        }
        return image.getMappedCopy(negativeMap)
    }


    override fun energyImage(image: SeamImage): SeamImage {
        val energyCalculator = EnergyCalculator(image)
        val maxEnergy = energyCalculator.calcMaxEnergy()
        val intensityMap: (color: Point) -> Point = {
            val energy = energyCalculator.calcEnergy(it)
            val intensity = (255.0 * energy / maxEnergy).toInt()
            Point(it.x, it.y, RGB(intensity, intensity, intensity))
        }
        return image.getMappedCopy(intensityMap)
    }

    override fun verticalRedSeam(image: SeamImage): SeamImage {
        val seamCalculator = SeamCalculator(image)
        val seam = seamCalculator.calculateSeam()
        val redSeamMap: (Point) -> Point = { point ->
            val rgb = if (seam[point.y].x == point.x) RGB.RED_COLOR else point.rgb
            Point(point.x, point.y, rgb)
        }
        return image.getMappedCopy(redSeamMap)
    }

    override fun horizontalRedSeam(image: SeamImage): SeamImage {
        val rotated = transposeImage(image)
        val seamedRotated = verticalRedSeam(rotated)
        return transposeImage(seamedRotated)
    }

    override fun resizeImage(image: SeamImage, width: Int, height: Int): SeamImage {
        var removeVerticalSeam = removeVerticalSeam(image, width)

        removeVerticalSeam = removeHorizontalSeam(removeVerticalSeam, height)

        return removeVerticalSeam

    }

    private fun removeVerticalSeam(image: SeamImage, count: Int): SeamImage {
        val seamCalculator = SeamCalculator(image)
        val newImage = seamCalculator.removeSeam(count)
        return SeamImage(newImage)
    }

    private fun removeHorizontalSeam(image: SeamImage, count: Int): SeamImage {
        val rotated = transposeImage(image)
        val removed = removeVerticalSeam(rotated, count)
        return transposeImage(removed)
    }

    override fun transposeImage(image: SeamImage): SeamImage {
        val rotateClockwise: (Point) -> Point = {
            Point(it.y, it.x, it.rgb)
        }
        return image.getMappedCopy(rotateClockwise, image.height, image.width)
    }
}
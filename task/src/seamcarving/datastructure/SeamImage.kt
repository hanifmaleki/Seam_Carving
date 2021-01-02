package seamcarving.datastructure

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

const val FORMAT = "png"
val blackMap: (Point) -> Point = {
    Point(it.x, it.y, RGB.BLACK_COLOR)
}

/**
 * This class is a wrapper of java BufferedImage appropriate for the application
 */
class SeamImage(val image: BufferedImage) {

    val width = image.width
    val height = image.height

    constructor(width: Int, height: Int, map: (Point) -> Point) : this(createImage(width, height, map))

    constructor(filename: String) : this(readImageFromFile(filename))
    constructor(points: Array<Array<Point>>) : this(getBufferImage(points))

    companion object {
        private fun createImage(width: Int, height: Int, map: (Point) -> Point = blackMap): BufferedImage {
            val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val mappedPoint = map(Point(x, y, RGB.BLACK_COLOR))
                    image.setRGB(mappedPoint.x, mappedPoint.y, mappedPoint.rgb.color)
                }
            }
            return image
        }

        private fun readImageFromFile(filename: String): BufferedImage {
            val file = File(filename)
            return ImageIO.read(file)!!
        }

        fun fillRedDiagonal(width: Int, height: Int): SeamImage {
            val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            val max = maxOf(height, width)
            for (index in 0 until max) {
                val x = index * width / max
                val y = index * height / max
                image.setRGB(x, y, RGB.RED_COLOR.color)
                image.setRGB(x, height - y - 1, RGB.RED_COLOR.color)
            }
            return SeamImage(image)
        }

        private fun getBufferImage(points: Array<Array<Point>>): BufferedImage {
            val width = points.size
            val height = points[0].size
            val bufferImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val point = points[x][y]
                    bufferImage.setRGB(point.x, point.y, point.rgb.color)
                }
            }
            return bufferImage
        }
    }

    fun writeImageToFile(filename: String?) {
        ImageIO.write(image, FORMAT, File(filename))
    }


    fun getMappedCopy(map: (point: Point) -> Point, w: Int = width, h: Int = height): SeamImage {
        val copy = createImage(w, h)
        for (x in 0 until width) {
            for (y in 0 until height) {
                val mappedPoint = map(getPoint(x, y))
                copy.setRGB(mappedPoint.x, mappedPoint.y, mappedPoint.rgb.color)
            }
        }
        return SeamImage(copy)
    }


    fun getPoint(x: Int, y: Int) = Point(x, y, RGB(image.getRGB(x, y)))

}
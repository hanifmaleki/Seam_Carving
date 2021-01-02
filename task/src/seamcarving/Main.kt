package seamcarving

import seamcarving.datastructure.SeamImage
import seamcarving.service.DefaultImageManager
import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    var inputFilename: String = ""
    var outputFilename: String = ""
    var width: Int = 0
    var height: Int = 0

    for (index in args.indices) {
        if (args[index] == "-in") {
            inputFilename = args[index + 1]
        }
        if (args[index] == "-out") {
            outputFilename = args[index + 1]
        }
        if (args[index] == "-width") {
            width = args[index + 1].toInt()
        }
        if (args[index] == "-height") {
            height = args[index + 1].toInt()
        }
    }

    println(Date(System.currentTimeMillis()))
    val image = SeamImage(filename = inputFilename)
    val mapped = DefaultImageManager.resizeImage(image, width, height)
    println(Date(System.currentTimeMillis()))
    mapped.writeImageToFile(outputFilename)
}






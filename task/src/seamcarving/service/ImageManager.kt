package seamcarving.service

import seamcarving.datastructure.SeamImage

interface ImageManager {
    fun negativeImage(image: SeamImage): SeamImage
    fun energyImage(image: SeamImage): SeamImage
    fun verticalRedSeam(image: SeamImage): SeamImage
    fun horizontalRedSeam(image: SeamImage): SeamImage
    fun resizeImage(image: SeamImage, width: Int, height: Int): SeamImage
    fun transposeImage(image: SeamImage): SeamImage
}
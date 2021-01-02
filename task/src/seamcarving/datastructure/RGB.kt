package seamcarving.datastructure


/**
 * This class is simplified version of java awt.Color appropriate for this application
 */
data class RGB(val color: Int) {
    val red = color and 0xFF0000 ushr (16)
    val blue = color and 0x00FF00 ushr (8)
    val green = color and 0x0000FF

    constructor(red: Int, green: Int, blue: Int) : this(red * 0x10000 + green * 0x100 + blue)

    companion object {
        val RED_COLOR = RGB(0xFF0000)
        val BLACK_COLOR = RGB(0x000000)
    }

}
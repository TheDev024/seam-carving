package seamcarving

data class SeamPixel(
    val x: Int,
    val y: Int,
    var prevX: Int?,
    var prevY: Int?,
    var value: Double
)
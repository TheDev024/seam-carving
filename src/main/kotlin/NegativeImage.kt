package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage

class NegativeImage(private val image: BufferedImage) {
    fun process() {
        for (x in 0 until image.width) for (y in 0 until image.height) {
            val pixel = image.getRGB(x, y)
            val color = Color(pixel)
            val r = 255 - color.red
            val g = 255 - color.green
            val b = 255 - color.blue
            val negativeColor = Color(r, g, b)
            image.setRGB(x, y, negativeColor.rgb)
        }
    }
}

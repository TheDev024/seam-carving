package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage

class EnergyImage(private val image: BufferedImage) {
    fun process() {
        val energyMap = DetailedMap(image).map

        val maxEnergy = energyMap.map { pixels -> pixels.maxOf { it!!.energy } }.maxOf { it }

        for (x in 0 until image.width) for (y in 0 until image.height) {
            val pixel = energyMap[x][y]
            val intensity = (255.0 * pixel!!.energy / maxEnergy).toInt()
            val color = Color(intensity, intensity, intensity)
            image.setRGB(x, y, color.rgb)
        }
    }
}

package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.sqrt

class EnergyImage(private val image: BufferedImage) {
    fun process() {
        val energyMap = MutableList<MutableList<Pixel?>>(image.width) { MutableList(image.height) { null } }

        for (x in 0 until image.width) for (y in 0 until image.height) {
            val energy = getEnergy(x, y)
            val color = Color(image.getRGB(x, y))
            energyMap[x][y] = Pixel(color, energy)
        }

        val maxEnergy = energyMap.map { pixels -> pixels.maxOf { it!!.energy } }.maxOf { it }

        for (x in 0 until image.width) for (y in 0 until image.height) {
            val pixel = energyMap[x][y]
            val intensity = (255.0 * pixel!!.energy / maxEnergy).toInt()
            val color = Color(intensity, intensity, intensity)
            image.setRGB(x, y, color.rgb)
        }
    }

    private fun getEnergy(x: Int, y: Int): Double {
        val checkX: Int = when (x) {
            0 -> 1

            image.width - 1 -> x - 1

            else -> x
        }

        val checkY: Int = when (y) {
            0 -> 1

            image.height - 1 -> y - 1

            else -> y
        }

        val prevX = Color(image.getRGB(checkX - 1, y))
        val nextX = Color(image.getRGB(checkX + 1, y))
        val nextY = Color(image.getRGB(x, checkY + 1))
        val prevY = Color(image.getRGB(x, checkY - 1))

        val rX = prevX.red - nextX.red
        val gX = prevX.green - nextX.green
        val bX = prevX.blue - nextX.blue

        val rY = prevY.red - nextY.red
        val gY = prevY.green - nextY.green
        val bY = prevY.blue - nextY.blue

        val lambdaX = rX * rX + gX * gX + bX * bX
        val lambdaY = rY * rY + gY * gY + bY * bY

        return sqrt(lambdaX + lambdaY + 0.0)
    }
}

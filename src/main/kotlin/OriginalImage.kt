package seamcarving

import sun.awt.image.ImageFormatException
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.ImageIO

class OriginalImage(
    inputFile: File
) {
    private val image: BufferedImage

    companion object Orientation {
        const val VERTICAL = 1
        const val HORIZONTAL = 2
    }

    init {
        try {
            this.image = ImageIO.read(inputFile)
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException()
        } catch (e: ImageFormatException) {
            throw ImageFormatException("Image format invalid")
        }
    }

    fun process(type: Type, outputFile: File) {
        when (type) {
            Type.NEGATIVE_IMAGE -> NegativeImage(image).process()

            Type.ENERGY_IMAGE -> EnergyImage(image).process()
        }
        ImageIO.write(image, "png", outputFile)
    }

    fun findSeam(outputFile: File, orientation: Int) {
        val detailedMap = DetailedMap(image).map
        val seamMap = detailedMap.map {
            it.map { pixel -> SeamPixel(pixel!!.x, pixel.y, null, null, pixel.energy) }.toTypedArray()
        }.toTypedArray()

        val seam: SeamPixel = when (orientation) {
            VERTICAL -> findVerticalSeam(seamMap)

            HORIZONTAL -> findHorizontalSeam(seamMap)

            else -> throw NoSuchOrientationException("Orientation must be either vertical(1) or horizontal(2)")
        }

        var x: Int? = seam.x
        var y: Int? = seam.y

        val redColor = Color(255, 0, 0).rgb

        while (x != null && y != null) {
            image.setRGB(x, y, redColor)
            val currentSeam = seamMap[x][y]
            x = currentSeam.prevX
            y = currentSeam.prevY
        }
        ImageIO.write(image, "png", outputFile)
    }

    private fun findHorizontalSeam(seamMap: Array<Array<SeamPixel>>): SeamPixel {
        val transposedSeamMap = Array(seamMap.first().size) { Array(seamMap.size) { SeamPixel(0, 0, 0, 0, 0.0) } }

        seamMap.forEachIndexed { x, row ->
            row.forEachIndexed { y, pixel ->
                transposedSeamMap[y][x] = pixel
            }
        }

        processDijkstra(transposedSeamMap, image.height, image.width)

        val bottomRow = transposedSeamMap.map { it.last() }

        return bottomRow.minBy { it.value }
    }

    private fun findVerticalSeam(seamMap: Array<Array<SeamPixel>>): SeamPixel {
        processDijkstra(seamMap, image.width, image.height)

        val bottomRow = seamMap.map { it.last() }

        return bottomRow.minBy { it.value }
    }

    private fun processDijkstra(map: Array<Array<SeamPixel>>, width: Int, height: Int) {
        for (y in 1 until height) for (x in 0 until width) {
            val pixelsAbove = mutableListOf(map[x][y - 1])
            if (x != 0) pixelsAbove.add(map[x - 1][y - 1])
            if (x != width - 1) pixelsAbove.add(map[x + 1][y - 1])

            val minSeam = pixelsAbove.minBy { it.value }
            map[x][y].prevX = minSeam.x
            map[x][y].prevY = minSeam.y
            map[x][y].value += minSeam.value
        }
    }
}

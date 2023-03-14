package seamcarving

import sun.awt.image.ImageFormatException
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.ImageIO

class OriginalImage(
    private val inputFile: File
) {
    private val image: BufferedImage

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

    fun findVerticalSeam(outputFile: File) {
        val detailedMap = DetailedMap(image).map
        val seamMap = detailedMap.map { it.map { pixel -> SeamPixel(pixel!!.x, pixel.y, null, null, pixel.energy) }.toTypedArray() }.toTypedArray()

        for (y in 1 until (image.height)) for (x in 0 until image.width) {
            val pixelsAbove = mutableListOf(seamMap[x][y - 1])
            if (x != 0) pixelsAbove.add(seamMap[x - 1][y - 1])
            if (x != image.width - 1) pixelsAbove.add(seamMap[x + 1][y - 1])

            val minSeam = pixelsAbove.minBy { it.value }
            seamMap[x][y].prevX = minSeam.x
            seamMap[x][y].prevY = minSeam.y
            seamMap[x][y].value += minSeam.value
        }

        val bottomRow = seamMap.map { it.last() }
        val minSeam = bottomRow.minBy { it.value }

        var x: Int? = minSeam.x
        var y: Int? = minSeam.y

        // val verticalSeam = mutableListOf<Pair<Int, Int>>()

        val redColor = Color(255, 0, 0).rgb

        while (x != null && y != null) {
            image.setRGB(x, y, redColor)
            // verticalSeam.add(Pair(x, y))
            val currentSeam = seamMap[x][y]
            x = currentSeam.prevX
            y = currentSeam.prevY
        }
        ImageIO.write(image, "png", outputFile)
    }
}

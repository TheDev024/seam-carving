package seamcarving

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageProcessor(
    private val inputFile: File, private val outputFile: File
) {
    fun process(type: Type) {
        val inputImage: BufferedImage = ImageIO.read(inputFile)
        when (type) {
            Type.NEGATIVE_IMAGE -> NegativeImage(inputImage).process()

            Type.ENERGY_IMAGE -> EnergyImage(inputImage).process()
        }

        ImageIO.write(inputImage, "png", outputFile)
    }
}

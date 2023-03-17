import matrix.ImageMatrix
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class SeamCarver(
    inputFile: File
) {
    private val image: BufferedImage = ImageIO.read(inputFile)
    private val imageMatrix = ImageMatrix.toMatrix(image)

    fun resizeImage(width: Int, height: Int, outputFile: File) {
        imageMatrix.resize(width, height)
        val resizedImage = imageMatrix.generateImage()
        ImageIO.write(resizedImage, "png", outputFile)
    }
}

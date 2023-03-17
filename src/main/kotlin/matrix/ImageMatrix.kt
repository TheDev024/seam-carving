package matrix

import data.Coordinate
import data.Pixel
import java.awt.Color
import java.awt.image.BufferedImage

class ImageMatrix(matrix: List<MutableList<Pixel>>) : CoordinatedMatrix<Pixel>(matrix) {
    companion object {
        fun toMatrix(image: BufferedImage): ImageMatrix = ImageMatrix(List(image.width) { x ->
            MutableList(image.height) { y ->
                Pixel(Coordinate(x, y), Color(image.getRGB(x, y)))
            }
        })
    }

    fun resize(width: Int, height: Int) {
        repeat(width) { carveSeam(VERTICAL) }
        repeat(height) { carveSeam(HORIZONTAL) }
    }

    fun generateImage(): BufferedImage {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        matrix.forEachIndexed { x, row ->
            row.forEachIndexed { y, pixel ->
                image.setRGB(x, y, pixel.color.rgb)
            }
        }
        return image
    }

    private fun carveSeam(orientation: Int) {
        val energyMatrix = EnergyMatrix.toEnergyMatrix(this)
        val seam = energyMatrix.findSeam(orientation)
        removeCurve(seam, orientation)
    }
}

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.Scanner
import javax.imageio.ImageIO

val scanner = Scanner(System.`in`)

fun main() {
    println("Enter rectangle width:")
    val width = scanner.nextInt() - 1
    println("Enter rectangle height:")
    val height = scanner.nextInt() - 1
    println("Enter output image name:")
    val outPath = scanner.next()
    val outFile = File(outPath)
    val image = BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_BGR)
    val graphic = image.graphics
    graphic.color = Color.RED
    graphic.drawLine(0, 0, width, height)
    graphic.drawLine(0, height, width, 0)
    ImageIO.write(image, "png", outFile)
}

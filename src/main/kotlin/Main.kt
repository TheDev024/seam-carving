import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    var inputPath: String? = null
    var outputPath: String? = null
    for (i in 0 until args.lastIndex) when (args[i]) {
        "-in" -> inputPath = args[i + 1]

        "-out" -> outputPath = args[i + 1]
    }
    val inputFile = File(inputPath ?: "in.png")
    val outputFile = File(outputPath ?: "out.png")
    val inputImage = ImageIO.read(inputFile)
    for (x in 0 until inputImage.width)
        for (y in 0 until inputImage.height) {
            val pixel = inputImage.getRGB(x, y)
            val color = Color(pixel)
            val r = 255 - color.red
            val g = 255 - color.green
            val b = 255 - color.blue
            val negativeColor = Color(r, g, b)
            inputImage.setRGB(x, y, negativeColor.rgb)
        }
    ImageIO.write(inputImage, "png", outputFile)
}

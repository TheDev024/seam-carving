package seamcarving

import seamcarving.OriginalImage.Orientation
import java.io.File

fun main(args: Array<String>) {
    var inputPath: String? = null
    var outputPath: String? = null
    for (i in 0 until args.lastIndex) when (args[i]) {
        "-in" -> inputPath = args[i + 1]

        "-out" -> outputPath = args[i + 1]
    }
    val inputFile = File(inputPath ?: "in.png")
    val outputFile = File(outputPath ?: "out.png")

    val image = OriginalImage(inputFile)
    image.findSeam(outputFile, Orientation.HORIZONTAL)
}

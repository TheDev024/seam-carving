package seamcarving

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

    val processor = ImageProcessor(inputFile, outputFile)
    processor.process(Type.ENERGY_IMAGE)
}

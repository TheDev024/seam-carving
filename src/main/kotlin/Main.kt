import java.io.File

fun main(args: Array<String>) {

    var inputPath: String? = null
    var outputPath: String? = null
    var width: Int? = null
    var height: Int? = null

    for (i in 0 until args.lastIndex) when (args[i]) {
        "-in" -> inputPath = args[i + 1]

        "-out" -> outputPath = args[i + 1]

        "-width" -> width = args[i + 1].toInt()

        "-height" -> height = args[i + 1].toInt()
    }

    val inputFile = File(inputPath ?: "input.png")
    val outputFile = File(outputPath ?: "output.png")

    val seamCarver = SeamCarver(inputFile)
    seamCarver.resizeImage(width ?: 30, height ?: 20, outputFile)
}

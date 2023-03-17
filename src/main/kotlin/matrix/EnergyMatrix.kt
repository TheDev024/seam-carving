package matrix

import data.Coordinate
import data.EnergyUnit
import java.awt.Color
import kotlin.math.sqrt

class EnergyMatrix(matrix: List<MutableList<EnergyUnit>>) : CoordinatedMatrix<EnergyUnit>(matrix) {
    companion object {
        fun toEnergyMatrix(imageMatrix: ImageMatrix): EnergyMatrix = EnergyMatrix(List(imageMatrix.width) { x ->
            MutableList(imageMatrix.height) { y ->
                EnergyUnit(
                    Coordinate(x, y), getEnergy(x, y, imageMatrix)
                )
            }
        })

        private fun getEnergy(x: Int, y: Int, imageMatrix: ImageMatrix): Double {
            val checkX: Int = when (x) {
                0 -> 1

                imageMatrix.width - 1 -> x - 1

                else -> x
            }

            val checkY: Int = when (y) {
                0 -> 1

                imageMatrix.height - 1 -> y - 1

                else -> y
            }

            val prevX = Color(imageMatrix[checkX - 1, y].color.rgb)
            val nextX = Color(imageMatrix[checkX + 1, y].color.rgb)
            val nextY = Color(imageMatrix[x, checkY + 1].color.rgb)
            val prevY = Color(imageMatrix[x, checkY - 1].color.rgb)

            val rX = prevX.red - nextX.red
            val gX = prevX.green - nextX.green
            val bX = prevX.blue - nextX.blue

            val rY = prevY.red - nextY.red
            val gY = prevY.green - nextY.green
            val bY = prevY.blue - nextY.blue

            val lambdaX = rX * rX + gX * gX + bX * bX
            val lambdaY = rY * rY + gY * gY + bY * bY

            return sqrt(lambdaX + lambdaY + 0.0)
        }
    }

    fun findSeam(orientation: Int): List<Coordinate> {
        val graph = GraphMatrix.toGraph(this)
        return graph.getSeam(orientation)
    }
}

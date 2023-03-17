package matrix

import data.Coordinate
import data.GraphUnit

class GraphMatrix(matrix: List<MutableList<GraphUnit>>) : CoordinatedMatrix<GraphUnit>(matrix) {
    companion object {
        fun toGraph(energyMatrix: EnergyMatrix) = GraphMatrix(List(energyMatrix.width) { x ->
            MutableList(energyMatrix.height) { y ->
                GraphUnit(
                    energyMatrix[x, y].coordinate, pathEnergy = energyMatrix[x, y].energy
                )
            }
        })
    }

    fun getSeam(orientation: Int): List<Coordinate> {
        if (orientation == HORIZONTAL) transpose()
        for (y in 1 until height) for (x in 0 until width) {
            val previousPixels = mutableListOf(matrix[x][y - 1])
            if (x != 0) previousPixels += matrix[x - 1][y - 1]
            if (x != width - 1) previousPixels += matrix[x + 1][y - 1]

            val minSeam = previousPixels.minBy { it.pathEnergy }
            matrix[x][y].previousCoordinate = minSeam.coordinate
            matrix[x][y].pathEnergy += minSeam.pathEnergy
        }

        val lastRow = matrix.map { it.last() }
        var seamPixel = lastRow.minBy { it.pathEnergy }
        val seam = mutableListOf(seamPixel.coordinate)

        var previousCoordinate = seamPixel.previousCoordinate
        while (previousCoordinate != null) {
            val x = previousCoordinate.x
            val y = previousCoordinate.y
            seamPixel = if (orientation == HORIZONTAL) matrix[y][x] else matrix[x][y]
            previousCoordinate = seamPixel.previousCoordinate
            seam.add(seamPixel.coordinate)
        }

        return seam
    }
}

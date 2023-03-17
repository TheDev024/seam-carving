package matrix

import data.Coordinate

abstract class Matrix<T>(
    protected var matrix: List<MutableList<T>>
) {
    companion object Orientation {
        const val VERTICAL = 0
        const val HORIZONTAL = 1
    }

    val width: Int
        get() = matrix.size

    val height: Int
        get() = matrix[0].size

    operator fun get(x: Int, y: Int): T =
        if (x >= width || y >= height) throw ArrayIndexOutOfBoundsException() else matrix[x][y]

    operator fun set(x: Int, y: Int, element: T) {
        if (x >= width || y >= height) throw ArrayIndexOutOfBoundsException() else matrix[x][y] = element
    }

    fun transpose() {
        val transposedMatrix = List(height) { y ->
            MutableList(width) { x ->
                matrix[x][y]
            }
        }
        matrix = transposedMatrix
    }

    protected open fun removeCurve(path: List<Coordinate>, orientation: Int) {
        if (orientation == VERTICAL) transpose()
        path.forEach { element ->
            val x = element.x
            val y = element.y

            when (orientation) {
                VERTICAL -> matrix[y].removeAt(x)

                HORIZONTAL -> matrix[x].removeAt(y)
            }
        }
        if (orientation == VERTICAL) transpose()
    }

    override fun toString(): String = matrix.joinToString("\n") { row -> row.joinToString(", ") }
}

package matrix

import data.Cell
import data.Coordinate

open class CoordinatedMatrix<T : Cell>(matrix: List<MutableList<T>>) : Matrix<T>(matrix) {
    override fun removeCurve(path: List<Coordinate>, orientation: Int) {
        super.removeCurve(path, orientation)
        updateCoordinates()
    }

    private fun updateCoordinates() {
        matrix.forEachIndexed { x, row ->
            row.forEachIndexed { y, cell ->
                cell.coordinate = Coordinate(x, y)
            }
        }
    }
}

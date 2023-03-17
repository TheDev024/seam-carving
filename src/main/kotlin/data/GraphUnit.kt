package data

data class GraphUnit(
    override var coordinate: Coordinate, var previousCoordinate: Coordinate? = null, var pathEnergy: Double = 0.0
) : Cell(coordinate)

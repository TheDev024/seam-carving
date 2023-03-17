package data

class EnergyUnit(
    override var coordinate: Coordinate = Coordinate(),
    val energy: Double = 0.0,
) : Cell(coordinate)

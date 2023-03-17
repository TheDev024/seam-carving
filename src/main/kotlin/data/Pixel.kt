package data

import java.awt.Color

class Pixel(
    override var coordinate: Coordinate = Coordinate(), val color: Color = Color.WHITE
) : Cell(coordinate)

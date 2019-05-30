package tools

import org.openrndr.Program
import org.openrndr.draw.isolated
import org.openrndr.shape.Rectangle

fun Program.grid(x: Int, y: Int,
                 marginX: Double = 0.0, marginY: Double = 0.0,
                 gutterX: Double = 0.0, gutterY: Double = 0.0,

                 drawFunction: (Int, Int) -> Unit) {

    val hg = (x-1).coerceAtLeast(0)
    val vg = (y-1).coerceAtLeast(0)
    val cellWidth = (width - 2.0*marginX - hg * gutterX).toDouble() / x
    val cellHeight = (height - 2.0*marginY - vg * gutterY).toDouble() / y
    var v = marginY

    var realWidth = width
    val realHeight = height

    width = cellWidth.toInt()
    height = cellHeight.toInt()

    for (j in 0 until y) {
        var u = marginX
        for (i in 0 until x) {
            drawer.isolated {
                drawer.drawStyle.clip = Rectangle(u, v, cellWidth, cellHeight)
                drawer.translate(u, v)
                drawFunction(i, j)
            }
            u += cellWidth
            u += gutterX
        }
        v += cellHeight
        v+= gutterY

    }
    width = realWidth
    height = realHeight
}
package examples

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
import org.openrndr.filter.blur.DropShadow
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.VerticalWaves
import org.openrndr.workshop.toolkit.filters.Waves
import tools.grid
import java.time.LocalDateTime

fun main() = application {
    configure {
        width = 600
        height = 800
    }

    program {
        val poster = compose {
            layer {
                draw {
                    grid(5, 5, 20.0, 20.0, 20.0, 20.0) { x, y ->
                        drawer.fill = ColorRGBa.RED
                        drawer.circle(width / 2.0, height / 2.0, 50.0 + Math.cos(x + y + seconds) * 10.0)
                        drawer.fill = ColorRGBa.YELLOW
                        drawer.circle(width / 2.0, height / 2.0, 50.0 + Math.sin(x + y + seconds) * 10.0)
                    }
                }
            }
        }
        extend {
            poster.draw(drawer)
        }
    }
}


package poster

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer

fun main() = application {

    program {

        configure {
            width = 800
            height = 800
        }

        val poster = compose {
            layer {
                draw {
                    drawer.background(ColorRGBa.WHITE)
                }
            }
        }

        extend {
            poster.draw(drawer)
        }

    }

}
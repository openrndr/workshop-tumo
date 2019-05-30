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
                    drawer.fill = ColorRGBa.PINK
                    drawer.circle(width/2.0, height/2.0, 200.0)
                }
            }

            layer {

                draw {
                    val font = FontImageMap.fromUrl("file:data/fonts/Rumeur/rumeur.otf", 32.0)
                    drawer.fontMap = font
                    drawer.fill = ColorRGBa.WHITE
                    drawer.translate(10.0, 10.0)
                    drawer.text("poster for today:", 0.0, 100.0)
                }
            }
            layer {
                post(Waves()) {
                    amplitude = 0.1
                    period = 20.0
                    phase = seconds
                }
                post(DropShadow()) {
                    window = 3
                    gain = 0.8
                    shift = Vector2(10.0, -10.0)
                }

                draw {
                    val font = FontImageMap.fromUrl("file:data/fonts/Rumeur/rumeur.otf", 96.0)
                    drawer.fontMap = font
                    drawer.fill = ColorRGBa.WHITE
                    val date = LocalDateTime.now()
                    drawer.translate(10.0, 10.0)
                    drawer.text("${date.dayOfWeek.name}", 0.0, 200.0)
                    drawer.text("${date.month.name} ${date.dayOfMonth}", 0.0, 280.0)
                    drawer.text("${date.year}", 0.0, 360.0)
                }
            }
        }

        extend {
            poster.draw(drawer)
        }
    }

}


package examples

import org.openrndr.application
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.grayscale
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
import org.openrndr.ffmpeg.FFMPEGVideoPlayer
import org.openrndr.filter.blur.BoxBlur

fun main() {

    application {
        configure {
            width = 600
            height = 800
        }

        program {

            val camera = FFMPEGVideoPlayer.fromDevice()
            camera.start()

            val poster = compose {
                layer {
                    post(BoxBlur()) {
                        window = (20.0 * mouse.position.y/height).toInt()
                    }
                    draw {
                        drawer.drawStyle.colorMatrix = grayscale()
                        drawer.scale(height.toDouble()/camera.height)
                        camera.draw(drawer)
                    }
                }
                layer {

                    val font = FontImageMap.fromUrl("file:data/fonts/Rumeur/rumeur.otf", 128.0)

                    draw {
                        drawer.fontMap = font
                        drawer.text("0 0", width/2.0 - 50.0, height/4.0)
                    }

                }
            }

            extend {
                camera.next()
                poster.draw(drawer)
            }
        }
    }
}
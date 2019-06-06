package examples

import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.isolated
import org.openrndr.draw.loadImage
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
import org.openrndr.filter.blur.DropShadow
import org.openrndr.math.Vector2
import org.openrndr.text.Writer
import org.openrndr.workshop.toolkit.filters.VerticalWaves
import org.openrndr.workshop.toolkit.filters.Waves
import tools.website.scrapeWebsite
import java.time.LocalDateTime

fun main() = application {

    class Image(val image: ColorBuffer, val position: Vector2)

    configure {
        width = 600
        height = 800
    }

    program {
        val poster = compose {

            layer {

                val image = loadImage("data/images/image.jpg")
                val zoom = height / image.height.toDouble()
                class AnimImage(var x:Double = 0.0, var y:Double = 0.0, var scale:Double):Animatable()

                val animImage = AnimImage(scale=zoom)

                draw {
                    animImage.updateAnimation()
                    if (!animImage.hasAnimations()) {
                        val minZoom = height / image.height.toDouble()
                        val maxZoom = minZoom*2.0
                        val scale = Math.random()*(maxZoom-minZoom)+minZoom
                        animImage.animate("scale", scale, 3000, Easing.CubicInOut)
                        val cx = (width - image.width) /2.0
                        val cy = (height - image.height)/2.0
                        val tx = (Math.random()-0.5) * cx * scale
                        val ty = (Math.random()-0.5) * cy * scale
                        animImage.animate("x", tx, 3000, Easing.CubicInOut)
                        animImage.animate("y", ty, 3000, Easing.CubicInOut)
                    }
                    val cx = (width - image.width) /2.0
                    val cy = (height - image.height)/2.0
                    drawer.translate(animImage.x, animImage.y)
                    drawer.translate(cx, cy)

                    drawer.translate(image.width/2.0, image.height/2.0)
                    drawer.scale(animImage.scale)
                    drawer.translate(-image.width/2.0, -image.height/2.0)
                    drawer.image(image)
                }
            }
        }

        extend {
            poster.draw(drawer)
        }
    }
}


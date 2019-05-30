package examples

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.isolated
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

        val imdbExludeImages = listOf("https://m.media-amazon.com/images/G")
        val cnnExcludeImages = listOf("https://dynaimage.c11dn.cnn.com/cnn/e_blur:500,q_auto:low,w_50,c_fill,g_auto,h_28,ar_16:9")
        //val page = scrapeWebsite("https://www.imdb.com/title/tt2527338", 4, imdbExludeImages)
        //val page = scrapeWebsite("https://www.imdb.com/title/tt0120338", excludeImages = imdbExludeImages)
        val page = scrapeWebsite("https://edition.cnn.com/travel/article/disneyland-star-wars-galaxys-edge-preview/index.html", 20, excludeImages = cnnExcludeImages)

        println(page.title)
        println(page.body)

        fun randomizeImages():List<Image> {
            return page.images.map {
                val x = Math.random() * (width - it.width)
                val y = Math.random() * (height - it.height)
                Image(it, Vector2(x,y))
            }
        }

        var images = randomizeImages()


        mouse.clicked.listen {
            images = randomizeImages()
        }

        val poster = compose {

            layer {
                draw {
                    for (image in images) {
                        for (i in 0 until 10) {
                            drawer.image(image.image, image.position + Vector2(i*4.0, i  * 4.0))
                        }
                    }
                }
            }


            layer {

                post(DropShadow()) {
                    window = 1
                }
                draw {
                    val font = FontImageMap.fromUrl("file:data/fonts/Rumeur/rumeur.otf", 32.0)
                    drawer.fontMap = font
                    val w = Writer(drawer)
                    w.box = drawer.bounds.offsetEdges(-20.0)
                    w.style.leading = 20.0
                    w.newLine()
                    w.text(page.body)
                }

            }
        }

        extend {
            poster.draw(drawer)
        }
    }

}


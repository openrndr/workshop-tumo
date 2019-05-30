package org.openrndr.filter.blur

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.Filter
import org.openrndr.draw.filterShaderFromUrl
import org.openrndr.filter.blend.Normal
import org.openrndr.math.Vector2
import org.openrndr.resourceUrl

class Blend : Filter(filterShaderFromUrl(resourceUrl("/shaders/blend.frag"))) {
    var shift: Vector2 by parameters

}

class DropShadow : Filter(filterShaderFromUrl(resourceUrl("/shaders/dropshadow-blur.frag"))) {

    var window: Int by parameters
    var spread: Double by parameters
    var gain: Double by parameters
    var shift: Vector2 = Vector2.ZERO
    var color: ColorRGBa by parameters

    private var intermediate:ColorBuffer? = null
    private var intermediate2:ColorBuffer? = null
    var b = Blend()

    init {
        color = ColorRGBa.BLACK
        window = 5
        spread = 1.0
        gain = 1.0
    }

    override fun apply(source: Array<ColorBuffer>, target: Array<ColorBuffer>) {

        intermediate?.let {
            if (it.width != target[0].width || it.height != target[0].height) {
                intermediate = null
            }
        }

        intermediate2?.let {
            if (it.width != target[0].width || it.height != target[0].height) {
                intermediate2 = null
            }
        }


        if (intermediate == null) {
            intermediate = ColorBuffer.create(target[0].width, target[0].height, target[0].contentScale, target[0].format, target[0].type)
        }
        if (intermediate2 == null) {
            intermediate2 = ColorBuffer.create(target[0].width, target[0].height, target[0].contentScale, target[0].format, target[0].type)
        }

        intermediate?.let {
            parameters["blurDirection"] = Vector2(1.0, 0.0)
            super.apply(source, arrayOf(it))

            parameters["blurDirection"] = Vector2(0.0, 1.0)
            super.apply(arrayOf(it), arrayOf(intermediate2!!))

            b.shift = shift / Vector2(target[0].width*1.0, target[0].height*1.0)
            b.apply(arrayOf(intermediate2!!, source[0]), target)
        }

    }
}
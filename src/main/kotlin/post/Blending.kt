package post

import org.openrndr.draw.Filter
import org.openrndr.draw.filterShaderFromUrl
import org.openrndr.filter.blend.*
import org.openrndr.resourceUrl

class AbsoluteDifference : Filter(filterShaderFromUrl(resourceUrl("/shaders/difference.frag")))

class Clip : Filter(filterShaderFromUrl(resourceUrl("/shaders/clip.frag")))



import org.openrndr.Program
import org.openrndr.application
import org.openrndr.extra.olive.Olive

fun main() = application {

    configure {
        width = 1280
        height = 720
    }

    program {

        extend(Olive<Program>()) {
            script = "src/main/kotlin/live/scripts/LiveCamera.kts"
        }


    }

}

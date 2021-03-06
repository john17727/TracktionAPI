package app.tracktion.domain.models.routing

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/types")
class Types {
    @Serializable
    @Resource("categories")
    class Categories(val parent: Types = Types())

    @Serializable
    @Resource("muscles")
    class Muscles(val parent: Types = Types())
}
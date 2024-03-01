package infinitum.com.templates

import infinitum.com.components.adInfinitumImage
import infinitum.com.components.inputComponent
import infinitum.com.components.respondComponent
import infinitum.com.components.searchResultContainer
import infinitum.com.pages.withRootHtml
import io.ktor.server.application.*
import io.ktor.server.routing.*



fun Application.configureImageSearchRoutes() {
    routing {
        route("/imageSearch") {
            get {
                withRootHtml {
                    inputComponent()
                    searchResultContainer()
                }
            }

            get("/search") {
                val search = call.request.queryParameters["search"].orEmpty()
                respondComponent {
                    repeat(5) {
                        adInfinitumImage(search)
                    }
                }
            }
        }
    }
}
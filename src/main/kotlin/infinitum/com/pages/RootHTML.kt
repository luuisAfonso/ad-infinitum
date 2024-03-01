package infinitum.com.pages

import infinitum.com.components.adInfinitumHeader
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import javax.swing.text.html.HTML

suspend fun PipelineContext<Unit, ApplicationCall>.withRootHtml(block: HtmlBlockTag.() -> Unit) {
    call.respondHtml {
        head {
            meta { charset = "utf-8" }
            meta { name="viewport"; content="width=device-width, initial-scale=1" }
            title { +"Tables" }
            script { src="https://cdn.tailwindcss.com" }
            script {
                src="https://unpkg.com/htmx.org@1.9.10"
                integrity="sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC"
                attributes["crossorigin"]="anonymous"
            }
        }
        body {
            classes = setOf(
                "min-h-[100vh]",
                "flex",
                "flex-col",
                "justify-start",
                "content-center",
                "bg-black",
                "text-white"
            )
            adInfinitumHeader()
            block()
        }
    }
}
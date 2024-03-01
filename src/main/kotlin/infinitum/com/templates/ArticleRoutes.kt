package infinitum.com.templates

import infinitum.com.clients.generateLocalArticle
import infinitum.com.clients.generateLocalArticleLinks
import infinitum.com.clients.generateLocalImage
import infinitum.com.components.adInfinitumArticle
import infinitum.com.components.adInfinitumImage
import infinitum.com.components.respondComponent
import infinitum.com.pages.withRootHtml
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureArticleRoutes() {
    routing {
        get("/image/{generate}") {
            val generate = call.parameters["generate"] ?: "AD INFINITUM"
            val imageBytes = generateLocalImage(generate).getOrElse { ByteArray(0) }
            val file = File.createTempFile("pra", ".png")
            file.writeBytes(imageBytes)
            call.respondFile(file)
        }

        get("/replace/image/{generate}") {
            val generate = call.parameters["generate"] ?: "AD INFINITUM"
            respondComponent { adInfinitumImage(generate) }
        }

        get("/") {
            val articleContent = generateLocalArticleLinks().getOrElse { "ERROR" }
            withRootHtml {
                adInfinitumArticle(
                    title = "",
                    content = articleContent
                )
            }
        }

        get("/{article}") {
            val article = call.parameters["article"] ?: "AD INFINITUM"

            if (article == "favicon.ico") return@get call.respond(400)

            val articleContent = generateLocalArticle(article)

            withRootHtml {
                adInfinitumArticle(
                    title = article,
                    content = articleContent.getOrElse { "ERROR GENERATING ARTICLE" }
                )
            }
        }

        get("/{writer}/{article}") {
            val article = call.parameters["article"] ?: "AD INFINITUM"
            val writer = call.parameters["writer"] ?: "AD INFINITUM"

            if (article == "favicon.ico") return@get call.respond(400)

            val articleContent = generateLocalArticle(article, writer)

            withRootHtml {
                adInfinitumArticle(
                    title = article,
                    content = articleContent.getOrElse { "ERROR GENERATING ARTICLE" },
                    writer = writer
                )
            }
        }
    }
}
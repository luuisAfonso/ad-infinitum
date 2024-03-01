package infinitum.com

import infinitum.com.plugins.*
import infinitum.com.templates.configureArticleRoutes
import infinitum.com.templates.configureImageSearchRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
        watchPaths = listOf("src", "build"))
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureSockets()
    configureArticleRoutes()
    configureImageSearchRoutes()
}

package infinitum.com.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LocalResponse(
    val generated: String
)

private const val url = "http://localhost:4000"
private const val textGenerationUrl = "$url/ai/generate"
private const val imageGenerationUrl = "$url/ai/generate/image"

val localClient = HttpClient(CIO) {
    engine {
        requestTimeout = Long.MAX_VALUE
    }
    install(ContentNegotiation) {
        serialization(
            contentType = ContentType.Application.Json,
            format = Json {
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = false
                useArrayPolymorphism = false
                ignoreUnknownKeys = true
            }
        )

    }
}

suspend fun generateLocalArticleLinks(): Result<String> {
    return runCatching {
        val response = localClient.post(textGenerationUrl) {
            headers {
                header("Content-type", "application/json")
            }
            setBody(
                mapOf("text" to buildCoolArticlesPrompts())
            )
        }

        val body: LocalResponse = response.body()
        body.generated
    }
}

suspend fun generateLocalArticle(topic: String, writer: String? = null): Result<String> {
    return runCatching {
        val response = localClient.post(textGenerationUrl) {
            headers {
                header("Content-type", "application/json")
            }
            setBody(
                mapOf("text" to buildPromptForTopic(topic, writer))
            )
        }

        val body: LocalResponse = response.body()
        body.generated
    }
}

suspend fun generateLocalImage(description: String): Result<ByteArray> {
    return runCatching {
        val response = localClient.post(imageGenerationUrl) {
            headers {
                header("Content-type", "application/json")
            }
            setBody(
                mapOf("text" to description)
            )
            retry {
                retryOnServerErrors(4)
            }
        }

        val body: ByteArray = response.body()
        body
    }
}
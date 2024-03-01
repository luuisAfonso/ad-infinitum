package infinitum.com.components

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import kotlin.random.Random

suspend fun PipelineContext<Unit, ApplicationCall>.respondComponent(block: HtmlBlockTag.() -> Unit) {
    call.respondHtml {
        body {
            block()
        }
    }
}

fun HtmlBlockTag.link(string: String) = a {
    classes = setOf("underline")
    href = string
    +string
}

fun HtmlBlockTag.adInfinitumGeneratedImage(description: String) = div {
    classes = setOf("mt-16", "flex", "flex-1", "justify-center", "center-items")
    attributes["hx-get"]="/replace/image/$description"
    adInfinitumImage(description)
}

fun HtmlBlockTag.adInfinitumImage(description: String) = img {
    src = "/image/$description.png?r=${Random.nextInt()}"
    alt = description
}

fun HtmlBlockTag.adInfinitumHeader() = header {
    classes = setOf(
        "border-solid",
        "border-b-2",
        "bg-neutral-950",
        "*:bg-neutral-950",
        "py-4"
    )
    h1 {
        classes = setOf(
            "bg-black",
            "font-extrabold",
            "text-5xl",
            "mb-2",
            "mx-16",
        )
        +"AD INFINITUM"
    }
}

fun HtmlBlockTag.adInfinitumArticle(title: String, content: String, writer: String? = null) = article {
    classes = setOf("p-4")
    h2 {
        classes = setOf("text-4xl", "font-medium")
        +title
    }


    ElementParser.createContent(content)
        .map { (type, content) ->
            when (type) {
                ElementParser.AdinfinitumElements.PARAGRAPH_ELEMENT -> adInfinitumParagraph(content)
                ElementParser.AdinfinitumElements.IMAGE_ELEMENT -> adInfinitumGeneratedImage(content)
            }
        }

    writer?.let {
        h5 {
            classes = setOf("text-2xl", "mt-4", "font-medium")
            +"Written by $it"
        }
    }
}

fun HtmlBlockTag.adInfinitumParagraph(content: String) = p {
    val textThenLink = content
        .replace("[/link]", "[link]", ignoreCase = true)
        .split("[link]")

    classes = setOf("mt-10", "text-1xl", "tracking-wider")

    textThenLink.mapIndexed { index: Int, s: String ->
        when (index % 2) {
            0 -> +s
            else -> link(s)
        }
    }
}

fun HtmlBlockTag.inputComponent() {
    return input {
        classes= setOf("text-black", "m-4", "p-2")
        type = InputType.search
        name = "search"
        attributes["hx-get"] = "/imageSearch/search"
        attributes["hx-trigger"] = "keyup changed delay:500ms, search"
        attributes["hx-target"] = "#search-result"
    }
}

fun HtmlBlockTag.searchResultContainer() {
    return div {
        id="search-result"
        classes=setOf("flex", "wrap", "gap-4", "flex-1", "flex-wrap")
    }
}
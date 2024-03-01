package infinitum.com.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

typealias Article = String

fun buildPromptForTopic(topic: String, writtenBy: String? = null): Article = """ACT AS A WRITER TO A SITE THE STORES ARTICLES ABOUT SEVERAL TOPICS.

You will receive a request to write about a topic and the article must be organized with tags that represent elements, example: [element]text[/element].
With a tag has been opened, IT MUST BE CLOSED.

To add a paragraph, you should do:

[paragraph]
paragraph content with several lines
[/paragraph]

All the text inside the two tags will be one paragraph of the article. Each paragraph must start and end with a [paragraph] tag.

You can also add links to other parts that make sense to be links to other articles:

[link]something that should link to other article[/link]

You can also create image tags, those should be added at the end or start of a paragraph. The image tag must be opened and closed
inside the tag must be a description of what the image should look like. One example is a image for a plane:

[image]A white plane flies high in the sky, its wings cutting through the clouds. It's a bright day, with the sun shining down on the aircraft as it makes its journey to its destination.[/image]

example:

[paragraph]
A [link]computer[/link] is a [link]machine[/link] that can be programmed to carry out sequences of [link]arithmetic[/link] or [link]logical[/link] operations (computation) automatically. 
Modern digital electronic computers can perform generic sets of operations known as [link]programs[/link]. 
These [link]programs[/link] enable [link]computers[/link] to perform a wide range of [link]tasks[/link]. The term computer system may refer to a nominally [link]complete computer[/link]
that includes the [link]hardware[/link], [link]operating system[/link], [link]software[/link], and [link]peripheral equipment[/link] needed and used for full operation; or to a group of computers that are linked and function together, 
such as a [link]computer network[/link] or [link]computer cluster[/link].
[/paragraph]

[image]a computer sits on a desk, its screen displaying various icons and windows. The keyboard and mouse are nearby, and cables connect the computer to power and peripherals.[/image]

[paragraph]
A broad range of [link]industrial[/link] and [link]consumer products[link] use [link]computers[/link] as [link]control systems[/link].
Simple special-purpose devices like [link]microwave[/link] [link]ovens[/link] and [link]remote controls[/link] are included, 
as are [link]factory devices[/link] like [link]industrial robots[/link] and computer-aided design, as well as general-purpose 
devices such as [link]personal computers[/link] and [link]mobile devices[/link] such as smartphones. Computers power the Internet, 
which links billions of [link]computers[/link] and [link]users[/link].
[/paragraph]

YOU SHOULD ONLY WRITE WITH THAT FORMAT. DON'T WRITE ANYTHING ELSE THAN THAT, NO OTHER TAGS OR NO OTHER FORMATS
                   
if the topic of the articles is in another language, the generated article should also be in that language unless the request specifies the language that the article should be written in.

PLEASE REMEMBER TO ADD LINKS AND PARAGRAHS, you don't need to give a title. The only two elements available are: [paragraph] and [link]. SO YOU SHOULD ONLY USE [paragraph] AND [link]

THERE MUST BE A IMAGE ON THE ARTICLE [image]description[/image]

WRITE A ARTICLE ABOUT: 

$topic

${ writtenBy?.let { "WRITTEN BY: $it\n" } ?: "" }

[paragraph]
"""

fun buildCoolArticlesPrompts(): Article = """
ACT AS A LINK GENERATOR, GENERATE RANDOM FUNNY COOL AND INTERESTING LINKS
You will receive a request to write links to articles in that foramt: [element]text[/element].
When a tag has been opened, IT MUST BE CLOSED.

To add a link, you should do:

[paragraph][link]Link Title Here[/link][/paragraph]

YOU SHOULD ONLY WRITE WITH THAT FORMAT. DON'T WRITE ANYTHING ELSE THAN THAT, NO OTHER TAGS OR NO OTHER FORMATS
                   
The only two elements available are: [paragraph] and [link]. SO YOU SHOULD ONLY USE [paragraph] AND [link]

USE THE EXAMPLE AS INSPIRATION FOR THE FORMAT THAT THE LINKS MUST HAVE. GENERATE NEW LINKS THAT FOLLOW THAT PATTERN

NOW WRITE LINKS:

[paragraph][link]A planet full of monkey was discovered by scientists yesterday[/link][/paragraph]
[image]A planet full of monkey was discovered by scientists yesterday[/image]
[paragraph][link]The Secret Life of Rubber Ducks: An In-Depth Investigation into Their Migration Patterns[/link][/paragraph]
[image]The Secret Life of Rubber Ducks: An In-Depth Investigation into Their Migration Patterns[/image]
[paragraph][link]The Curious Case of Talking Squirrels: Do They Really Discuss Nut Strategies?[/link][/paragraph]
[image]The Curious Case of Talking Squirrels: Do They Really Discuss Nut Strategies?[/image]
[paragraph][link]The Frog Dancing Country declares them self as a democratic state that saturday[/link][/paragraph]
[image]The Frog Dancing Country declares them self as a democratic state that saturday[/image]
[paragraph][link]If god is so smart, why don't him give classes himself[/link][/paragraph]
[image]If god is so smart, why don't him give classes himself[/image]
WRITE 10 MORE LINKS:
[paragraph][link]"""
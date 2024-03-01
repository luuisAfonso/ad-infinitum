package infinitum.com.components

import infinitum.com.components.ElementParser.AdinfinitumElements.IMAGE_ELEMENT
import infinitum.com.components.ElementParser.AdinfinitumElements.PARAGRAPH_ELEMENT
import infinitum.com.components.ElementParser.BuildState.*

object ElementParser {
    private var buildState = NONE

    enum class AdinfinitumElements {
        PARAGRAPH_ELEMENT, IMAGE_ELEMENT
    }

    enum class BuildState {
        PARAGRAPH, IMAGE, NONE
    }

    private fun clearState() {
        buildState = NONE
    }

    fun createContent(content: String): List<Pair<AdinfinitumElements, String>> {
        clearState()

        return buildList {
            content
                .replace("[/", "[")
                .replace("[", " [")
                .replace("]", "] ")
                .split(" ")
                .filter { it != "" }.run {

                    val buildingElement = mutableListOf<String>()

                    fun buildAndClearElement(element: AdinfinitumElements) {
                        clearState()
                        add(element to buildingElement.joinToString(" "))
                        buildingElement.clear()
                    }

                    forEach { word ->
                        when (word) {
                            "[paragraph]" -> {
                                when (buildState) {
                                    PARAGRAPH -> {
                                        buildAndClearElement(PARAGRAPH_ELEMENT)
                                    }

                                    NONE -> {
                                        buildState = PARAGRAPH
                                    }

                                    IMAGE -> Unit
                                }
                            }

                            "[image]" -> {
                                when (buildState) {
                                    IMAGE -> {
                                        buildAndClearElement(IMAGE_ELEMENT)
                                    }

                                    NONE -> {
                                        buildState = IMAGE
                                    }

                                    PARAGRAPH -> Unit
                                }
                            }
                            "\n" -> {
                                when (buildState) {
                                    IMAGE -> buildAndClearElement(IMAGE_ELEMENT)
                                    PARAGRAPH -> buildingElement.add(word)
                                    NONE -> Unit
                                }
                            }
                            else -> {
                                buildingElement.add(word)
                            }
                        }
                    }

                    if (buildingElement.size > 0) {
                        when(buildState) {
                            PARAGRAPH -> buildAndClearElement(PARAGRAPH_ELEMENT)
                            IMAGE -> buildAndClearElement(IMAGE_ELEMENT)
                            NONE -> buildAndClearElement(PARAGRAPH_ELEMENT)
                        }
                    }
                }
        }
    }
}
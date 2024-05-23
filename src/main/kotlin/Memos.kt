import kotlin.properties.Delegates

class Memos {
    private var navigation = mutableListOf<Screen>(MainScreen())
    private var archiveList = mutableListOf<Archive>()

    private var archiveIndex: Int = -1

    fun run() {
        while (navigation.isNotEmpty()) {
            val currentScreen = navigation.lastOrNull() ?: return
            when (currentScreen) {
                is MainScreen -> currentScreen.fillScreen(archiveList)
                is SecondScreen -> Unit
                is NotesListScreen -> Unit
                is ShowNoteScreen -> Unit
            }

            currentScreen.navigate(
                nextScreen = doOnNextScreen(currentScreen),
                onCreate = {
                    when (currentScreen) {
                        is MainScreen -> {
                            val archive: Archive = NotepadDispatcher.makeNewArchive()
                            archiveList = archiveList.apply {
                                add(archive)
                            }
                        }

                        is SecondScreen -> {
                            val note = NotepadDispatcher.makeNewNote()
                            archiveList.getOrNull(archiveIndex)?.apply {
                                archiveNotes.apply {
                                    addLast(note)
                                }
                            }
                        }

                        is NotesListScreen -> Unit
                        is ShowNoteScreen -> Unit
                    }
                },
                onExit = {
                    navigation = navigation.apply {
                        removeLast()
                    }
                },
                onError = {
                    println(it)
                },
            )
        }
        println("Выход из программы")
    }

    private fun doOnNextScreen(currentScreen: Screen) = { pointer: Int? ->
        navigation = when (currentScreen) {
            is MainScreen -> {
//                println("${pointer?.let { it1 -> archiveList.get(it1) }}")

                navigation.apply {
                    val screen = SecondScreen()
                    val p = pointer ?: return@apply
                    screen.fillScreen(archiveList[p])
                    archiveIndex = p
                    add(screen)
                }
            }

            is SecondScreen -> {
                navigation.apply {
                    val screen = NotesListScreen()
                    screen.fillScreen(archiveList[archiveIndex])
                    add(screen)
                }
            }

            is NotesListScreen -> {
                navigation.apply {
                    val screen = ShowNoteScreen()
                    val p = pointer ?: return@apply
                    screen.fillScreen(archiveList[archiveIndex].archiveNotes[pointer])
                    add(screen)
                }
            }
            is ShowNoteScreen -> navigation
        }

    }


}


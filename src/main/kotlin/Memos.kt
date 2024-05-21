import kotlin.properties.Delegates

class Memos {
    private var navigation = mutableListOf<Screen>(MainScreen())
    private var archiveList = mutableListOf<Archive>()

    //    private var notesMap = mapOf<Archive, List<Notes>>()
//    private var notesMap = archiveList.map { it.archiveName to it.archiveNotes }.toMap()
    private var archiveIndex: Int = -1

    fun run() {
        while (navigation.isNotEmpty()) {
            val currentScreen = navigation.lastOrNull() ?: return
            when (currentScreen) {
                is MainScreen -> currentScreen.fillScreen(archiveList)
                is SecondScreen -> Unit
            }

            currentScreen.navigate(
                nextScreen = doOnNextScreen(currentScreen),
                onCreate = {
                    when (currentScreen) {
                        is MainScreen -> {
                            val archive = NotepadDispatcher.makeNewArchive()
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
                println("${pointer?.let { it1 -> archiveList.get(it1) }}")

                navigation.apply {
                    val screen = SecondScreen()
                    val p = pointer ?: return@apply
                    screen.fillScreen(archiveList[p])
                    archiveIndex = p
                    add(screen)
                }

            }

            is SecondScreen -> {
                println("*** ТУТ ПОКА ЗАГЛУШКА ***")
                navigation
            }

            else -> navigation
        }


    }

//    private fun doOnCreate(currentScreen: Screen) = { pointer: Int? ->
//        when(currentScreen) {
//            is MainScreen -> {
//                val archive = NotepadDispatcher.makeNewArchive()
//                archiveList = archiveList.apply {
//                    add(archive)
//                }
//            }
//            is SecondScreen -> {
//                val note = NotepadDispatcher.makeNewNote()
//                val p = pointer ?: -1
//                archiveList.getOrNull(p)?.apply {
//                    val list = archiveNotes.addLast(note)
////                    copy(archiveNotes = list)
//                }
//            }
//        }
//
//    }


}


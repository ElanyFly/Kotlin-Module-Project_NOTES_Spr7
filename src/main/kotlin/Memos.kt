import kotlin.properties.Delegates

class Memos {
    private var navigation = listOf<Screen>(MainScreen())
    private var archiveList = listOf<Archive>()
    private var notesMap = mapOf<Archive, List<Notes>>()

    fun run() {
        while (navigation.isNotEmpty()) {
            val currentScreen = navigation.lastOrNull() ?: return
            currentScreen.fillScreen(archiveList)
            currentScreen.navigate(
                nextScreen = {
                             println(archiveList.get(it ?: 0))
                },
                onCreate = {
                    val archive = NotepadDispatcher.makeNewArchive()
                    archiveList = archiveList.toMutableList().apply {
                        add(archive)
                    }.toList()
                },
                onExit = {
                    navigation = navigation.toMutableList().apply {
                        removeLast()
                    }.toList()
                },
                onError = {
                    println(it)
                },
            )

        }
        println("Выход из программы")
    }

}

class MainScreen : Screen() {
    private var archiveList = listOf<Archive>()
    override fun fillScreen(list: List<Any>) {
        archiveList = list.filterIsInstance<Archive>()

    }

    override fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    ) {
        val onExitOption = archiveList.size + 2
        println("1. Создать архив")
        archiveList.forEachIndexed { position, archive ->
            println("${position + 2}. ${archive.archiveName}")
        }
        println("$onExitOption. Выход")
        val archiveOptions = if (archiveList.isNotEmpty()) {
            2..archiveList.size+2
        } else {
            Int.MIN_VALUE.. Int.MIN_VALUE
        }
        val option = NotepadDispatcher.callOption("Введите exit")
        when (option) {
            1 -> onCreate.invoke()
            in archiveOptions -> nextScreen.invoke(option-2)
            onExitOption -> onExit.invoke()
            else -> onError.invoke("Такого номера меню нет.")
        }
    }
}
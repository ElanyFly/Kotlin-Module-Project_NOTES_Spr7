sealed class Screen: Startable, Navigatable {
    open val screenName: String? = null
}

interface Startable {
    fun fillScreen(obj: Any)
}

interface Navigatable {
    fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    )
}

class MainScreen : Screen() {
    override val screenName = "Меню: "
    private var archiveList = listOf<Archive>()
    override fun fillScreen(obj: Any) {
        if (obj !is List<*>) {
            return
        }
        archiveList = obj.filterIsInstance<Archive>()
    }

    override fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    ) {
        println(screenName)
        val onExitOption = archiveList.size + 2
        println("1. Создать архив")

        archiveList.forEachIndexed { position, archive ->
            println("${position + 2}. ${archive.archiveName}")
        }

        val archiveOptions = if (archiveList.isNotEmpty()) {
            2..archiveList.size + 1
        } else {
            Int.MIN_VALUE..Int.MIN_VALUE
        }

        println("$onExitOption. Выход")

        when (val option = NotepadDispatcher.callOption("Введите номер меню: ")) {
            1 -> onCreate.invoke()
            in archiveOptions -> nextScreen.invoke(option - 2)
            onExitOption -> onExit.invoke()
            else -> onError.invoke("Такого номера меню нет.")
        }
    }
}

class SecondScreen : Screen() {
    private var currentArchive: Archive? = null
    override val screenName by lazy { "* Архив ${currentArchive?.archiveName} *" } //делегат - значение создастся, когда будет произведен первый вызов.

    override fun fillScreen(obj: Any) {
        currentArchive = obj as? Archive //если архив, то обжект не налл, если что-то другое, то налл.
    }


    override fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    ) {

        println(screenName)
        println("1. Создать заметку")
        println("2. Выбрать заметку")
        println("3. Назад")

        when (NotepadDispatcher.callOption("Введите номер меню: ")) {
            1 -> onCreate.invoke()
            2 -> nextScreen.invoke(2)
            3 -> onExit.invoke()
            else -> onError.invoke("Такого номера меню нет.")
        }

    }

}
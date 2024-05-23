sealed class Screen : Startable, Navigatable {
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
    override val screenName = "* Меню *"
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

        println("1. ${Menu.NEW_ARCHIVE.text}")

        archiveList.forEachIndexed { position, archive ->
            println("${position + 2}. ${archive.archiveName}")
        }

        val archiveOptions = if (archiveList.isNotEmpty()) {
            2..archiveList.size + 1
        } else {
            Int.MIN_VALUE..Int.MIN_VALUE
        }

        println("$onExitOption. ${Menu.EXIT.text}")

        when (val option = NotepadDispatcher.callOption(Message.INSERT_NUMBER.text)) {
            1 -> onCreate.invoke()
            in archiveOptions -> nextScreen.invoke(option - 2)
            onExitOption -> onExit.invoke()
            else -> onError.invoke(Message.ERROR_MESSAGE.text)
        }
    }
}

class SecondScreen : Screen() {
    private var currentArchive: Archive? = null
    override val screenName by lazy { "\n* Архив ${currentArchive?.archiveName} *" } //делегат - значение создастся, когда будет произведен первый вызов.

    override fun fillScreen(obj: Any) {
        currentArchive =
            obj as? Archive //если архив, то обжект не налл, если что-то другое, то налл.
    }


    override fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    ) {

        println(screenName)
        println("1. ${Menu.NEW_NOTE.text}")
        println("2. ${Menu.CHOOSE_NOTE.text}")
        println("3. ${Menu.BACK.text}")

        when (NotepadDispatcher.callOption(Message.INSERT_NUMBER.text)) {
            1 -> onCreate.invoke()
            2 -> nextScreen.invoke(null)
            3 -> onExit.invoke()
            else -> onError.invoke(Message.ERROR_MESSAGE.text)
        }

    }

}

class NotesListScreen : Screen() {
    private var currentArchive: Archive? = null
    override val screenName = "\n* Список заметок *"

    private val notesList: MutableList<Notes>? by lazy { currentArchive?.archiveNotes }

    override fun fillScreen(obj: Any) {
        currentArchive = obj as? Archive
    }

    override fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    ) {
        val onExitOption = notesList?.size?.plus(1)
        val notesListRange = notesList?.size ?: Int.MIN_VALUE

        println(screenName)

//        println("${currentArchive}")

        notesList?.forEachIndexed { position, noteName ->
            println("${position + 1}. ${noteName.heading}")
        }

        val notesOption = if (notesList?.isNotEmpty() == true) {
            1..notesListRange
        } else {
            Int.MIN_VALUE..Int.MIN_VALUE
        }

        println("$onExitOption. ${Menu.BACK.text}")

        val option = NotepadDispatcher.callOption(Message.INSERT_NUMBER.text)
        when (option) {
            in notesOption -> nextScreen.invoke(option-1)
            onExitOption -> onExit.invoke()
            else -> onError.invoke(Message.ERROR_MESSAGE.text)
        }
    }
}

class ShowNoteScreen : Screen() {
    private var thisNote: Notes? = null
    override val screenName = "\n* Заметка *"
    override fun fillScreen(obj: Any) {
        thisNote = obj as? Notes
    }

    override fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    ) {
        val contentList: List<String>? = thisNote?.content?.chunked(35)

        println(screenName)

        println("Заголовок: ${thisNote?.heading}")
        println("***\t***")
        println("Содержание заметки: ")

        println("${thisNote?.content}")

        println("1. ${Menu.BACK.text}")

        val option = NotepadDispatcher.callOption(Message.INSERT_NUMBER.text)
        when(option) {
            1 -> onExit.invoke()
            else -> onError.invoke(Message.ERROR_MESSAGE.text)
        }
    }

}
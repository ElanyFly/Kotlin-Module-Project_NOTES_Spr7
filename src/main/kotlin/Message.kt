enum class Message(val text: String) {
    ERROR_MESSAGE("Такого номера меню нет."),
    INSERT_NUMBER("Введите номер меню: "),
    NEW_ARCHIVE(""),
    NEW_HEADER(""),
    ADD_CONTENT(""),
    EMPTY_FIELD("")
}

enum class Menu(val text: String) {
    BACK("Назад"),
    EXIT("Выход"),
    NEW_ARCHIVE("Создать архив"),
    NEW_NOTE("Создать заметку"),
    CHOOSE_NOTE("Выбрать заметку")
}

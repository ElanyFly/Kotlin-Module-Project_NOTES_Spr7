enum class Message(val text: String) {
    ERROR_MESSAGE("Такого номера меню нет."),
    INSERT_NUMBER("Введите номер меню: "),
    NEW_ARCHIVE("Введите название архива:"),
    NEW_HEADER("Введите заголовок:"),
    ADD_CONTENT("Введите текст заметки:"),
    EMPTY_FIELD("Поле не может быть пустым"),
    WRONG_INPUT("Неверный ввод -"),
    EXIT_PROGRAM("Завершение работы программы")
}

enum class Menu(val text: String) {
    BACK("Назад"),
    EXIT("Выход"),
    NEW_ARCHIVE("Создать архив"),
    NEW_NOTE("Создать заметку"),
    CHOOSE_NOTE("Выбрать заметку")
}

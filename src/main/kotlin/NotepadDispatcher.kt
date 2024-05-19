import java.util.Scanner

object NotepadDispatcher {  //не создавать инстанс, а напрямую обращаться к нему

    val scanInput = Scanner(System.`in`)

    fun callOption(message: String): Int {
        var option: Int? = null
        while (option == null) {
            println(message)
            val result = runCatching {
                scanInput.nextInt()
            }
            if (result.isFailure) {
                println("Неверный ввод - ${scanInput.next()}.")
            }

            option = result.getOrNull()
        }
        return option
    }

    fun makeNewArchive(): Archive {
        var newArchive = callInput("Введите название архива: ")
        newArchive = checkStringEmpty(newArchive)
        return Archive(newArchive)
    }

    fun makeNewNote(): Notes {
        var header = callInput("Введите заголовок: ")
        header = checkStringEmpty(header)

        var content = callInput("Введите текст заметки: ")
        content = checkStringEmpty(content)

        return Notes(header, content)
    }


    private fun callInput(message: String): String {
        println(message)
        val scanInput = scanInput.nextLine()
        return scanInput.trim()
    }

    private fun checkStringEmpty(input: String): String {
        var scannerInput = input
        while (scannerInput.isEmpty()) {
            println("Поле не может быть пустым")  //нужно вывести заголовок после каждой неверной попытки
            scannerInput = scanInput.nextLine().trim()
        }
        return scannerInput
    }


}
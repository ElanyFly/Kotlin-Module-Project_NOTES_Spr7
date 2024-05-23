import java.util.Scanner

object NotepadDispatcher {  //не создавать инстанс, а напрямую обращаться к нему

    private val scanInput = Scanner(System.`in`)

    fun callOption(message: String): Int {
        var option: Int? = null
        while (option == null) {
            println(message)
            val result = runCatching {
                scanInput.nextInt()
            }
            if (result.isFailure) {
                println("${Message.WRONG_INPUT.text} ${scanInput.next()}.")
            }

            option = result.getOrNull()
        }
        scanInput.nextLine()
        return option
    }

    fun makeNewArchive(): Archive {
        var newArchive = callInput(Message.NEW_ARCHIVE.text)
        newArchive = checkStringEmpty(newArchive)

        return Archive(newArchive)
    }

    fun makeNewNote(): Notes {
        var header = callInput(Message.NEW_HEADER.text)
        header = checkStringEmpty(header)

        var content = callInput(Message.ADD_CONTENT.text)
        content = checkStringEmpty(content)

        return Notes(header, content)
    }

    private fun callInput(message: String): String {
        println(message)
        val input = scanInput.nextLine()
        return input.trim()
    }

    private fun checkStringEmpty(input: String): String {
        var scannerInput = input
        while (scannerInput.isEmpty()) {
            println(Message.EMPTY_FIELD.text)
            scannerInput = scanInput.nextLine().trim()
        }
        return scannerInput
    }
}
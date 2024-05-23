import java.util.Scanner

object NotepadDispatcher {  //не создавать инстанс, а напрямую обращаться к нему

    private val scanInput = Scanner(System.`in`)

    fun callOption(message: String): Int {
        var option: Int? = null
        while (option == null) {
            println(message)
            val result = runCatching {
//                readlnOrNull()?.toIntOrNull()
                scanInput.nextInt()
            }
            if (result.isFailure) {
                println("Неверный ввод - ${scanInput.next()}.")
            }

            option = result.getOrNull()
        }
        scanInput.nextLine()
        return option
    }

    fun makeNewArchive(): Archive {
        var newArchive = callInput("\nВведите название архива:")
        newArchive = checkStringEmpty(newArchive)

        return Archive(newArchive)
    }

    fun makeNewNote(): Notes {
        var header = callInput("\nВведите заголовок:")
        header = checkStringEmpty(header)

        var content = callInput("\nВведите текст заметки:")
        content = checkStringEmpty(content)

        return Notes(header, content)
    }


     fun callInput(message: String): String {
        println(message)
         clearInput()
        val input = scanInput.nextLine()
        return input.trim()
    }

    fun clearInput(){
       /* if (scanInput.hasNext()){
            scanInput.nextLine()
        }*/
    }

     fun checkStringEmpty(input: String): String {
        var scannerInput = input
        while (scannerInput.isEmpty()) {
            println("Поле не может быть пустым")
            scannerInput = scanInput.nextLine().trim()
        }
        return scannerInput
    }
}
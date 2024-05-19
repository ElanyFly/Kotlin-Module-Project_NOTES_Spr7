/*
import java.util.Scanner

class MenuList(
    val menuName: String = "",
    action: (() -> Unit)? = null
) {}


class Menu: Screen() {
    val menuList: MutableList<MenuList> = mutableListOf(
        MenuList("Создать архив"),
        MenuList("Выход")
    )
    var isRun = true

    val archiveList: MutableList<Archive> = mutableListOf()

    fun addArchiveToList(archive: Archive) {
        archiveList.add(archive)
    }

    fun startMenu() {

        println("Выберите действие (номер):")
        while (isRun) {
//            showStartMenu()

            for (i in menuList.indices) {
                println("$i ${menuList[i].menuName}")
//                for (j in archiveList.indices) {
//                    println("$i ${archiveList[j].archiveName}")
//                }
            }

            val scanInput = Scanner(System.`in`).nextInt()



            when(scanInput) {
                0 -> addArchiveToList(NotepadDispatcher.makeNewArchive())
                else -> println("sgdg")
            }

        }
    }

//    private fun showStartMenu() {
//        var menuNumber = 0
//        for (i in menuList)
//
//
////        menuList.forEach {
////            println("${menuNumber++} ${it.menuName}")
////        }
//    }
}

fun main() {
    val menu: Menu = Menu()

    menu.startMenu()

}

enum class menuCommands(command: String) {
    NEW_ARCHIVE("Создать архив"),
    EXIT("Выход")
}*/

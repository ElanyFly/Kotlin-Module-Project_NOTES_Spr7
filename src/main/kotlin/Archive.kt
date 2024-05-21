
data class Archive (
    val archiveName: String,
    val archiveNotes: MutableList<Notes> = mutableListOf()

)

//fun main() {
//
//    val arch = Archive(
//        ""
//    )
//    var list = arch.archiveNotes
//    list = list.toMutableList().apply {
//        add(Notes("", ""))
//    }.toList()
//    val newArch = arch.copy(
//        archiveNotes = list
//    )
//    newArch
//}
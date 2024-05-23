data class Archive(
    val archiveName: String,
    val archiveNotes: MutableList<Notes> = mutableListOf()
)
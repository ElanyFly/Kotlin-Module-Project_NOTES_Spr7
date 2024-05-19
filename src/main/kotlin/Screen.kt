abstract class Screen(
    val screenName: String? = null
): Startable, Navigatable {

}

interface Startable {
    fun fillScreen(list: List<Any>)
}

interface Navigatable {
    fun navigate(
        nextScreen: (Int?) -> Unit,
        onCreate: () -> Unit,
        onExit: () -> Unit,
        onError: (String) -> Unit
    )
}
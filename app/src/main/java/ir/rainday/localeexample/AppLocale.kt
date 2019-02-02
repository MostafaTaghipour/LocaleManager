package ir.rainday.localeexample

enum class AppLocale(val value: Int) {
    System(0),
    English(1),
    Persian(2);


    val title: Int
        get() {
            return when (this) {
                System -> R.string.system_language
                English -> R.string.english
                Persian -> R.string.persian
            }
        }

    val lang: String
        get() {
            return when (this) {
                English -> "en"
                Persian -> "fa"
                else -> ""
            }
        }

    override fun toString(): String {
        return MyApp.appContext.getString(title)
    }

    companion object {
        fun fromValue(value: Int): AppLocale {
            return AppLocale.values().first { it.value == value }
        }
    }
}



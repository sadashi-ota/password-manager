package jp.sadashi.manager.password

enum class PasswordManagerScreen {
    List,
    Detail,
    ;

    companion object {
        fun fromRoute(route: String?): PasswordManagerScreen =
            when (route?.substringBefore("/")) {
                List.name -> List
                Detail.name -> Detail
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
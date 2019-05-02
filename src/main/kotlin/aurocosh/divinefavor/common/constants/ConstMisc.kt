package aurocosh.divinefavor.common.constants

object ConstMisc {
    // Mod Constants
    const val MOD_ID = "divinefavor"
    const val MOD_NAME = "Divine Favor"
    const val BUILD = "GRADLE:BUILD"
    const val VERSION = "GRADLE:VERSION-$BUILD"
    const val PREFIX_CONF = "$MOD_ID."
    const val BETA_TESTING = false

    const val KEYBIND_CATEGORY = "key.categories." + MOD_ID

    // Proxy Constants
    const val PROXY_COMMON = "aurocosh.divinefavor.common.core.proxy.CommonProxy"
    const val PROXY_CLIENT = "aurocosh.divinefavor.client.core.proxy.ClientProxy"

    val logging = PREFIX_CONF + "logging"
    val content = PREFIX_CONF + "content"
    const val SQ = 18
}

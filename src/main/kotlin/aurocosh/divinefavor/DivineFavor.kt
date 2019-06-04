package aurocosh.divinefavor

import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab
import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTabArrowTalismans
import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTabGems
import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTabSpellTalismans
import aurocosh.divinefavor.common.core.proxy.CommonProxy
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModContainer
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_NAME, dependencies = "required-after:patchouli;required:forgelin", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object DivineFavor {
    // Mod Constants
    const val MOD_ID = "divinefavor"
    const val MOD_NAME = "Divine Favor"

    const val KEYBIND_CATEGORY = "key.categories." + MOD_ID

    // Proxy Constants
    const val PROXY_COMMON = "aurocosh.divinefavor.common.core.proxy.CommonProxy"
    const val PROXY_CLIENT = "aurocosh.divinefavor.client.core.proxy.ClientProxy"

    @Mod.Instance(DivineFavor.MOD_ID)
    var instance: DivineFavor? = null

    @SidedProxy(serverSide = DivineFavor.PROXY_COMMON, clientSide = DivineFavor.PROXY_CLIENT)
    lateinit var proxy: CommonProxy

    lateinit var logger: Logger
    lateinit var container: ModContainer
    lateinit var TAB_MAIN: DivineFavorCreativeTab
    lateinit var TAB_GEMS: DivineFavorCreativeTabGems
    lateinit var TAB_ARROW_TALISMANS: DivineFavorCreativeTabArrowTalismans
    lateinit var TAB_SPELL_TALISMANS: DivineFavorCreativeTabSpellTalismans

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = LogManager.getLogger()
        container = Loader.instance().modObjectList.inverse()[instance]!!

        TAB_MAIN = DivineFavorCreativeTab()
        TAB_GEMS = DivineFavorCreativeTabGems()
        TAB_ARROW_TALISMANS = DivineFavorCreativeTabArrowTalismans()
        TAB_SPELL_TALISMANS = DivineFavorCreativeTabSpellTalismans()

        proxy.preInit(event)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        proxy.init(event)
    }

    @Mod.EventHandler
    fun serverStartingEvent(@Suppress("UNUSED_PARAMETER") event: FMLServerStartingEvent) {

    }
}
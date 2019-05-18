package aurocosh.divinefavor

import aurocosh.divinefavor.common.constants.ConstMisc
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
import org.apache.logging.log4j.Logger

@Mod(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_NAME, version = ConstMisc.VERSION, dependencies = "required-after:patchouli;required:forgelin", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object DivineFavor {
    @Mod.Instance(ConstMisc.MOD_ID)
    var instance: DivineFavor? = null

    @SidedProxy(serverSide = ConstMisc.PROXY_COMMON, clientSide = ConstMisc.PROXY_CLIENT)
    lateinit var proxy: CommonProxy

    lateinit var logger: Logger
    lateinit var container: ModContainer
    lateinit var TAB_MAIN: DivineFavorCreativeTab
    lateinit var TAB_GEMS: DivineFavorCreativeTabGems
    lateinit var TAB_ARROW_TALISMANS: DivineFavorCreativeTabArrowTalismans
    lateinit var TAB_SPELL_TALISMANS: DivineFavorCreativeTabSpellTalismans

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
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
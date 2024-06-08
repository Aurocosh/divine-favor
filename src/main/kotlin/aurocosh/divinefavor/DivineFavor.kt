package aurocosh.divinefavor

import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab
import aurocosh.divinefavor.common.core.proxy.CommonProxy
import aurocosh.divinefavor.common.item.common.*
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ModContainer
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_NAME, dependencies = "required-after:patchouli;required:forgelin_continuous;required:autonetworklib", modLanguageAdapter = "io.github.chaosunity.forgelin.KotlinAdapter")
//@Mod(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_NAME, dependencies = "required-after:patchouli;required:forgelin;required:autonetworklib@[1.0.36,)", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object DivineFavor {
    // Mod Constants
    const val MOD_ID = "divinefavor"
    const val MOD_NAME = "Divine Favor"

    const val KEYBIND_CATEGORY = "key.categories.$MOD_ID"

    // Proxy Constants
    const val PROXY_COMMON = "aurocosh.divinefavor.common.core.proxy.CommonProxy"
    const val PROXY_CLIENT = "aurocosh.divinefavor.client.core.proxy.ClientProxy"

    @Mod.Instance(MOD_ID)
    var instance: DivineFavor? = null

    @SidedProxy(serverSide = PROXY_COMMON, clientSide = PROXY_CLIENT)
    lateinit var proxy: CommonProxy

    lateinit var logger: Logger
    lateinit var container: ModContainer
    lateinit var TAB_MAIN: DivineFavorCreativeTab
    lateinit var TAB_GEMS: DivineFavorCreativeTab
    lateinit var TAB_ARROW_TALISMANS: DivineFavorCreativeTab
    lateinit var TAB_BLADE_TALISMANS: DivineFavorCreativeTab
    lateinit var TAB_TOOL_TALISMANS: DivineFavorCreativeTab
    lateinit var TAB_SPELL_TALISMANS: DivineFavorCreativeTab

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = LogManager.getLogger()
        container = Loader.instance().modObjectList.inverse()[instance] as ModContainer

        TAB_MAIN = DivineFavorCreativeTab("main") { ModItems.grimoire_red }
        TAB_GEMS = DivineFavorCreativeTab("gems") { ModCallingStones.calling_stone_neblaze }
        TAB_ARROW_TALISMANS = DivineFavorCreativeTab("arrow_talismans") { ModArrowTalismans.fill_lungs }
        TAB_BLADE_TALISMANS = DivineFavorCreativeTab("blade_talismans") { ModBladeTalismans.rain_sword }
        TAB_TOOL_TALISMANS = DivineFavorCreativeTab("tool_talismans") { ModToolTalismans.break_radius }
        TAB_SPELL_TALISMANS = DivineFavorCreativeTab("spell_talismans") { ModSpellTalismans.wild_sprint }

        proxy.preInit(event)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        proxy.init(event)
    }


    @Mod.EventHandler
    fun init(event: FMLPostInitializationEvent) {
        proxy.postInit(event)
    }

    @Mod.EventHandler
    fun serverStartingEvent(@Suppress("UNUSED_PARAMETER") event: FMLServerStartingEvent) {
    }
}
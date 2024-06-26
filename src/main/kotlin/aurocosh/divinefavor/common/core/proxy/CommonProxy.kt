package aurocosh.divinefavor.common.core.proxy

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.custom_data.living.capability.LivingDataDataHandler
import aurocosh.divinefavor.common.custom_data.player.capability.PlayerDataDataHandler
import aurocosh.divinefavor.common.custom_data.world.capability.WorldDataDataHandler
import aurocosh.divinefavor.common.entity.ModEntities
import aurocosh.divinefavor.common.item.common.*
import aurocosh.divinefavor.common.item.contract.ModContracts
import aurocosh.divinefavor.common.item.gem_pouch.capability.GemPouchDataHandler
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireDataHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeDataHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.SpellBowDataHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability.SpellPickDataHandler
import aurocosh.divinefavor.common.muliblock.common.ModCustomMultiBlocks
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks
import aurocosh.divinefavor.common.network.GuiHandler
import aurocosh.divinefavor.common.network.ModBufSerializers
import aurocosh.divinefavor.common.network.NetworkHandler
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.receipes.RecipeLoader
import aurocosh.divinefavor.common.receipes.common.ModContactRecipes
import aurocosh.divinefavor.common.receipes.common.ModMediumRecipes
import aurocosh.divinefavor.common.receipes.common.ModRecipes
import aurocosh.divinefavor.common.spirit.ModSpirits
import com.google.common.util.concurrent.ListenableFuture
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.config.Config
import net.minecraftforge.common.config.ConfigManager
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
open class CommonProxy {

    open val clientPlayer: EntityPlayer
        get() = throw IllegalStateException("This should only be called from client side")

    open val hasClientPlayer: Boolean
        get() = throw IllegalStateException("This should only be called from client side")

    open fun preInit(event: FMLPreInitializationEvent) {
        ModBufSerializers.preInit()

        ModSpirits.preInit()
        ModCustomMultiBlocks.preInit(event)
        ModMultiBlocks.preInit()

        ModPotions.preInit()
        ModCurses.preInit()
        ModBlendEffects.preInit()
        ModBlessings.preInit()
        ModArrowTalismans.preInit()
        ModBladeTalismans.preInit()
        ModToolTalismans.preInit()
        ModItems.preInit()
        ModRecipes.preInit()
        ModRopes.preInit()
        ModSoulShards.preInit()
        ModSpellTalismans.preInit()
        ModBathingBlends.preInit()

        ModContracts.preInit()
        ModCallingStones.preInit()
        ModWishingStones.preInit()
        ModFavorMarks.preInit()

        ModBlocks.preInit()
        ModEntities.preInit()

        NetworkHandler.init()

        LivingDataDataHandler.register()
        PlayerDataDataHandler.register()
        WorldDataDataHandler.register()

        GemPouchDataHandler.register()
        GrimoireDataHandler.register()
        MemoryPouchDataHandler.register()
        SpellBladeDataHandler.register()
        SpellBowDataHandler.register()
        SpellPickDataHandler.register()

        NetworkRegistry.INSTANCE.registerGuiHandler(DivineFavor, GuiHandler())
    }

    open fun init(e: FMLInitializationEvent) {
        ModMultiBlocks.init()
        ModItems.init()
        ModSoulShards.init()
        ModBlocks.init()
        ModContactRecipes.init()
        ModMediumRecipes.init()
        RecipeLoader.init()
        ModBlessings.init()
    }

    fun postInit(@Suppress("UNUSED_PARAMETER") event: FMLPostInitializationEvent) {
        ModMediumRecipes.postInit()
    }

    open fun addScheduledTaskClient(runnableToSchedule: Runnable): ListenableFuture<Any> {
        throw IllegalStateException("This should only be called from client side")
    }

    companion object {

        @SubscribeEvent
        @JvmStatic
        fun onConfigChangedEvent(event: ConfigChangedEvent.OnConfigChangedEvent) {
            if (event.modID == DivineFavor.MOD_ID)
                ConfigManager.sync(DivineFavor.MOD_ID, Config.Type.INSTANCE)
        }
    }
}

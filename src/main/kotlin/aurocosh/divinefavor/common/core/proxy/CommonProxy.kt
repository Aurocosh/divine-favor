package aurocosh.divinefavor.common.core.proxy

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.custom_data.living.capability.LivingDataDataHandler
import aurocosh.divinefavor.common.custom_data.player.capability.PlayerDataDataHandler
import aurocosh.divinefavor.common.custom_data.world.capability.WorldDataDataHandler
import aurocosh.divinefavor.common.entity.ModEntities
import aurocosh.divinefavor.common.item.bathing_blend.ModBathingBlends
import aurocosh.divinefavor.common.item.gems.calling_stones.ModCallingStones
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.contract.ModContracts
import aurocosh.divinefavor.common.item.gems.favor_marks.ModFavorMarks
import aurocosh.divinefavor.common.item.gems.soul_shards.ModSoulShards
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireDataHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeDataHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.SpellBowDataHandler
import aurocosh.divinefavor.common.item.talismans.common.ModArrowTalismans
import aurocosh.divinefavor.common.item.talismans.spell.common.ModSpellTalismans
import aurocosh.divinefavor.common.item.gems.wishing_stones.ModWishingStones
import aurocosh.divinefavor.common.item.talismans.common.ModBladeTalismans
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks
import aurocosh.divinefavor.common.network.GuiHandler
import aurocosh.divinefavor.common.network.NetworkHandler
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.receipes.ModRecipes
import aurocosh.divinefavor.common.receipes.RecipeLoader
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
        ModSpirits.preInit()
        ModMultiBlocks.preInit()

        ModPotions.preInit()
        ModCurses.preInit()
        ModBlendEffects.preInit()
        ModBlessings.preInit()
        ModArrowTalismans.preInit()
        ModBladeTalismans.preInit()
        ModItems.preInit()
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

        SpellBladeDataHandler.register()
        SpellBowDataHandler.register()
        GrimoireDataHandler.register()

        NetworkRegistry.INSTANCE.registerGuiHandler(DivineFavor, GuiHandler())
    }

    open fun init(e: FMLInitializationEvent) {
        ModMultiBlocks.init()
        ModItems.init()
        ModBlocks.init()
        ModRecipes.init()
        RecipeLoader.init()
        ModBlessings.init()
    }

    fun postInit(@Suppress("UNUSED_PARAMETER") event: FMLPostInitializationEvent) {}

    open fun addScheduledTaskClient(runnableToSchedule: Runnable): ListenableFuture<Any> {
        throw IllegalStateException("This should only be called from client side")
    }

    companion object {

        @SubscribeEvent
        fun onConfigChangedEvent(event: ConfigChangedEvent.OnConfigChangedEvent) {
            if (event.modID == DivineFavor.MOD_ID)
                ConfigManager.sync(DivineFavor.MOD_ID, Config.Type.INSTANCE)
        }
    }
}

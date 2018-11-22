package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.block.tile.TileFastFurnace;
import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.lib.LibBlockNames;
import aurocosh.divinefavor.common.lib.LibEntityNames;
import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.MessageRegister;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        //ConfigHandler.init(event.getSuggestedConfigurationFile());

        ModItems.preInit();
        ModBlocks.preInit();
        ModEntities.init();
        ModSpells.init();
        //ModCraftingRecipes.init();

        MessageRegister.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(DivineFavor.instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new PlayerDataHandler.EventHandler());
    }

    public void init(FMLInitializationEvent e) {
        ModItems.init();
        ModBlocks.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public void onLevelUp(int level) {
        // proxy override
    }

    public void savePersistency() {
        // proxy override
    }
}

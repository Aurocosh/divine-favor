package aurocosh.divinefavor.common.core.proxy;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.entity.ModEntities;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.item.talisman.capability.TalismanDataHandler;
import aurocosh.divinefavor.common.muliblock.ModMultiBlocks;
import aurocosh.divinefavor.common.network.GuiHandler;
import aurocosh.divinefavor.common.network.base.MessageRegister;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import com.google.common.util.concurrent.ListenableFuture;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

    @FunctionalInterface
    public static interface Test<T extends Object> {
        void write(ByteBuf buf, T t);
    }

    public static interface Test2<T extends Object> {
        T read(ByteBuf buf);
    }
    public void preInit(FMLPreInitializationEvent e) {
        //UtilAssets.BufWriter();
        //UtilAssets.BufReader();

        TalismanDataHandler.register();

        ModSpirits.preInit();
        ModFavors.preInit();
        ModSpells.preInit();
        ModBlocks.preInit();
        ModMultiBlocks.preInit();
        ModItems.preInit();
        ModEntities.preInit();

        MessageRegister.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(DivineFavor.instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new PlayerDataHandler.EventHandler());
//
//        Test<Boolean> writeBool = ByteBuf::writeBoolean;
//        Test<Short> writeShort = ByteBuf::writeShort;
//
//        Test2<Boolean> readBool = ByteBuf::readBoolean;
//        Test2<Short> readShort = ByteBuf::readShort;
//
//        boolean b = true;
//        short s = 54;
//
//        PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
//        writeBool.write(packetbuffer,b);
//        writeShort.write(packetbuffer,s);
//
//        boolean b2 = readBool.read(packetbuffer);
//        short s2 = readShort.read(packetbuffer);





    }

    public void init(FMLInitializationEvent e) {
        ModMultiBlocks.init();
        ModItems.init();
        ModBlocks.init();
        ModRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }
    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}

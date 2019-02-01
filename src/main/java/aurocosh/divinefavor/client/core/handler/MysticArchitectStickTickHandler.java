package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.item.mystic_architect_stick.ArchitectStickMode;
import aurocosh.divinefavor.common.item.mystic_architect_stick.ItemMysticArchitectStick;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;


@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public class MysticArchitectStickTickHandler {
    @SubscribeEvent
    public static void onMouseEvent(MouseEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            return;
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (!player.isSneaking())
            return;
        int dWheel = event.getDwheel();
        if (dWheel == 0)
            return;

        ItemStack stack = player.getHeldItemMainhand();
        if(!(stack.getItem() instanceof ItemMysticArchitectStick))
            stack = player.getHeldItemOffhand();
        if(!(stack.getItem() instanceof ItemMysticArchitectStick))
            return;

        NBTTagCompound compound = UtilNbt.getNbt(stack);
        int mode = compound.getInteger(ItemMysticArchitectStick.TAG_CURRENT_MODE);
        if (dWheel < 0)
            mode--;
        else
            mode++;
        mode = ArchitectStickMode.clampModeIndex(mode);
        compound.setInteger(ItemMysticArchitectStick.TAG_CURRENT_MODE, mode);
        ArchitectStickMode stickMode = ArchitectStickMode.VALUES[mode];
        DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Selected mode: " + stickMode.getDescription()));
        event.setCanceled(true);
    }
}
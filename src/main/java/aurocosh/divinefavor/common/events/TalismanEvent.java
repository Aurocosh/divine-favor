package aurocosh.divinefavor.common.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class TalismanEvent extends Event {

    public final ItemStack itemStack;

    public TalismanEvent(ItemStack itemStack) {
        super();
        this.itemStack = itemStack;
    }

    @Cancelable
    public static class ExtraBlockBreak extends TalismanEvent {

        public final EntityPlayer player;
        public final IBlockState state;

        public int width;
        public int height;
        public int depth;
        public int distance;

        public ExtraBlockBreak(ItemStack itemStack, EntityPlayer player, IBlockState state) {
            super(itemStack);
            this.player = player;
            this.state = state;
        }

        public static ExtraBlockBreak fireEvent(ItemStack itemStack, EntityPlayer player, IBlockState state, int width, int height, int depth, int distance) {
            ExtraBlockBreak event = new ExtraBlockBreak(itemStack, player, state);
            event.width = width;
            event.height = height;
            event.depth = depth;
            event.distance = distance;

            MinecraftForge.EVENT_BUS.post(event);
            return event;
        }
    }
}
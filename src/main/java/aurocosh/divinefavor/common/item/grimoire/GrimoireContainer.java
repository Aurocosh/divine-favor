package aurocosh.divinefavor.common.item.grimoire;

import aurocosh.divinefavor.common.item.base.GenericContainer;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncGrimoireSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GrimoireContainer extends GenericContainer {
    private int blocked;
    private EnumHand hand;
    private EntityPlayer player;
    private IGrimoireHandler grimoireHandler;

    public GrimoireContainer(EntityPlayer player, IGrimoireHandler grimoireHandler, EnumHand hand) {
        this.hand = hand;
        this.player = player;
        this.grimoireHandler = grimoireHandler;

        int x = 8;
        int y = 18;
        int slotIndex = 0;
        // Add our own slots
        generateCustomSlotsGrid(grimoireHandler.getStackHandler(), x, y, 3, 9, slotIndex);

        generateInventorySlots(player.inventory, 8, 84);
        generateHotbarSlots(player.inventory, 8, 142);

        blocked = (inventorySlots.size() - 1) - (8 - player.inventory.currentItem);
    }

    @Override
    public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player) {
        assert player.world.isRemote;

        if (slot == blocked)
            return ItemStack.EMPTY;
        if(flag == ClickType.CLONE){
            grimoireHandler.setSelectedSlotIndex(slot);
            player.closeScreen();
            int playerSlot = hand == EnumHand.OFF_HAND ? 40 : player.inventory.currentItem;
            new MessageSyncGrimoireSlot(playerSlot,slot).send();
            return ItemStack.EMPTY;
        }

        return super.slotClick(slot, button, flag, player);
    }
}
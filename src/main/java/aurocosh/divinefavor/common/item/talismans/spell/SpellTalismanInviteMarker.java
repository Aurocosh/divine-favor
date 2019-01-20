package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.ItemInviteMarker;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SpellTalismanInviteMarker extends ItemSpellTalisman {
    private final ModItem item;

    public SpellTalismanInviteMarker(String name, int startingSpellUses, ModItem item) {
        super(name, startingSpellUses, true, true);
        this.item = item;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        ItemStack stack = new ItemStack(item);
        NBTTagCompound nbt = UtilNbt.getTag(stack);
        nbt.setString(ItemInviteMarker.TAG_PLAYER_UUID, player.getGameProfile().getId().toString());
        player.inventory.addItemStackToInventory(stack);
    }
}

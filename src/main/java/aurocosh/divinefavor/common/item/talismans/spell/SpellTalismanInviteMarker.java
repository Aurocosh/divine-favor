package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.ItemInviteMarker;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.EnumSet;

public class SpellTalismanInviteMarker extends ItemSpellTalisman {
    private final ModItem item;

    public SpellTalismanInviteMarker(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options, ModItem item) {
        super(name, spirit, favorCost, options);
        this.item = item;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        ItemStack stack = new ItemStack(item);
        NBTTagCompound nbt = UtilNbt.getNbt(stack);
        nbt.setString(ItemInviteMarker.TAG_PLAYER_UUID, player.getGameProfile().getId().toString());
        player.inventory.addItemStackToInventory(stack);
    }
}

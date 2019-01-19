package aurocosh.divinefavor.common.arrow_spell;

import aurocosh.divinefavor.common.arrow_spell.base.ArrowSpell;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;

public class SpellHandSwap extends ArrowSpell {
    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if(target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            ItemStack stackMain = player.getHeldItemMainhand();
            ItemStack stackOff = player.getHeldItemOffhand();
            player.inventory.setInventorySlotContents(player.inventory.currentItem,stackOff);
            player.inventory.setInventorySlotContents(InventoryIndexes.Offhand.getValue(),stackMain);
        }
    }
}

package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowSpell;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;

public class SpellBlinkArrow extends ArrowSpell {
    public SpellBlinkArrow() {
        super(false);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if (!(shooter instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) shooter;
        UtilEntity.teleport(player,arrow.getPosition());
    }
}

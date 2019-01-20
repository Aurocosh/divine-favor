package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;

import static aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk;

public class PotionLiquidWalking extends ModPotion {
    private final Block block;

    public PotionLiquidWalking(String name, Block block) {
        super(name, true, 0x7FB8A4);
        this.block = block;
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        tickLiquidWalk(livingBase, block);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}

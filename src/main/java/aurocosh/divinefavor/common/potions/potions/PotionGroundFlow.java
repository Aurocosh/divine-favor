package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.network.message.client.MessageSyncFlyingCapability;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class PotionGroundFlow extends ModPotionToggle {
    public PotionGroundFlow() {
        super("ground_flow", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        assert !livingBase.world.isRemote;

        BlockPos pos = livingBase.getPosition();
        EntityPlayer player = (EntityPlayer) livingBase;
        if(!player.capabilities.allowFlying && pos.getY() <= ConfigSpells.groundFlow.yLimit)
            player.capabilities.allowFlying = true;
        else if(player.capabilities.allowFlying && pos.getY() > ConfigSpells.groundFlow.yLimit) {
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            player.removePotionEffect(ModPotions.ground_flow);
            new MessageSyncFlyingCapability(false).sendTo(player);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}

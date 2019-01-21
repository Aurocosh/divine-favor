package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.custom_data.living.petrification.IPetrificationHandler;
import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;

import static aurocosh.divinefavor.common.custom_data.living.petrification.PetrificationDataHandler.CAPABILITY_PETRIFICATION;

@Mod.EventBusSubscriber
public class PotionPetrification extends ModPotion {
    public static final float DAMAGE = 4;

    public PotionPetrification() {
        super("petrification", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (livingBase instanceof EntityPlayer) {
            new MessagePetrificationReset().sendTo((EntityPlayer) livingBase);
        }
        else {
            IPetrificationHandler handler = livingBase.getCapability(CAPABILITY_PETRIFICATION, null);
            assert handler != null;
            handler.resetCureTimer();
        }
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase instanceof EntityPlayer)
            performForPlayer((EntityPlayer) livingBase);
        else
            performForMob(livingBase);
    }

    private void performForMob(EntityLivingBase notPlayer) {
        if (notPlayer.world.isRemote)
            return;
        IPetrificationHandler handler = notPlayer.getCapability(CAPABILITY_PETRIFICATION, null);
        assert handler != null;
        if (notPlayer.motionX == 0 && notPlayer.motionZ == 0) {
            if (handler.cureTick())
                notPlayer.removePotionEffect(ModCurses.petrification);
        }
        else if (handler.damageTick())
            notPlayer.attackEntityFrom(ModDamageSources.petrificationDamage, DAMAGE);
    }

    private void performForPlayer(EntityPlayer player) {
        if (!player.world.isRemote)
            return;
        IPetrificationHandler handler = player.getCapability(CAPABILITY_PETRIFICATION, null);
        assert handler != null;
        if (player.motionX == 0 && player.motionZ == 0) {
            if (handler.cureTick()) {
                player.removePotionEffect(ModCurses.petrification);
                new MessagePetrificationCure().send();
            }
        }
        else if (handler.damageTick())
            new MessagePetrificationDamage().send();
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}

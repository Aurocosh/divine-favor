package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.config.common.ConfigPunishments;
import aurocosh.divinefavor.common.config.data.DoubleInterval;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.red_fury.RedFuryData;
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncRedFury;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class PotionRedFury extends ModPotion {
    private static final DoubleInterval directionInterval = new DoubleInterval(-1, 1);

    public PotionRedFury() {
        super("red_fury", false, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);

        if (!(livingBase instanceof EntityPlayer)) {
            livingBase.removePotionEffect(ModCurses.red_fury);
            return;
        }
        EntityPlayer player = (EntityPlayer) livingBase;
        RedFuryData furyData = PlayerData.get(player).getRedFuryData();

        double x = directionInterval.random();
        double y = directionInterval.random();
        double z = directionInterval.random();
        Vec3d vector = new Vec3d(x, y, z).scale(ConfigPunishments.redwind.dragSpeed);

        furyData.setVector(vector);
        new MessageSyncRedFury(vector).sendTo(player);
        return;
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        EntityPlayer player = (EntityPlayer) livingBase;
        RedFuryData furyData = PlayerData.get(player).getRedFuryData();
        Vec3d vector = furyData.getVector();
        player.motionX = vector.x;
        player.motionY = vector.y;
        player.motionZ = vector.z;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}

package aurocosh.divinefavor.common.network.message.client.syncing;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncPotionCharge extends NetworkWrappedClientMessage {
    public int potionId;
    public int charges;

    public MessageSyncPotionCharge() {
    }

    public MessageSyncPotionCharge(Potion potion, int charges) {
        this.potionId = Potion.getIdFromPotion(potion);
        this.charges = charges;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        Potion potion = Potion.getPotionById(potionId);
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        PotionEffect effect = player.getActivePotionEffect(potion);
        if (effect != null)
            ReflectionHelper.setPrivateValue(PotionEffect.class, effect, charges, 3);
    }
}
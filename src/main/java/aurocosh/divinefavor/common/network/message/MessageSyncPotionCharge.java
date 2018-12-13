package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkAutoMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncPotionCharge extends NetworkAutoMessage {

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
    public IMessage handleMessage(MessageContext context) {
        DivineFavor.proxy.addScheduledTaskClient(this::handle);
        return null;
    }

    @SideOnly(Side.CLIENT)
    private void handle() {
        Potion potion = Potion.getPotionById(potionId);
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        PotionEffect effect = player.getActivePotionEffect(potion);
        if (effect == null)
            return;

        ReflectionHelper.setPrivateValue(PotionEffect.class, effect, charges, 3);
    }
}
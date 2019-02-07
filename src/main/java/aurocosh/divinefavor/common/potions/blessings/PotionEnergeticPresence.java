package aurocosh.divinefavor.common.potions.blessings;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.energetic.EnergeticPresenceData;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk;

@Mod.EventBusSubscriber
public class PotionEnergeticPresence extends ModPotion {
    private final float SPEED_MODIFIER = 0.35f;

    public PotionEnergeticPresence() {
        super("energetic_presence", true, 0x7FB8A4);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "5c6cd11e-eb6c-43b2-9e56-34a386693e93", SPEED_MODIFIER, 2);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        EnergeticPresenceData presenceData = PlayerData.get(player).getEnergeticPresenceData();
        presenceData.reset();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.isSprinting())
            tickLiquidWalk(livingBase, Blocks.WATER);
        EntityPlayer player = (EntityPlayer) livingBase;
        EnergeticPresenceData presenceData = PlayerData.get(player).getEnergeticPresenceData();

        boolean isInWater = player.isInWater();
        Block block = player.world.getBlockState(player.getPosition().down()).getBlock();
        boolean isOnWater = block == Blocks.WATER || block == Blocks.FLOWING_WATER;

        if (isInWater || !isOnWater)
            presenceData.reset();
        else if (presenceData.count() && !player.world.isRemote) {
            player.removePotionEffect(ModBlessings.energetic_presence);
            player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_redwind));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}

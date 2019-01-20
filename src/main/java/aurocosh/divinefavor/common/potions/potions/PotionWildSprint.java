package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;

import static aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk;

@Mod.EventBusSubscriber
public class PotionWildSprint extends ModPotion {
    private final float SPEED_MODIFIER = 0.2f;
    private final float WATER_RUNNING_SPEED = 0.25f;

    public PotionWildSprint() {
        super("wild_sprint", true, 0x7FB8A4);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "e9c4efd6-98fc-4273-ae05-571f4fd18628", SPEED_MODIFIER, 2);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ < WATER_RUNNING_SPEED * WATER_RUNNING_SPEED)
            return;
        tickLiquidWalk(entity, Blocks.WATER);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}

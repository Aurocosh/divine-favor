package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionRoots extends ModPotion {
    private final float SLOWNESS_FORCE = 4;

    public PotionRoots() {
        super("roots", true, 0x7FB8A4);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "817e7bc4-8ad5-4323-9131-aa71236a1b83", -SLOWNESS_FORCE, 2);
    }
}

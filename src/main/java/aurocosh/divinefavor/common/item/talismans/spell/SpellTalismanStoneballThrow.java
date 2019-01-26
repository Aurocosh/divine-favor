package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.entity.projectile.EntityStoneball;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SpellTalismanStoneballThrow extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanStoneballThrow() {
        super("stoneball_throw", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        throwStoneball(context.world, context.player);
    }

    public boolean throwStoneball(World worldIn, EntityPlayer playerIn) {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (UtilRandom.random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            EntityStoneball entityStoneball = new EntityStoneball(worldIn, playerIn);
            entityStoneball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.0F, 1.0F);
            worldIn.spawnEntity(entityStoneball);
        }
        return true;
    }
}

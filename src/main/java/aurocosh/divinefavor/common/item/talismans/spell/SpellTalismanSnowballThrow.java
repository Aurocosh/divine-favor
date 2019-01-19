package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.base.spell.TalismanContext;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SpellTalismanSnowballThrow extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanSnowballThrow() {
        super("snowball_throw", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        throwSnowball(context.world, context.player);
    }

    public boolean throwSnowball(World worldIn, EntityPlayer playerIn) {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (UtilRandom.random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            EntitySnowball entitysnowball = new EntitySnowball(worldIn, playerIn);
            entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(entitysnowball);
        }
        return true;
    }
}

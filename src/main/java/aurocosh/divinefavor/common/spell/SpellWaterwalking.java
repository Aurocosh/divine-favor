package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.api.spell.Spell;
import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.effect.ModPotionEffects;
import aurocosh.divinefavor.common.effect.effect.PotionWaterwalk;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellWaterwalking extends Spell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellWaterwalking() {
        super(LibSpellNames.WATERWALKING);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.playerIn.getEntityWorld().isRemote)
        //    return true;

        PotionEffect potioneffect = new PotionEffect(ModPotionEffects.waterwalk, NORMAL);
        context.playerIn.addPotionEffect(potioneffect);

        return true;
    }

    @Override
    protected boolean claimCost(SpellContext context) {
        return true;
    }
}

package aurocosh.divinefavor.common.item.arrows.base;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.arrow_spell.base.ArrowSpell;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpellArrow extends ModItemArrow {
    private final ArrowSpell spell;
    private final int color;

    public ItemSpellArrow(String name, ArrowSpell spell, int color) {
        super("spell_arrow_" + name, "spell_arrows/" + name);
        this.spell = spell;
        this.color = color;

//        setMaxStackSize(64);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public int getColor() {
        return color;
    }

    public ArrowSpell getSpell() {
        return spell;
    }

    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        EntitySpellArrow arrow = new EntitySpellArrow(worldIn, shooter);
        arrow.setSpell(stack, shooter);
        return arrow;
    }
}

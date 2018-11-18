package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.arl.item.ItemMod;

public class ItemIgnitionTalisman extends ItemTalisman {
    public ItemIgnitionTalisman() {
        super(LibItemNames.IGNITION_TALISMAN,true,false);

        //new AssemblyScavengeRecipe();
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return ModSpells.ignition.cast(context);
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return false;
    }
}
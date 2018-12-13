package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.CastType;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SpellCombustion extends ModSpell {
    private final int MAX_SMELT_COUNT = 10;
    private final int SMELTING_CHANCE = 50;
    private final float EXPLOSION_POWER = 4;
    private final boolean CAUSE_FIRE = false;
    private final boolean DAMAGE_TERRAIN = true;

    public SpellCombustion() {
        super("combustion");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        assert context.castType == CastType.ITEM_USE_CAST;

        BlockPos pos = context.pos;
        IBlockState state = context.world.getBlockState(pos);
        if(state.getBlock() != Blocks.CHEST)
            return false;

        TileEntity entity = context.world.getTileEntity(pos);
        IItemHandler stackHandler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, context.facing);

        int stacksToSmelt = MAX_SMELT_COUNT;
        int slotCount = stackHandler.getSlots();
        for (int i = 0; (i < slotCount) && (stacksToSmelt > 0); i++) {
            ItemStack stack = stackHandler.getStackInSlot(i);
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);

            if(result.isEmpty())
                continue;
            if(!UtilRandom.getPercentChance(SMELTING_CHANCE))
                continue;

            ItemStack smelted = result.copy();
            smelted.setCount(stack.getCount());

            stackHandler.extractItem(i,stack.getCount(),false);
            stackHandler.insertItem(i,smelted,false);
            stacksToSmelt--;
        }

        boolean damageTerrain = DAMAGE_TERRAIN && !state.getMaterial().isLiquid();
        context.world.newExplosion(context.player, pos.getX(), pos.getY(), pos.getZ(), EXPLOSION_POWER, CAUSE_FIRE, damageTerrain);
        return true;
    }
}

package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.custom_data.player.talisman_uses.ITalismanUsesHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static aurocosh.divinefavor.common.custom_data.player.talisman_uses.TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;

public abstract class ItemTalisman extends ModItem implements IIndexedEntry {
    private static int nextId = 0;

    protected final int id;
    protected final String name;
    protected final int startingSpellUses;

    // Talisman functions
    public ItemTalisman(String name, String texturePath, int startingSpellUses) {
        super(name, texturePath);

        id = nextId++;
        this.name = name;
        this.startingSpellUses = startingSpellUses;

        setMaxStackSize(1);
    }

    public String getName() {
        return name;
    }

    public int getStartingSpellUses() {
        return startingSpellUses;
    }

    public int getUseCount(EntityPlayer player) {
        ITalismanUsesHandler usesHandler = player.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;
        return usesHandler.getUses(id);
    }
// Talisman functions


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemTalisman))
            return EnumActionResult.PASS;
        if (getSpellUses(playerIn, worldIn, pos, stack))
            return EnumActionResult.SUCCESS;
        return EnumActionResult.SUCCESS;
    }

    public boolean getSpellUses(EntityPlayer playerIn, World worldIn, BlockPos pos, ItemStack stack) {
        if (!playerIn.isSneaking())
            return false;
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() != ModBlocks.blockDiviner)
            return false;
        ITalismanUsesHandler usesHandler = playerIn.getCapability(CAPABILITY_TALISMAN_USES, null);
        assert usesHandler != null;
        usesHandler.addUses(id, 10);
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public int getId() {
        return id;
    }
}
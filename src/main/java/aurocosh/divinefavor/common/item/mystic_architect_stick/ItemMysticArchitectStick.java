package aurocosh.divinefavor.common.item.mystic_architect_stick;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.util.UtilNbt;
import aurocosh.divinefavor.common.util.helper_classes.MultiblockBlockData;
import aurocosh.divinefavor.common.util.helper_classes.CubeCoordinates;
import aurocosh.divinefavor.common.util.helper_classes.MultiblockTemplateData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import vazkii.arl.item.ItemMod;

import java.util.*;

import static net.minecraft.client.gui.GuiScreen.setClipboardString;

public class ItemMysticArchitectStick extends ItemMod implements IDivineFavorItem {
    public static final String TAG_CURRENT_IS_SECOND = "CurrentIsSecond";
    public static final String TAG_POS_FIRST = "PosFirst";
    public static final String TAG_POS_SECOND = "PosSecond";

    public ItemMysticArchitectStick() {
        super(LibItemNames.MYSTIC_ARCHITECT_STICK);
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            return EnumActionResult.PASS;
        if (hand == EnumHand.OFF_HAND)
            return EnumActionResult.PASS;

        ItemStack stack = playerIn.getHeldItemMainhand();
        NBTTagCompound compound = UtilNbt.getEistingOrNewNBT(stack);



        if (!playerIn.isSneaking()) {
            boolean currentIsSecond = compound.getBoolean(TAG_CURRENT_IS_SECOND);
            if(currentIsSecond)
                UtilNbt.setBlockPos(compound, TAG_POS_SECOND, pos);
            else
                UtilNbt.setBlockPos(compound, TAG_POS_FIRST, pos);
            compound.setBoolean(TAG_CURRENT_IS_SECOND,!currentIsSecond);
            return EnumActionResult.SUCCESS;

        }

        boolean isCornersSet = UtilNbt.checkForTags(stack, TAG_POS_FIRST, TAG_POS_SECOND);
        if (!isCornersSet)
            return EnumActionResult.SUCCESS;

        BlockPos firstCorner = UtilNbt.getBlockPos(compound, TAG_POS_FIRST);
        BlockPos secondCorner = UtilNbt.getBlockPos(compound, TAG_POS_SECOND);

        String templateString = getTemplateData(worldIn, firstCorner, secondCorner, pos);
        setClipboardString(templateString);

        TextComponentString component = new TextComponentString("Template copied to clipboard");
        DivineFavor.proxy.getClientPlayer().sendMessage(component);

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if (!worldIn.isRemote)
            return new ActionResult<>(EnumActionResult.PASS, stack);
        if (hand == EnumHand.OFF_HAND)
            return new ActionResult<>(EnumActionResult.PASS, stack);

        boolean isCornersSet = UtilNbt.checkForTags(stack, TAG_POS_FIRST, TAG_POS_SECOND);
        if (!isCornersSet)
            return new ActionResult<>(EnumActionResult.PASS, stack);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private String getTemplateData(World world, BlockPos firstCorner, BlockPos secondCorner, BlockPos controllerPos) {
        CubeCoordinates coordinates = new CubeCoordinates(firstCorner, secondCorner);
        BlockPos[] positions = coordinates.getAllPositionsInside();
        coordinates.setCenter(Vector3i.fromBlockPos(controllerPos));
        BlockPos[] relativePositions = coordinates.getAllPositionsInside();

        List<MultiblockBlockData> dataList = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            BlockPos relativePos = relativePositions[i];
            BlockPos pos = positions[i];
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block != Blocks.AIR)
                dataList.add(new MultiblockBlockData(relativePos, block));
        }

        MultiblockTemplateData template = new MultiblockTemplateData(dataList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(template, MultiblockTemplateData.class);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}

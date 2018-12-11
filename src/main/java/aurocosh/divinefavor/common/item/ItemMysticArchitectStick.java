package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.items.ConstItemNames;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.lib.math.CubeCoordinates;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.MultiBlockData;
import aurocosh.divinefavor.common.muliblock.parts.*;
import aurocosh.divinefavor.common.muliblock.serialization.StateValidatorSerializer;
import aurocosh.divinefavor.common.muliblock.serialization.Vector3iByteSerializer;
import aurocosh.divinefavor.common.util.UtilNbt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.*;

import static net.minecraft.client.gui.GuiScreen.setClipboardString;

public class ItemMysticArchitectStick extends ModItem {
    public static final String TAG_CURRENT_IS_SECOND = "CurrentIsSecond";
    public static final String TAG_POS_FIRST = "PosFirst";
    public static final String TAG_POS_SECOND = "PosSecond";
    public static final String TAG_AIR_TYPE = "AirType";

    public ItemMysticArchitectStick() {
        super(ConstItemNames.MYSTIC_ARCHITECT_STICK,"");
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

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            if (compound.hasKey(TAG_AIR_TYPE)) {
                compound.removeTag(TAG_AIR_TYPE);
                DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Air block marker cleared"));
            }
            else {
                Block block = worldIn.getBlockState(pos).getBlock();
                String name = block.getRegistryName().toString();
                compound.setString(TAG_AIR_TYPE, name);
                DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Air block marker set to: " + name));
            }
            return EnumActionResult.SUCCESS;
        }

        if (!playerIn.isSneaking()) {
            boolean currentIsSecond = compound.getBoolean(TAG_CURRENT_IS_SECOND);
            if (currentIsSecond)
                UtilNbt.setBlockPos(compound, TAG_POS_SECOND, pos);
            else
                UtilNbt.setBlockPos(compound, TAG_POS_FIRST, pos);
            compound.setBoolean(TAG_CURRENT_IS_SECOND, !currentIsSecond);
            return EnumActionResult.SUCCESS;

        }

        boolean isCornersSet = UtilNbt.checkForTags(stack, TAG_POS_FIRST, TAG_POS_SECOND);
        if (!isCornersSet)
            return EnumActionResult.SUCCESS;

        BlockPos firstCorner = UtilNbt.getBlockPos(compound, TAG_POS_FIRST);
        BlockPos secondCorner = UtilNbt.getBlockPos(compound, TAG_POS_SECOND);
        String airMarker = compound.getString(TAG_AIR_TYPE);

        String templateString = getTemplateData(worldIn, firstCorner, secondCorner, pos, airMarker);
        setClipboardString(templateString);

        DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Template copied to clipboard"));

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

    private String getTemplateData(World world, BlockPos firstCorner, BlockPos secondCorner, BlockPos controllerPos, String airMarker) {
        CubeCoordinates coordinatesWorld = new CubeCoordinates(firstCorner, secondCorner);
        Vector3i[] positions = coordinatesWorld.getAllPositionsInside();
        Vector3i lowerCorner = coordinatesWorld.lowerCorner;
        ResourceLocation airMarkerName = new ResourceLocation(airMarker);

        Block centerBlock = Blocks.AIR;
        Vector3i center = Vector3i.convert(controllerPos);
        Map<Block, List<Vector3i>> partMap = new HashMap<>();
        for (Vector3i pos : positions) {
            IBlockState state = world.getBlockState(pos.toBlockPos());
            Block block = state.getBlock();
            if(block == Blocks.AIR)
                continue;

            if (pos.equals(center))
                centerBlock = block;
            else {
                List<Vector3i> partPositions = partMap.computeIfAbsent(block, k -> new ArrayList<>());
                partPositions.add(lowerCorner.getRealativePosition(pos));
            }
        }

        List<MultiBlockPart> parts = new ArrayList<>(partMap.size());
        for (Map.Entry<Block, List<Vector3i>> entry : partMap.entrySet()) {
            Block block = entry.getKey();
            StateValidator validator;
            if (block.getRegistryName().equals(airMarkerName))
                validator = new AirStateValidator();
            else
                validator = new BlockStateValidator(entry.getKey().getRegistryName());
            parts.add(new MultiBlockPart(validator, entry.getValue()));
        }
        parts.add(new MultiBlockPart(new CenterStateValidator(centerBlock.getRegistryName()), Collections.singletonList(lowerCorner.getRealativePosition(center))));

        MultiBlockData data = new MultiBlockData(lowerCorner.getRealativePosition(Vector3i.convert(controllerPos)), parts);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(StateValidator.class, new StateValidatorSerializer())
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Vector3i.class, new Vector3iByteSerializer())
                .create();

        return gson.toJson(data, MultiBlockData.class);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}

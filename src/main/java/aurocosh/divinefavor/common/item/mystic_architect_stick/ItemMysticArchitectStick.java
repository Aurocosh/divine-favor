package aurocosh.divinefavor.common.item.mystic_architect_stick;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
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

import java.util.*;

import static net.minecraft.client.gui.GuiScreen.setClipboardString;

public class ItemMysticArchitectStick extends ModItem {
    public static final String TAG_CURRENT_IS_SECOND = "CurrentIsSecond";
    public static final String TAG_POS_FIRST = "PosFirst";
    public static final String TAG_POS_SECOND = "PosSecond";
    public static final String TAG_AIR_TYPE = "AirType";
    public static final String TAG_BASE_POSITION = "BasePosition";
    public static final String TAG_MEDIUM_POSITION = "MediumPosition";
    public static final String TAG_CURRENT_MODE = "CurrentMode";

    public ItemMysticArchitectStick() {
        super("mystic_architect_stick","mystic_architect_stick");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            return EnumActionResult.PASS;
        if (hand == EnumHand.OFF_HAND)
            return EnumActionResult.PASS;

        ItemStack stack = playerIn.getHeldItemMainhand();
        NBTTagCompound compound = UtilNbt.getNbt(stack);

        if (!playerIn.isSneaking()) {
            boolean currentIsSecond = compound.getBoolean(TAG_CURRENT_IS_SECOND);
            if (currentIsSecond)
                UtilNbt.setBlockPos(compound, TAG_POS_SECOND, pos);
            else
                UtilNbt.setBlockPos(compound, TAG_POS_FIRST, pos);
            compound.setBoolean(TAG_CURRENT_IS_SECOND, !currentIsSecond);
            return EnumActionResult.SUCCESS;

        }
        else {
            int mode = compound.getInteger(ItemMysticArchitectStick.TAG_CURRENT_MODE);
            ArchitectStickMode stickMode = ArchitectStickMode.VALUES[mode];

            switch (stickMode) {
                case SELECT_MULTIBLOCK_BASE:
                    compound.setLong(TAG_BASE_POSITION, pos.toLong());
                    DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Set base position: " + pos.toString()));
                    break;
                case SELECT_MEDIUM:
                    compound.setLong(TAG_MEDIUM_POSITION, pos.toLong());
                    DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Set medium position: " + pos.toString()));
                    break;
                case SELECT_AIR_BLOCK:
                    selectAirBlock(world, pos, compound);
                    break;
                case CREATE_TEMPLATE:
                    createTemplate(world, pos, stack, compound);
                    break;
            }
        }
        return EnumActionResult.SUCCESS;
    }

    private void selectAirBlock(World worldIn, BlockPos pos, NBTTagCompound compound) {
        if (compound.hasKey(TAG_AIR_TYPE)) {
            compound.removeTag(TAG_AIR_TYPE);
            DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Air block marker cleared"));
        }
        else {
            Block block = worldIn.getBlockState(pos).getBlock();
            String name = block.getRegistryName().toString();
            compound.setString(TAG_AIR_TYPE, name);
            DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Air block marker setValue to: " + name));
        }
    }

    private void createTemplate(World worldIn, BlockPos pos, ItemStack stack, NBTTagCompound compound) {
        if (!UtilNbt.checkForTags(stack, TAG_POS_FIRST, TAG_POS_SECOND)){
            DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Corners not setValue"));
            return;
        }
        if (!UtilNbt.checkForTags(stack, TAG_BASE_POSITION)){
            DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Base position not setValue"));
            return;
        }
        if (!UtilNbt.checkForTags(stack, TAG_MEDIUM_POSITION)){
            DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Medium position not setValue"));
            return;
        }

        BlockPos firstCorner = UtilNbt.getBlockPos(compound, TAG_POS_FIRST);
        BlockPos secondCorner = UtilNbt.getBlockPos(compound, TAG_POS_SECOND);
        BlockPos basePosition = UtilNbt.getBlockPos(compound, TAG_BASE_POSITION);
        BlockPos mediumPosition = UtilNbt.getBlockPos(compound, TAG_MEDIUM_POSITION);
        String airMarker = compound.getString(TAG_AIR_TYPE);

        String templateString = getTemplateData(worldIn, firstCorner, secondCorner, mediumPosition, basePosition, airMarker);
        setClipboardString(templateString);

        DivineFavor.proxy.getClientPlayer().sendMessage(new TextComponentString("Template copied to clipboard"));
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

    private String getTemplateData(World world, BlockPos firstCorner, BlockPos secondCorner, BlockPos mediumPosition, BlockPos basePosition, String airMarker) {
        CubeCoordinates coordinatesWorld = new CubeCoordinates(firstCorner, secondCorner);
        Vector3i[] positions = coordinatesWorld.getAllPositionsInside();
        Vector3i lowerCorner = coordinatesWorld.lowerCorner;
        ResourceLocation airMarkerName = new ResourceLocation(airMarker);

        Block baseBlock = Blocks.AIR;
        Vector3i base = Vector3i.convert(basePosition);
        Map<Block, List<Vector3i>> partMap = new HashMap<>();
        for (Vector3i pos : positions) {
            IBlockState state = world.getBlockState(pos.toBlockPos());
            Block block = state.getBlock();
            if(block == Blocks.AIR)
                continue;

            if (pos.equals(base))
                baseBlock = block;
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
        parts.add(new MultiBlockPart(new CenterStateValidator(baseBlock.getRegistryName()), Collections.singletonList(lowerCorner.getRealativePosition(base))));

        MultiBlockData data = new MultiBlockData(lowerCorner.getRealativePosition(Vector3i.convert(mediumPosition)), parts);

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

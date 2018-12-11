package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.constants.items.ConstCallingStoneNames;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.talismans.Talisman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public final class ModCallingStones {
    private static List<CallingStone> callingStonesList = new ArrayList<>();
    private static final Map<ResourceLocation, CallingStone> callingStones = new HashMap<>();

    public static CallingStone allfire_calling_stone;
    public static CallingStone timber_calling_stone;
    public static CallingStone romol_calling_stone;

    public static Collection<CallingStone> getValues() {
        return callingStones.values();
    }

    public static ItemStack getStack(CallingStone callingStone){
        return new ItemStack(ModItems.calling_stone,1, getMeta(callingStone));
    }


    public static int getMeta(CallingStone talisman){
        return callingStonesList.indexOf(talisman);
    }

    public static CallingStone getByMeta(int meta){
        if(meta >= callingStonesList.size())
            return null;
        return callingStonesList.get(meta);
    }

    public static String[] getNames() {
        String[] names = new String[callingStonesList.size()];
        for (int i = 0; i < callingStonesList.size(); i++)
            names[i] = callingStonesList.get(i).name;
        return names;
    }

    public static void preInit() {
        allfire_calling_stone = register(new CallingStone(ConstCallingStoneNames.ALLFIRE, ModSpirits.allfire, ModMultiBlocks.allfire_altar));
        timber_calling_stone = register(new CallingStone(ConstCallingStoneNames.TIMBER, ModSpirits.timber, ModMultiBlocks.timber_altar));
        romol_calling_stone = register(new CallingStone(ConstCallingStoneNames.ROMOL, ModSpirits.romol, ModMultiBlocks.romol_altar));
    }

    public static void init() {
    }

    private static CallingStone register(CallingStone item) {
        callingStones.put(item.getRegistryName(), item);
        callingStonesList.add(item);
        CommonRegistry.register(item);
        return item;
    }
}
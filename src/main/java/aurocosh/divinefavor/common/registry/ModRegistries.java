package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.potions.base.ModPotion;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraft.item.ItemBlock;

public class ModRegistries {
    public static RegistryMap<ItemBlock> itemBlocks = new RegistryMap<>();
    public static RegistryMap<ModBlock> blocks = new RegistryMap<>();
    public static RegistryMap<ModFavor> favors = new RegistryMap<>();
    public static RegistryMap<ModItem> items = new RegistryMap<>();
    public static RegistryMap<ModMultiBlock> multiBlocks = new RegistryMap<>();
    public static RegistryMap<ModSpell> spells = new RegistryMap<>();
    public static RegistryMap<ModSpirit> spirits = new RegistryMap<>();
    public static RegistryMap<ModPotion> potions = new RegistryMap<>();
}

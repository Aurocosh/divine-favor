package aurocosh.divinefavor.common.integrations.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.ArrayList;
import java.util.List;

public class SpellTalismanStatProcessor implements IComponentProcessor {
    ItemSpellTalisman spellTalisman;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String talismanName = variables.get("talisman");
        Item item = Item.REGISTRY.getObject(new ResourceLocation(talismanName));
        if (item instanceof ItemSpellTalisman) {
            spellTalisman = (ItemSpellTalisman) item;


        }
        else
            DivineFavor.logger.error("Spell talisman not found:" + talismanName);
    }

    @Override
    public String process(String key) {
        if (key.startsWith("talisman")) {
            ItemStack stack = new ItemStack(spellTalisman, 1);
            return ItemStackUtil.serializeStack(stack);
        }
        else if (key.equals("spirit_icon")) {

            ModSpirit spirit = spellTalisman.getSpirit();
            return spirit.getIcon().toString();
        }
        else if (key.equals("spirit_symbol")) {
            ModSpirit spirit = spellTalisman.getSpirit();
            return spirit.getSymbol().toString();
        }
        else if (key.equals("text")) {
            List<String> lines = new ArrayList<>();
            ModSpirit spirit = spellTalisman.getSpirit();
            lines.add("Favor: " + spirit.getName());
            int favorCost = spellTalisman.getFavorCost();
            if (favorCost == 0)
                lines.add("No cost");
            else
                lines.add("Favor cost: " + favorCost);
            return String.join("$(br)", lines);
        }
        return null;
    }

}
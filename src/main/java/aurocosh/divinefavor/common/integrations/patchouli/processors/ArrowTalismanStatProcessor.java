package aurocosh.divinefavor.common.integrations.patchouli.processors;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanStatProcessor implements IComponentProcessor {
    ItemArrowTalisman arrowTalisman;

    @Override
    public void setup(IVariableProvider<String> variables) {
        String talismanName = variables.get("talisman");
        Item item = Item.REGISTRY.getObject(new ResourceLocation(talismanName));
        if (item instanceof ItemArrowTalisman)
            arrowTalisman = (ItemArrowTalisman) item;
        else
            DivineFavor.logger.error("Arrow talisman not found:" + talismanName);
    }

    @Override
    public String process(String key) {
        if (key.startsWith("talisman")) {
            ItemStack stack = new ItemStack(arrowTalisman, 1);
            return ItemStackUtil.serializeStack(stack);
        }
        else if (key.equals("spirit_icon")) {
            ModSpirit spirit = arrowTalisman.getSpirit();
            return spirit.getIcon().toString();
        }
        else if (key.equals("spirit_symbol")) {
            ModSpirit spirit = arrowTalisman.getSpirit();
            return spirit.getSymbol().toString();
        }
//
        else if (key.equals("text")) {
            List<String> lines = new ArrayList<>();
            lines.add("Favor: " + arrowTalisman.getSpirit().getName());
            int favorCost = arrowTalisman.getFavorCost();
            if (favorCost == 0)
                lines.add("No cost");
            else
                lines.add("Favor cost: " + favorCost);
            double arrowDamage = arrowTalisman.getArrowDamage();
            if (arrowDamage == 0)
                lines.add("Arrow deals no damage");
            else {
                NumberFormat formatter = new DecimalFormat("#0.00");
                lines.add("Arrow damage: " + formatter.format(arrowDamage));
            }
            EnumSet<ArrowOptions> options = arrowTalisman.getOptions();
            if (options.equals(ArrowOptions.Companion.getNORMAL()))
                lines.add("No extra properties");
            else {
                lines.add("Extra properties: ");
                if (options.contains(ArrowOptions.BreakOnHit))
                    lines.add("  Will break on hit. ");
                if (options.contains(ArrowOptions.RequiresTarget))
                    lines.add("  Needs target.");
            }
            return String.join("$(br)", lines);
        }
        return null;
    }

}
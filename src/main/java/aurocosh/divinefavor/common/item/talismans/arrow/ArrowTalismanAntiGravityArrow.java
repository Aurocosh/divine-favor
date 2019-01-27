package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.GravityType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanAntiGravityArrow extends ItemArrowTalisman {
    public ArrowTalismanAntiGravityArrow(String name, ModFavor favor, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, favor, favorCost, color, arrowDamage, options, arrowType);
        gravityType = GravityType.ANTIGRAVITY;
    }
}

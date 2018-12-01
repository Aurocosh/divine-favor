package aurocosh.divinefavor.common.item.talisman;

import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.requirements.cost.costs.Cost;
import aurocosh.divinefavor.common.spell.base.ModSpell;

import java.util.ArrayList;
import java.util.List;

public class TalismanBuilder {
    private String name;
    private boolean castOnUse;
    private boolean castOnRightClick;
    private ModSpell spell;
    private List<Cost> costs;

    public TalismanBuilder(String name) {
        this.name = name;
        castOnUse = false;
        castOnRightClick = false;
        costs = new ArrayList<>();
    }

    public TalismanBuilder castOnUse(){
        this.castOnUse = true;
        return this;
    }

    public TalismanBuilder castOnRighClick(){
        this.castOnRightClick = true;
        return this;
    }

    public TalismanBuilder setSpell(ModSpell spell){
        this.spell = spell;
        return this;
    }

    public TalismanBuilder addCost(Cost cost){
        costs.add(cost);
        return this;
    }

    public ItemTalisman create(){
        SpellRequirement requirement = new SpellRequirement(costs);
        return new ItemTalisman(name,spell,requirement,castOnUse,castOnRightClick);
    }
}

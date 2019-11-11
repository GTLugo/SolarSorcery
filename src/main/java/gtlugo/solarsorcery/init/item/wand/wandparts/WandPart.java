package gtlugo.solarsorcery.init.item.wand.wandparts;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class WandPart {
    // Unique name for each material for identification purposes
    public final String _materialName;

    public final float _combat;
    public final float _buff;
    public final float _manip;

    public WandPart(String materialName, float combat, float buff, float manip) {
        this._materialName = materialName;
        this._combat = combat;
        this._buff = buff;
        this._manip = manip;
    }
    /*
    public String getMaterialName() {
        return _materialName;
    }

    public Item getMaterialItem() {
        return _materialItem;
    }

    public float getCombat() {
        return _combat;
    }

    public float getBuff() {
        return _buff;
    }

    public float getManip() {
        return _manip;
    }
    */
}

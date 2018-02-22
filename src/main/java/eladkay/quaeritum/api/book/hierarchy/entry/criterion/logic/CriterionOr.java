package eladkay.quaeritum.api.book.hierarchy.entry.criterion.logic;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eladkay.quaeritum.api.book.hierarchy.entry.criterion.ICriterion;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

/**
 * @author WireSegal
 * Created at 9:45 PM on 2/21/18.
 */
public class CriterionOr implements ICriterion {

    private final List<ICriterion> criteria = Lists.newArrayList();

    public CriterionOr(JsonObject object) {
        JsonArray values = object.getAsJsonArray("values");
        for (JsonElement value : values) {
            ICriterion criterion = ICriterion.fromJson(value);
            if (criterion != null)
                criteria.add(criterion);
        }
    }

    @Override
    public boolean isUnlocked(EntityPlayer player, boolean grantedInCode) {
        for (ICriterion criterion : criteria)
            if (criterion.isUnlocked(player, grantedInCode))
                return true;
        return false;
    }
}

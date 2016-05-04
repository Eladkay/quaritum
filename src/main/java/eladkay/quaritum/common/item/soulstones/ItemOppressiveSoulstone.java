package eladkay.quaritum.common.item.soulstones;

import eladkay.quaritum.common.item.base.ItemMod;
import eladkay.quaritum.common.lib.LibNames;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemOppressiveSoulstone extends ItemMod {
    public ItemOppressiveSoulstone() {
        super(LibNames.OPPRESSIVE_SOULSTONE);
        setMaxStackSize(1);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }
}

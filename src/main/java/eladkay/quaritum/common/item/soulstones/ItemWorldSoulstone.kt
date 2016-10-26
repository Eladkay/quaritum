package eladkay.quaritum.common.item.soulstones

import com.teamwizardry.librarianlib.common.base.item.ItemMod
import eladkay.quaritum.api.animus.AnimusHelper
import eladkay.quaritum.api.animus.ISoulstone
import eladkay.quaritum.api.lib.LibNBT
import eladkay.quaritum.api.util.ItemNBTHelper
import eladkay.quaritum.client.core.ClientUtils
import eladkay.quaritum.common.lib.LibNames
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class ItemWorldSoulstone : ItemMod(LibNames.STONE_OF_THE_WORLDSOUL), ISoulstone {

    init {
        setMaxStackSize(1)
    }

    override fun onUpdate(stack: ItemStack?, worldIn: World?, entityIn: Entity?, itemSlot: Int, isSelected: Boolean) {
        if (worldIn!!.totalWorldTime % 60 == 0L)
            doPassive(stack!!)
    }

    override fun doPassive(stack: ItemStack) {
        AnimusHelper.addAnimus(stack, 1)
    }

    override fun addInformation(itemStack: ItemStack, player: EntityPlayer?, list: MutableList<String>, par4: Boolean) {
        ClientUtils.addInformation(itemStack, list, par4)
    }

    override fun getEntityLifespan(itemStack: ItemStack?, world: World?): Int {
        return Integer.MAX_VALUE
    }

    override fun getAnimusLevel(stack: ItemStack): Int {
        return ItemNBTHelper.getInt(stack, LibNBT.TAG_ANIMUS, 0)
    }

    override fun getMaxAnimus(stack: ItemStack): Int {
        return 800
    }

    override fun getMaxRarity(stack: ItemStack): Int {
        return 20
    }

    override fun isRechargeable(stack: ItemStack): Boolean {
        return true
    }

}

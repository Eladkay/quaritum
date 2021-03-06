package eladkay.quaeritum.common.item

import com.teamwizardry.librarianlib.core.common.OreDictionaryRegistrar
import com.teamwizardry.librarianlib.features.base.item.ItemMod
import eladkay.quaeritum.api.animus.EnumAnimusTier
import eladkay.quaeritum.api.animus.IAnimusResource
import eladkay.quaeritum.api.spell.EnumSpellElement
import eladkay.quaeritum.api.spell.ISpellReagent
import eladkay.quaeritum.common.lib.LibNames
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import java.util.*

/**
 * @author WireSegal
 * Created at 9:05 PM on 2/8/17.
 */
class ItemEssence : ItemMod(LibNames.ESSENCE, *NAMES), IAnimusResource, ISpellReagent {
    companion object {
        @JvmOverloads
        fun stackOf(enum: EnumAnimusTier, size: Int = 1) = ItemStack(ModItems.essence, size, enum.ordinal)

        val NAMES = EnumAnimusTier.values().map { it.name.toLowerCase(Locale.ROOT) }.toTypedArray()
    }

    init {
        for (variant in EnumAnimusTier.values())
            OreDictionaryRegistrar.registerOre(variant.oreName) {
                ItemStack(this, 1, variant.ordinal)
            }
    }

    override fun getAnimus(stack: ItemStack) = (EnumAnimusTier.fromMeta(stack.itemDamage).awakenedFillPercentage * ModItems.awakened.MAX_ANIMUS).toInt()

    override fun getAnimusTier(stack: ItemStack) = EnumAnimusTier.fromMeta(stack.itemDamage)

    override fun canAddToReagentBag(stack: ItemStack) = true

    override fun chargesForElement(stack: ItemStack, element: EnumSpellElement): Int {
        val tier = getAnimusTier(stack)
        return when (element) {
            tier.elementPrimary -> 50 * stack.count
            tier.elementSecondary -> 20 * stack.count
            else -> -1
        }
    }

    override fun consumeCharge(stack: ItemStack, element: EnumSpellElement, charges: Int): ActionResult<ItemStack> {
        val amountPerItem = chargesForElement(stack, element) / stack.count

        val totalAmount = amountPerItem * stack.count
        if (totalAmount < charges)
            return ActionResult(EnumActionResult.FAIL, stack)
        val returnStack = stack.copy()
        returnStack.count = (totalAmount - charges) / amountPerItem
        return ActionResult(EnumActionResult.SUCCESS, returnStack)
    }
}

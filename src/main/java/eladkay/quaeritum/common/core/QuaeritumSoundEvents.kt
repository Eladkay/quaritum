package eladkay.quaeritum.common.core

import eladkay.quaeritum.api.lib.LibMisc
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * @author WireSegal
 * Created at 4:53 PM on 11/5/16.
 */
object QuaeritumSoundEvents {
    val baubleEquip: SoundEvent
    val centrifuge: SoundEvent

    init {
        var loc = ResourceLocation(LibMisc.MOD_ID, "baubleEquip")
        baubleEquip = SoundEvent(loc)
        GameRegistry.register(baubleEquip, loc)

        loc = ResourceLocation(LibMisc.MOD_ID, "centrifuge")
        centrifuge = SoundEvent(loc)
        GameRegistry.register(centrifuge, loc)
    }
}
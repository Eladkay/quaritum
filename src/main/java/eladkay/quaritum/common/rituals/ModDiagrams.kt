package eladkay.quaritum.common.rituals

import eladkay.quaritum.api.rituals.RitualRegistry
import eladkay.quaritum.common.rituals.diagrams.*

object ModDiagrams {
    fun init() {
        RitualRegistry.registerDiagram(ShardedSkiesDiagram(), "shardedSkies")
        RitualRegistry.registerDiagram(ShardedSkiesTier2Diagram(), "shardedSkies2")
        RitualRegistry.registerDiagram(RitualSummoning(), "summonChaosborn")
        RitualRegistry.registerDiagram(AltarOfTheFallingStarDiagram(), "altarOfTheFallingStar")
        RitualRegistry.registerDiagram(HarmonizedHallDiagram(), "merger")
        RitualRegistry.registerDiagram(InfusionDiagram(), "infusion")
        RitualRegistry.registerDiagram(CircleOfTheFinalMomentDiagram(), "circleofthefinalmoment")

        RitualRegistry.registerDiagram(TransducerCraftingDiagram(), "transducer")
    }

}

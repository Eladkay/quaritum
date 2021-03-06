package eladkay.quaeritum.common.rituals

import eladkay.quaeritum.api.rituals.RitualRegistry
import eladkay.quaeritum.common.rituals.diagrams.*

object ModDiagrams {
    fun init() {
        RitualRegistry.registerDiagram(ShardedSkiesDiagram(), "shardedSkies")
        RitualRegistry.registerDiagram(ShardedSkiesTier2Diagram(), "shardedSkies2")
        RitualRegistry.registerDiagram(RitualSummoning(), "summonChaosborn")
        RitualRegistry.registerDiagram(AltarOfTheFallingStarDiagram(), "altarOfTheFallingStar")
        RitualRegistry.registerDiagram(CircleOfTheFinalMomentDiagram(), "circleofthefinalmoment")
        RitualRegistry.registerDiagram(PactforgerBind(), "pactforge")
        RitualRegistry.registerDiagram(PactbreakerBind(), "pactbreak")
        RitualRegistry.registerDiagram(AcademyOfTheFive(), "academy")
        RitualRegistry.registerDiagram(TheTwistedTower(), "tower")
        RitualRegistry.registerDiagram(DesignBornOfPassion(), "passion")
        RitualRegistry.registerDiagram(SealOfTheFive(), "eaeye")

    }

}

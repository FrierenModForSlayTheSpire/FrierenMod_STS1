package FrierenMod.relics;

import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class StarkRelic extends AbstractBaseRelic {
    public static final String ID = ModInformation.makeID(StarkRelic.class.getSimpleName());

    public StarkRelic() {
        super(ID, RelicTier.STARTER);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new StarkRelic();
    }
}

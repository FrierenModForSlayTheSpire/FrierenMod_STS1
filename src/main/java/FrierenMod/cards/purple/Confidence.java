package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.powers.ConfidencePower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Confidence extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Confidence.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 3, CardType.POWER, CardEnums.FERN_CARD, CardRarity.RARE);

    public Confidence() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new ConfidencePower()));
    }
}

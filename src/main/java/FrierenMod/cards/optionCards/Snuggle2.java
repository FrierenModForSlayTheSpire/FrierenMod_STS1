package FrierenMod.cards.optionCards;

import FrierenMod.actions.MakeManaInHandAction;
import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Snuggle2 extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Snuggle2.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, -2, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);

    public Snuggle2() {
        super(info);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        this.addToBot(new MakeManaInHandAction(this.magicNumber));
    }
}

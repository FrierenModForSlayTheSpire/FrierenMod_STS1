package FrierenMod.cards.orange;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Stark extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Defend_Stark.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 1, CardType.SKILL, CardEnums.STARK_CARD, CardRarity.BASIC, CardTarget.SELF);

    public Defend_Stark() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.block = baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
    }
}


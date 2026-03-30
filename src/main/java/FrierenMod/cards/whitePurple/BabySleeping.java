package FrierenMod.cards.whitePurple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.effects.PlaySFXEffect;
import FrierenMod.enums.CardEnums;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class BabySleeping extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(BabySleeping.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 1, CardType.SKILL, CardEnums.FRIEREN_CARD, CardRarity.COMMON, CardTarget.SELF);
    public static final CardInfo info2 = new CardInfo(ID, 1, CardType.SKILL, CardEnums.FERN_CARD, CardRarity.COMMON, CardTarget.SELF, true);

    public BabySleeping() {
        super(info);
    }

    public BabySleeping(boolean forSecondRegister) {
        super(info2);
    }

    @Override
    public AbstractCard getCardForSecondRegister() {
        return new BabySleeping(true);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.tags.add(Enum.FRIEREN_FERN_CARD);
        this.block = baseBlock = 7;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = CombatHelper.getManaNumInDiscardPile() == 0 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(p, new PlaySFXEffect("BabySleeping.mp3"),0.1F));
        this.addToBot(new GainBlockAction(p, p, this.block));
        if (CombatHelper.getManaNumInDiscardPile() == 0)
            this.addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, 1), 1));
        else
            this.addToBot(new RetainCardsAction(p, this.magicNumber));
    }
}



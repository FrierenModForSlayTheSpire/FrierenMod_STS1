package FrierenMod.cards.tempCards;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.gameHelpers.ActionHelper;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Cheering extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(Cheering.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 3, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);

    public Cheering() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.block = this.baseBlock = 10;
        this.isEthereal = true;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, this.block));
        ActionHelper.addToBotAbstract(()->{
            for (AbstractCard c : p.hand.group) {
                if (c.cardID.equals(Pouting.ID)) {
                    this.addToBot(new ModifyDamageAction(c.uuid, this.block));
                }
            }
            for (AbstractCard c : p.drawPile.group) {
                if (c.cardID.equals(Pouting.ID)) {
                    this.addToBot(new ModifyDamageAction(c.uuid, this.block));
                }
            }
            for (AbstractCard c : p.discardPile.group) {
                if (c.cardID.equals(Pouting.ID)) {
                    this.addToBot(new ModifyDamageAction(c.uuid, this.block));
                }
            }
            for (AbstractCard c : p.exhaustPile.group) {
                if (c.cardID.equals(Pouting.ID)) {
                    this.addToBot(new ModifyDamageAction(c.uuid, this.block));
                }
            }
        });
    }
}


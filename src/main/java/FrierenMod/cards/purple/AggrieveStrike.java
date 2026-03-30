package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.enums.CardEnums;
import FrierenMod.gameHelpers.CombatHelper;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AggrieveStrike extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(AggrieveStrike.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 0, AbstractCard.CardType.ATTACK, CardEnums.FERN_CARD, CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);

    public AggrieveStrike() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.damage = this.baseDamage = 5;
        this.raidNumber = this.baseRaidNumber = 1;
        this.tags.add(Enum.RAID);
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.returnToHand = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.isRaidTriggered = CombatHelper.triggerRaid(this.raidNumber, () -> this.returnToHand = true);
    }
}

package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.cards.optionCards.FocusOnAttack;
import FrierenMod.cards.optionCards.FocusOnDefense;
import FrierenMod.enums.CardEnums;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CounterAttack extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(CounterAttack.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 1, CardType.ATTACK, CardEnums.FERN_CARD, CardRarity.COMMON, CardTarget.ENEMY);

    public CounterAttack() {
        super(info);
    }

    @Override
    public void initializeSpecifiedAttributes() {
        this.tags.add(Enum.SPEED);
        this.damage = this.baseDamage = 9;
        this.block = this.baseBlock = 9;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> options = new ArrayList<>();
        FocusOnAttack focusOnAttack = new FocusOnAttack();
        FocusOnDefense focusOnDefense = new FocusOnDefense();
        focusOnAttack.target = m;
        focusOnAttack.damage = focusOnAttack.baseDamage = this.damage;
        focusOnDefense.block = focusOnDefense.baseBlock = this.baseBlock;
        focusOnAttack.applyPowers();
        focusOnDefense.applyPowers();
        options.add(focusOnAttack);
        options.add(focusOnDefense);
        this.addToBot(new ChooseOneAction(options));
    }
}


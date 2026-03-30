package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.effects.PlaySFXEffect;
import FrierenMod.enums.CardEnums;
import FrierenMod.powers.SocialDancingPower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SocialDancing extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(SocialDancing.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 3, CardType.POWER, CardEnums.FERN_CARD, CardRarity.RARE);

    public SocialDancing() {
        super(info);
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
        this.addToBot(new VFXAction(p, new PlaySFXEffect("SocialDancing.mp3"),0.1F));
        this.addToBot(new ApplyPowerAction(p, p, new SocialDancingPower()));
    }
}

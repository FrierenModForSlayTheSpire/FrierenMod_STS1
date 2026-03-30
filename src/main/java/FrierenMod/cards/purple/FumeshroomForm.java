package FrierenMod.cards.purple;

import FrierenMod.cards.AbstractBaseCard;
import FrierenMod.effects.PlaySFXEffect;
import FrierenMod.enums.CardEnums;
import FrierenMod.powers.FumeshroomFormPower;
import FrierenMod.utils.CardInfo;
import FrierenMod.utils.ModInformation;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FumeshroomForm extends AbstractBaseCard {
    public static final String ID = ModInformation.makeID(FumeshroomForm.class.getSimpleName());
    public static final CardInfo info = new CardInfo(ID, 2, CardType.POWER, CardEnums.FERN_CARD, CardRarity.RARE);

    public FumeshroomForm() {
        super(info);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(p, new PlaySFXEffect("FumeshroomForm.mp3"),0.1F));
        this.addToBot(new ApplyPowerAction(p, p, new FumeshroomFormPower()));
    }
}

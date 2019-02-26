package TheMusician.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class MusicalDestinyAction extends AbstractGameAction {

    //~~~~~~~~~~~~~~~~~~ Variables to be used ~~~~~~~~~~~~~~~~~~//
    private static final String tipMSG = "Card to be removed.";
    private static final String tipMSGs = "Cards to be removed.";
    private int NumOfCards;


    //~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~//
    public MusicalDestinyAction(int NumOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.NumOfCards = NumOfCards;
    }


    //~~~~~~~~~~~~~~~~~~ Uses of the Action ~~~~~~~~~~~~~~~~~~//
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (NumOfCards == 1){
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), NumOfCards, tipMSG, false, false, false, true);
            }
            else {
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), NumOfCards, tipMSGs, false, false, false, true);
            }
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
            tickDuration();
        }
        else {
            if ((!AbstractDungeon.isScreenUp) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    if (card == c) {
                        AbstractDungeon.player.drawPile.removeCard(c);
                    }
                }
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card == c) {
                        AbstractDungeon.player.hand.removeCard(c);
                    }
                }
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.effectList.add(new PurgeCardEffect(c));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            tickDuration();
        }
    }
}

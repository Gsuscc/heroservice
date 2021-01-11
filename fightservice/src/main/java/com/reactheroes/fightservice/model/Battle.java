package com.reactheroes.fightservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battle {

    private Fight myArmy;
    private Fight enemyArmy;

    List<Round> rounds;

    public void startBattle() {
        rounds = new ArrayList<>();
        myArmy.generateStats();
        enemyArmy.generateStats();
        List<HeroCard> myCards = new java.util.ArrayList<>(List.copyOf(myArmy.getCards()));
        List<HeroCard> enemyCards = new java.util.ArrayList<>(List.copyOf(enemyArmy.getCards()));
        Player me = initPlayer(myCards.remove(0), true);
        Player enemy = initPlayer(enemyCards.remove(0), false);
        roundGenerator(me, enemy);
        doBattle(myCards, enemyCards);
    }

    private void doBattle(List<HeroCard> myCards, List<HeroCard> enemyCards) {
        while (true) {
            Round lastRound = getLastRound();
            Player attacker = swapPlayer(lastRound.getDefender());
            Player defender = swapPlayer(lastRound.getAttacker());
            if (lastRound.getDefender().isDied()) {
                if (lastRound.getDefender().isAttacker) {
                    if (myCards.size() > 0) {
                        attacker = initPlayer(myCards.remove(0), true);
                    } else return;
                } else  {
                    if (enemyCards.size() > 0) {
                        attacker = initPlayer(enemyCards.remove(0), false);
                    } else return;
                }
            }
            if (lastRound.getAction().equals(Action.DOUBLE)) {
                roundGenerator(defender, attacker);
            } else {
                roundGenerator(attacker, defender);
            }
        }
    }

    private Player swapPlayer(Player defender) {
        return Player
                .builder()
                .isAttacker(defender.isAttacker)
                .myHp(defender.myHp)
                .card(defender.card)
                .build();
    }

    private Round getLastRound() {
        return rounds.get(rounds.size()-1);
    }

    private Player initPlayer(HeroCard card, boolean isAttacker) {
        return Player
                .builder()
                .card(card)
                .myHp(card.getStat().getMaxHp())
                .isAttacker(isAttacker)
                .build();
    }

    private void roundGenerator(Player attacker, Player defender) {
        Action action = attacker.getAction(defender.getCard());
        Integer damage = attacker.getDamage(action);
        defender.receiveDamage(damage);
        Round round = Round.builder()
                .attacker(attacker)
                .defender(defender)
                .action(action)
                .damage(damage)
                .build();
        rounds.add(round);
    }

}

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
        initialRound(me, enemy);
        roundGenerator(rebuildPlayer(me), rebuildPlayer(enemy));
        doBattle(myCards, enemyCards);
    }

    private void doBattle(List<HeroCard> myCards, List<HeroCard> enemyCards) {
        while (true) {
            Round lastRound = getLastRound();
            Player attacker = rebuildPlayer(lastRound.getDefender());
            Player defender = rebuildPlayer(lastRound.getAttacker());
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
                if(lastRound.getDefender().isDied()){
                    initNewRound(defender, attacker);
                }else{
                    roundGenerator(defender, attacker);
                }
            } else {
                if(lastRound.getDefender().isDied()){
                    initNewRound(attacker, defender);
                }else{
                    roundGenerator(attacker, defender);
                }

            }
        }
    }

    private Player rebuildPlayer(Player player) {
        return Player
                .builder()
                .isAttacker(player.isAttacker)
                .myHp(player.myHp)
                .uniqueId(player.getUniqueId())
                .build();
    }

    private Round getLastRound() {
        return rounds.get(rounds.size()-1);
    }

    private Player initPlayer(HeroCard card, boolean isAttacker) {
        return Player
                .builder()
                .uniqueId(card.getUniqueId())
                .myHp(card.getStat().getMaxHp())
                .isAttacker(isAttacker)
                .build();
    }

    private void initialRound(Player attacker, Player defender) {
        Round round = Round.builder()
                .attacker(attacker)
                .defender(defender)
                .action(Action.STARTBATTLE)
                .damage(null)
                .build();
        rounds.add(round);
    }

    private void initNewRound(Player attacker, Player defender) {
        Round round = Round.builder()
                .attacker(attacker)
                .defender(defender)
                .action(Action.KILLED)
                .damage(null)
                .build();
        rounds.add(round);
    }

    private void roundGenerator(Player attacker, Player defender) {
        HeroCard attackerCard = findHeroCard(attacker);
        HeroCard defenderCard = findHeroCard(defender);
        Action action = attacker.getAction(attackerCard, defenderCard);
        Integer damage = attacker.getDamage(attackerCard, action);
        defender.receiveDamage(damage);
        Round round = Round.builder()
                .attacker(attacker)
                .defender(defender)
                .action(action)
                .damage(damage)
                .build();
        rounds.add(round);
    }

    private HeroCard findHeroCard(Player player){
        if(player.isAttacker){
            return myArmy.getCards().stream().filter(x-> x.getUniqueId().equals(player.uniqueId)).findFirst().get();
        }
        else {
            return enemyArmy.getCards().stream().filter(x-> x.getUniqueId().equals(player.uniqueId)).findFirst().get();
        }
    }

}

package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import com.narxoz.rpg.quest.RewardSortedQuestIterator;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("  HOMEWORK 10 — THE ADVENTURERS' GUILD");
        System.out.println("  Iterator + Mediator");
        System.out.println("=".repeat(60));

        Hero zhanibek = new Hero("Zhanibek", 120, 30, 22, 14, 80);
        Hero elara    = new Hero("Elara",     90, 60, 16, 11, 120);
        List<Hero> party = Arrays.asList(zhanibek, elara);

        QuestLog log = new QuestLog();
        log.add(new Quest("Goblin Camp Raid",        QuestPriority.NORMAL, 60,  false));
        log.add(new Quest("Lost Caravan Escort",     QuestPriority.LOW,    40,  false));
        log.add(new Quest("Wyvern in the Pass",      QuestPriority.HIGH,   180, false));
        log.add(new Quest("Cursed Crypt Cleansing",  QuestPriority.HIGH,   220, false));
        log.add(new Quest("Demon Lord Sighted",      QuestPriority.URGENT, 500, true));
        log.add(new Quest("Herb Run for Apothecary", QuestPriority.LOW,    20,  false));
        log.add(new Quest("Bandit Lord Bounty",      QuestPriority.URGENT, 350, true));

        System.out.println();
        System.out.println("--- GUILD HALL ROSTER ---");
        GuildHall hall = new GuildHall();
        Quartermaster quartermaster = new Quartermaster("Borin",   hall);
        Scout         scout         = new Scout         ("Lyra",   hall);
        Healer        healer        = new Healer        ("Mira",   hall);
        Captain       captain       = new Captain       ("Aidos",  hall);

        List<?> roster = Arrays.asList(quartermaster, scout, healer, captain);
        System.out.println("  Roster size: " + roster.size());

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, log, hall);

        System.out.println();
        System.out.println("--- OPEN/CLOSED PROOF: 4TH ITERATOR (RewardSortedQuestIterator) ---");
        QuestIterator byReward = new RewardSortedQuestIterator(log);
        int top = 0;
        while (byReward.hasNext() && top < 3) {
            Quest q = byReward.next();
            top++;
            System.out.println("  Top reward #" + top + ": " + q.getTitle()
                    + " — " + q.getRewardGold() + "g [" + q.getPriority() + "]");
        }

        System.out.println();
        System.out.println("--- OPEN/CLOSED PROOF: NEW COLLEAGUE (Loremaster) ---");
        Loremaster loremaster = new Loremaster("Sage Orin", hall);
        loremaster.shareLore("lore",  "the Demon Lord answers to an old binding rune");
        loremaster.shareLore("curse", "the crypt's curse weakens at dawn");
        captain.issueOrder("history", "review past Demon Lord campaigns");

        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("  COUNCIL ADJOURNS");
        System.out.println("=".repeat(60));
        System.out.println("  Total dispatches by GuildHall: " + hall.getTotalDispatches());

        System.out.println();
        System.out.println("====== COUNCIL RUN RESULT ======");
        System.out.println("  " + result);
    }
}
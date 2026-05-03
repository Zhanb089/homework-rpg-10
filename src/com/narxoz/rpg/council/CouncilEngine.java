package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0;
        int messagesRouted = 0;
        int membersNotified = 0;

        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("  THE ADVENTURERS' GUILD — WAR COUNCIL CONVENES");
        System.out.println("=".repeat(60));
        System.out.println("  Party at the table:");
        for (Hero h : party) {
            System.out.println("    - " + h.getName()
                    + " | HP: " + h.getHp() + "/" + h.getMaxHp()
                    + " | ATK: " + h.getAttackPower()
                    + " | DEF: " + h.getDefense()
                    + " | Gold: " + h.getGold());
        }
        System.out.println("  Quests on the board: " + questLog.size());

        System.out.println();
        System.out.println("--- PHASE 1: FULL QUEST LOG REVIEW (ordered) ---");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            System.out.println("  > Quest #" + questsTraversed + ": " + q.getTitle()
                    + " [" + q.getPriority() + ", reward " + q.getRewardGold() + "g]");
            // Coordinating message: scout reports the route
            hall.dispatch("scouting", null,
                    "scouting route to '" + q.getTitle() + "'");
            messagesRouted++;
            membersNotified += notifiedFrom(hall);
        }

        System.out.println();
        System.out.println("--- PHASE 2: HIGH-PRIORITY DISPATCH (priority >= HIGH) ---");
        QuestIterator highOnly = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (highOnly.hasNext()) {
            Quest q = highOnly.next();
            questsTraversed++;
            System.out.println("  > High-priority: " + q.getTitle()
                    + " [" + q.getPriority() + ", reward " + q.getRewardGold() + "g]");

            if (q.isUrgent() || q.getPriority() == QuestPriority.URGENT) {
                hall.dispatch("urgent", null,
                        "URGENT mobilization for '" + q.getTitle() + "'");
                messagesRouted++;
                membersNotified += notifiedFrom(hall);
            } else {
                hall.dispatch("supplies", null,
                        "stock loadout for '" + q.getTitle() + "'");
                messagesRouted++;
                membersNotified += notifiedFrom(hall);

                hall.dispatch("healing", null,
                        "prep aid kit for '" + q.getTitle() + "'");
                messagesRouted++;
                membersNotified += notifiedFrom(hall);
            }
        }

        System.out.println();
        System.out.println("--- PHASE 3: REVERSE WALK (newest first) ---");
        QuestIterator reverse = questLog.reverse();
        int reverseCount = 0;
        while (reverse.hasNext() && reverseCount < 3) {
            Quest q = reverse.next();
            questsTraversed++;
            reverseCount++;
            System.out.println("  > Latest arrival #" + reverseCount + ": " + q.getTitle());
            hall.dispatch("orders", null,
                    "captain reviews newest arrival '" + q.getTitle() + "'");
            messagesRouted++;
            membersNotified += notifiedFrom(hall);
        }

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }

    private int notifiedFrom(GuildMediator hall) {
        if (hall instanceof GuildHall gh) {
            return gh.getLastNotifiedCount();
        }
        return 0;
    }
}
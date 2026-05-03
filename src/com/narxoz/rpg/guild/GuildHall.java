package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();
    private int lastNotifiedCount = 0;
    private int totalDispatches = 0;

    @Override
    public void register(GuildMember member) {
        if (member == null) return;
        
        if (member instanceof Quartermaster) {
            addSubscriber("supplies", member);
            addSubscriber("rewards", member);
            addSubscriber("orders", member);
        } else if (member instanceof Scout) {
            addSubscriber("scouting", member);
            addSubscriber("urgent", member);
            addSubscriber("orders", member);
        } else if (member instanceof Healer) {
            addSubscriber("healing", member);
            addSubscriber("urgent", member);
            addSubscriber("orders", member);
        } else if (member instanceof Captain) {
            addSubscriber("orders", member);
            addSubscriber("urgent", member);
            addSubscriber("scouting", member);
        } else if (member instanceof Loremaster) {
            addSubscriber("lore", member);
            addSubscriber("curse", member);
            addSubscriber("history", member);
        } else {
            // Fallback for unknown member types — universal channel
            addSubscriber("orders", member);
        }
        System.out.println("  [GuildHall] " + member.getName()
                + " registered on the council board");
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        totalDispatches++;
        List<GuildMember> subs = subscribersFor(topic);
        int notified = 0;
        System.out.println("  [GuildHall] dispatch on <" + topic + "> from "
                + (from == null ? "council" : from.getName())
                + ": \"" + payload + "\"");
        for (GuildMember m : subs) {
            if (m == from) continue; 
            m.receive(topic, from, payload);
            notified++;
        }
        if (notified == 0) {
            System.out.println("  [GuildHall] (no subscribers reacted)");
        }
        lastNotifiedCount = notified;
    }

    public int getLastNotifiedCount() {
        return lastNotifiedCount;
    }

    public int getTotalDispatches() {
        return totalDispatches;
    }

    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}
package com.narxoz.rpg.guild;

public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "healing":
                System.out.println("    [Healer " + getName()
                        + "] brewing potions and bandages: " + payload);
                break;
            case "urgent":
                System.out.println("    [Healer " + getName()
                        + "] readying emergency triage kit: " + payload);
                break;
            case "orders":
                System.out.println("    [Healer " + getName()
                        + "] understood, will support the party: " + payload);
                break;
            default:
                System.out.println("    [Healer " + getName()
                        + "] noted on <" + topic + ">: " + payload);
                break;
        }
    }
}
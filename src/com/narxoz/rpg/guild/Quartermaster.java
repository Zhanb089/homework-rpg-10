package com.narxoz.rpg.guild;

public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "supplies":
                System.out.println("    [Quartermaster " + getName()
                        + "] preparing crates and rations: " + payload);
                break;
            case "rewards":
                System.out.println("    [Quartermaster " + getName()
                        + "] earmarking gold and loot: " + payload);
                break;
            case "orders":
                System.out.println("    [Quartermaster " + getName()
                        + "] acknowledging order from "
                        + (from == null ? "council" : from.getName())
                        + ": " + payload);
                break;
            default:
                System.out.println("    [Quartermaster " + getName()
                        + "] noted on <" + topic + ">: " + payload);
                break;
        }
    }
}
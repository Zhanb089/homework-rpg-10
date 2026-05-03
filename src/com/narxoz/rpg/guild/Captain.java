package com.narxoz.rpg.guild;

public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "orders":
                if (from == this) return;
                System.out.println("    [Captain " + getName()
                        + "] confirms order: " + payload);
                break;
            case "urgent":
                System.out.println("    [Captain " + getName()
                        + "] !! URGENT signal received from "
                        + (from == null ? "council" : from.getName())
                        + ": " + payload);
                break;
            case "scouting":
                System.out.println("    [Captain " + getName()
                        + "] reviewing scout report: " + payload);
                break;
            default:
                System.out.println("    [Captain " + getName()
                        + "] noted on <" + topic + ">: " + payload);
                break;
        }
    }
}
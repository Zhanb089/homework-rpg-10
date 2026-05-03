package com.narxoz.rpg.guild;

public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "scouting":
                System.out.println("    [Scout " + getName()
                        + "] mapping the route: " + payload);
                break;
            case "urgent":
                System.out.println("    [Scout " + getName()
                        + "] sprinting ahead — URGENT recon: " + payload);
                break;
            case "orders":
                System.out.println("    [Scout " + getName()
                        + "] standing by for orders: " + payload);
                break;
            default:
                System.out.println("    [Scout " + getName()
                        + "] noted on <" + topic + ">: " + payload);
                break;
        }
    }
}
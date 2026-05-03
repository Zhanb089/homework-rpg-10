package com.narxoz.rpg.guild;

public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void shareLore(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "lore":
                System.out.println("    [Loremaster " + getName()
                        + "] consulting the tomes: " + payload);
                break;
            case "curse":
                System.out.println("    [Loremaster " + getName()
                        + "] tracing the curse pattern: " + payload);
                break;
            case "history":
                System.out.println("    [Loremaster " + getName()
                        + "] cross-referencing chronicles: " + payload);
                break;
            default:
                System.out.println("    [Loremaster " + getName()
                        + "] noted on <" + topic + ">: " + payload);
                break;
        }
    }
}
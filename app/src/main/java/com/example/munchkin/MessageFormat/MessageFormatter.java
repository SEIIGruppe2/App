package com.example.munchkin.MessageFormat;


public class MessageFormatter {

    public static String createPlayerAttackMessage(String monsterId, String cardTypePlayed) {
        return createMessage("PLAYER_ATTACK", "monsterid", monsterId, "cardTypePlayed", cardTypePlayed);
    }

    public static String createMonsterAttackMessage(String monsterId) {
        return createMessage("MONSTER_ATTACK", "monsterid", monsterId);
    }

    public static String createSwitchCardsDeckMessage(String card) {
        return createMessage("SWITCH_CARD_DECK", "cardid", card);
    }

    public static String createSwitchCardsPlayerMessage(String switchedWith, String cardGiven, String cardGotten) {
        return createMessage("SWITCH_CARD_PLAYER", "switchedWith", switchedWith, "cardGiven", cardGiven, "cardGivenP", cardGotten);
    }

    public static String createDrawCardMessage() {
        return createMessage("DRAW_CARD");
    }


    public static String registerUserMessage(String username) {
        return createMessage("REGISTER_USERNAME", "username",username);
    }


    public static String createUsernameRequestMessage() {
        return createMessage("REQUEST_USERNAMES");
    }

    public static String createSpawnMonsterMessage(String zone) {
        return createMessage("SPAWN_MONSTER", "zone", zone);
    }

    public static String createPlayerRollDiceMessage() {
        return createMessage("PLAYER_ROLL_DICE");
    }

    public static String createRequestRoundMessage() {
        return createMessage("ROUND_COUNTER");
    }

    public static String createEndTurnMessage() {
        return createMessage("END_TURN");
    }

    private static String createMessage(String type, String... params) {
        StringBuilder message = new StringBuilder("{\"type\":\"" + type + "\"");
        for (int i = 0; i < params.length; i += 2) {
            message.append(",\"").append(params[i]).append("\":\"").append(params[i + 1]).append("\"");
        }
        message.append("}");
        return message.toString();
    }



}

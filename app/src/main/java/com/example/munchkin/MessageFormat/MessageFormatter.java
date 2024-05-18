package com.example.munchkin.MessageFormat;


public class MessageFormatter {

    public static String createPlayerAttackMessage(String monsterId, String cardTypePlayed) {
        return createMessage("PLAYER_ATTACK", "monsterid", monsterId, "cardTypePlayed", cardTypePlayed);
    }

    public static String createMonsterAttackMessage(String monsterId, String towerId) {
        return createMessage("MONSTER_ATTACK", "monsterid", monsterId, "towerid", towerId);
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
  public static String createUsernameForSwitchRequestMessage(){
      return createMessage("REQUEST_USERNAMES_SWITCH");
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

    public static String createEndTurnMessage(String currentturn) {
        return createMessage("END_TURN", "turn",currentturn);
    }

    public static String createShowMonsterMessage(String id){
        return createMessage("SHOW_MONSTERS", "cardid",id);
    }

    public static String createCardAttackMonsterMessage(String monsterId, String cardId) {
        return createMessage("CARD_ATTACK_MONSTER", "monsterid", monsterId, "cardid", cardId);
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

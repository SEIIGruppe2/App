package com.example.munchkin.DrawableUtils;



import com.example.munchkin.DTO.ActionCardDTO;


import java.util.List;

public class CardUtils {


    private CardUtils() {}




    public static String[] getresources(List<ActionCardDTO> handkarten){
        String[]result = new String[handkarten.size()];
        String name;
        String zone;
        for(int i=0; i<result.length;i++){


            name=getcharacter(handkarten.get(i).getName());
            zone=getzone(handkarten.get(i).getZone());
            result[i]=zone+name;
        }


    return result;}

    public static String getcharacter(String name){
        switch (name) {
            case "Bogenschütze":
                return "bogenschuetze";
            case "Schwertkämpfer":
                return "schwertkaempfer";
            case "Held":
                return "held";
            case "Ritter":
                return "ritter";
            default:
                return "held";
        }
    }
    public static String getzone(int zone){
        switch (zone) {
            case 0:
                return "roter";
            case 1:
                return "blauer";
            case 2:
                return "gruener";
            case 3:
                return "brauner";
            default:
                return "blauer";
        }
    }





}

package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class NameConverter {

    public static HashMap<String, String> names = new HashMap<>();

    public static void main(String[] args) {
        names.put("MT_EXTERMINATION", "Extermination");
        names.put("MT_SABOTAGE", "Sabotage");
        names.put("MT_SURVIVAL", "Survival");
        names.put("MT_DEFENSE", "Defense");
        names.put("MT_SABOTAGE", "Sabotage");
        names.put("MT_INTEL", "Spy");
        names.put("MT_ASSASSINATION", "Assassination");
        names.put("MT_EXCAVATE", "Excavation");
        names.put("MT_RETRIEVAL", "Retrieval");
        names.put("MT_CAPTURE", "Capture");
        names.put("MT_MOBILE_DEFENSE", "Mobile Defense");
        names.put("MT_TERRITORY", "Interception");
        names.put("MT_RESCUE", "Rescue");

        //Faction Names
        names.put("FC_OROKIN", "Orokin");
        names.put("FC_INFESTATION", "Infestation");
        names.put("FC_GRINEER", "Grineer");
        names.put("FC_CORPUS", "Corpus");

        //Helmets
        names.put("Statless Mag Alt Helmet Blueprint", "Coil Mag Helmet Blueprint");
        names.put("Statless V2Vauban Alt Helmet Blueprint", "Gambit Vauban Helmet Blueprint");
        names.put("Harlequin Alt Helmet Blueprint", "Harlequin Mirage Helmet Blueprint");
        names.put("Statless Trinity Alt Helmet Blueprint", "Trinity Aura Helmet Blueprint");
        names.put("Statless Excalibur Alt Helmet Blueprint", "Avalon Excalibur Helmet Blueprint");
        names.put("Statless Volt Alt Helmet Blueprint", "Storm Volt Helmet Blueprint");

        //Other
        names.put("Alert Fusion Bundle Small", "Small Endo Bundle (80)");
        names.put("Alert Fusion Bundle Medium", "Medium Endo Bundle (100)");
        names.put("Alert Fusion Bundle Large", "Large Endo Bundle (150)");
        names.put("Grn Axe Blueprint", "Manticore Axe Skin Blueprint");
        names.put("Player Pistol Ammo Aura Mod", "Pistol Scavenger (Aura Mod)");
        names.put("Alertium", "Nitain Extract");
        names.put("Void Tear Drop", "Void Traces");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("./src/test/resources/names.json"), names);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

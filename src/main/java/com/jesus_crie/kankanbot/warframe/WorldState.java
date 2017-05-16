package com.jesus_crie.kankanbot.warframe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldState {

    private List<Alert> alerts;
    private List<Sortie> sorties;

    @JsonCreator
    public WorldState(@JsonProperty("Alerts") List<Alert> as,
                      @JsonProperty("Sorties") List<Sortie> se) {
        alerts = as;
        sorties = se;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public boolean alertExistForId(String id) {
        for (Alert a : alerts)
            if (a.getId().equalsIgnoreCase(id))
                return true;
        return false;
    }

    public List<Sortie> getSorties() {
        return sorties;
    }

    public boolean sortieExistForId(String id) {
        for (Sortie s : sorties)
            if (s.getId().equalsIgnoreCase(id))
                return true;
        return false;
    }
}

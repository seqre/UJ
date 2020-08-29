package uj.jwzp2019.hellospring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {
    private String name;
    private long diameter;
    private String climate;
    private String terrain;
    private long population;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDiameter() {
        return diameter;
    }

    public void setDiameter(long diameter) {
        this.diameter = diameter;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @JsonProperty("diameter")
    private void deserializeDiameter(String s) {
        try {
            diameter = Long.parseLong(s);
        } catch (NumberFormatException e) {
            diameter = 0;
        }
    }

    @JsonProperty("population")
    private void deserializePopulation(String s) {
        try {
            population = Long.parseLong(s);
        } catch (NumberFormatException e) {
            population = 0;
        }
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", diameter=" + diameter +
                ", climate='" + climate + '\'' +
                ", terrain='" + terrain + '\'' +
                ", population=" + population +
                '}';
    }

}

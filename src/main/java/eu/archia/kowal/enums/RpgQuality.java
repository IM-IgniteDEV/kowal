package eu.archia.kowal.enums;

import eu.archia.kowal.Kowal;

public enum RpgQuality {
    LOSOWANIE(0d),
    NORMALNY(1d),
    RZADKI(Kowal.getInst().getConfig().getDouble("rare")),
    EPICKI(Kowal.getInst().getConfig().getDouble("epic")),
    LEGENDARNY(Kowal.getInst().getConfig().getDouble("legendary")),
    ARTEFAKT(Kowal.getInst().getConfig().getDouble("artifact"));

    private double multiplier;
    RpgQuality(double multiplier){
        this.multiplier = multiplier;
    }

    public double getMultiplier(){
        return this.multiplier;
    }

    public static RpgQuality matchQality(String cls){
        return valueOf(cls.toUpperCase());
    }
}

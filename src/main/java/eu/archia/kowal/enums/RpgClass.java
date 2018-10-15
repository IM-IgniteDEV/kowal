package eu.archia.kowal.enums;

public enum RpgClass {
    UNIWERSALNY, WOJOWNIK, PALADYN, MAG;

    public static RpgClass matchClass(String cls){
        return valueOf(cls.toUpperCase());
    }
}

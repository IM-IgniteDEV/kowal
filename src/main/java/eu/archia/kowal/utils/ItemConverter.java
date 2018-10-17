package eu.archia.kowal.utils;

import eu.archia.kowal.Kowal;
import eu.archia.kowal.enums.ItemType;
import eu.archia.kowal.enums.RpgClass;
import eu.archia.kowal.enums.RpgQuality;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemConverter {
    private static YamlConfiguration kowal = File.getKowal();

    private ItemType type = ItemType.OTHER;
    private RpgQuality quality = RpgQuality.LOSOWANIE;
    private RpgClass cls = RpgClass.UNIWERSALNY;
    private int upgrade = 0;
    private int level = 1;

    private String name;
    private String rawName;

    private List<ItemStack> items = new ArrayList<>();
    private double chance = Kowal.getInst().getConfig().getDouble("defChance");
    private int maxValue = 1;
    private double dispersion = 0.5;
    private boolean isException = false;

    private ItemStack defaultItemStack;

    public ItemConverter(ItemStack is) {
        if (is.hasItemMeta()) {
            if (is.getItemMeta().hasDisplayName() && is.getItemMeta().hasLore()) {
                this.defaultItemStack = is;
                List<String> lore = is.getItemMeta().getLore();
                // przypisanie podstawowych zmiennych
                lore.stream().filter(line -> line.contains("Jakosc:")).findFirst().ifPresent(line -> {
                    String l1 = line.replaceAll(ChatColor.translateAlternateColorCodes('&', "&7Jakosc: "), "");
                    quality = RpgQuality.matchQality(l1);
                });
                lore.stream().filter(line -> line.contains("Klasa:")).findFirst().ifPresent(line -> {
                    String l1 = line.replaceAll(ChatColor.translateAlternateColorCodes('&', "&7Klasa: "), "");
                    cls = RpgClass.matchClass(l1);
                });
                lore.stream().filter(line -> line.contains("Od Poziomu:")).findFirst().ifPresent(line -> {
                    String l1 = line.replaceAll(ChatColor.translateAlternateColorCodes('&', "&7Od Poziomu: "), "");
                    level = Integer.parseInt(l1);
                });

                String namee = is.getItemMeta().getDisplayName();
                String split[] = namee.split("\\+");
                upgrade = Integer.parseInt(split[1]);
                name = split[0].replace(ChatColor.translateAlternateColorCodes('&', "&a&l"), "");
                rawName = name.replaceAll(Utils.colored("&f"), "").replaceAll(Utils.colored("&6"), "").replaceAll(Utils.colored("&l"), "").replaceAll(" ", "_");
                rawName = rawName.replace(rawName.substring(rawName.length() - 1), "");
                upgrade = Integer.parseInt(namee.split(ChatColor.translateAlternateColorCodes('&', "+"))[1]);
                if (kowal.contains("exception." + rawName)) {
                    isException = true;
                }
                //  tworzenie sekcji kowal.yml dla zdefiniowanych lub defaultowych itemow
                ConfigurationSection section;
                if (isException) {
                    section = kowal.getConfigurationSection("exception." + rawName);
                } else {
                    section = kowal.getConfigurationSection("default." + level + "l");
                }
                // sprawdzenie czy itemstack jest armorem bizu czy bronia
                String raw = rawName.toLowerCase();
                if (raw.contains("kolczyki") || raw.contains("tarcza") || raw.contains("pierscien") || raw.contains("naszyjnik")) {
                    type = ItemType.JEWEL;
                } else if (lore.stream().anyMatch(line -> line.contains("Obrona:"))) {
                    type = ItemType.ARMOR;
                } else if (lore.stream().anyMatch(line -> line.contains("Atak:"))) {
                    type = ItemType.WEAPON;
                }
                // ustalanie maksymalnych wartosci z kowal.yml dla zbroj broni i bizuterii
                if (type.equals(ItemType.ARMOR)) {
                    this.maxValue = section.getInt("maxValueArmor");
                } else if (type.equals(ItemType.WEAPON)) {
                    this.maxValue = section.getInt("maxValueWeapon");
                } else if (type.equals(ItemType.JEWEL)) {
                    this.maxValue = section.getInt("maxValueJewel");
                }
                // obliczanie szansy z kowal.yml (dla wartosci opcjonalnej) lub config.yml (dla warosci defaultowej)
                if (section.contains(upgrade + "p.chance")) {
                    this.chance = section.getDouble(upgrade + "p.chance");
                } else {
                    double ch = Kowal.getInst().getConfig().getDouble("defChance");
                    this.chance = ch - (upgrade * Kowal.getInst().getConfig().getDouble("chancePerLevel"));
                }
                if (section.contains("dispersion")) {
                    this.dispersion = section.getDouble("dispersion");
                } else {
                    this.dispersion = Kowal.getInst().getConfig().getDouble("defDispersion");
                }

                // pobieranie itemow potrzebnych do ulepszenia jak i ich ilosci z kowal.yml
                section.getStringList(upgrade + "p.items").forEach(line -> {
                    String[] split2 = line.split("%");
                    ItemStack iss = File.getItemBase().getItemStack(split2[1]);
                    iss.setAmount(Integer.parseInt(split2[0]));
                    items.add(iss);
                });
            }
        }
    }

    public boolean isException() {
        return isException;
    }

    public double getChance() {
        return chance;
    }

    public int getLevel() {
        return level;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public ItemType getType() {
        return type;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public RpgQuality getQuality() {
        return quality;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public String getName() {
        return name;
    }

    public String getRawName() {
        return rawName;
    }

    public ItemStack getDefaultItemStack() {
        return defaultItemStack;
    }

    public RpgClass getRpgClass() {
        return cls;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public void setRpgClass(RpgClass cls) {
        this.cls = cls;
    }

    public void setDefaultItemStack(ItemStack defaultItemStack) {
        this.defaultItemStack = defaultItemStack;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuality(RpgQuality quality) {
        this.quality = quality;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public ItemStack build() {
        if(defaultItemStack == null){
            return null;
        }
        ItemMeta meta = defaultItemStack.getItemMeta();
        //                                          tu byla ucieczka &a&l\\+'
        meta.setDisplayName(Utils.colored(name + "&a&l+" + upgrade));
        List<String> lore = meta.getLore();

        lore = lore.stream().map(this::buildLine).collect(Collectors.toList());

        meta.setLore(lore);
        defaultItemStack.setItemMeta(meta);
        return defaultItemStack;
    }

    private String buildLine(String s) {
        int value = (int) Math.round(maxValue / 9 * upgrade * quality.getMultiplier());
        if (s.contains("Atak: +")) {
            s = Utils.colored("&7Atak: +" + (value * dispersion) + "-" + value);
        } else if (s.contains("Obrona: ")) {
            s = Utils.colored("&7Obrona: +" + value);
        } else if (s.contains("Klasa: ")) {
            if (cls.equals(RpgClass.UNIWERSALNY)) {
                s = Utils.colored("&7Klasa: Uniwersalny");
            } else if (cls.equals(RpgClass.MAG)) {
                s = Utils.colored("&7Klasa: Mag");
            } else if (cls.equals(RpgClass.WOJOWNIK)) {
                s = Utils.colored("&7Klasa: Wojownik");
            } else if (cls.equals(RpgClass.PALADYN)) {
                s = Utils.colored("&7Klasa: Paladyn");
            }
        } else if (s.contains("Od Poziomu:")) {
            s = Utils.colored("&7Od Poziomu: "+level);
        }
        else if (s.contains("Jakosc:")) {
            if (quality.equals(RpgQuality.LOSOWANIE)) {
                s = Utils.colored("&7Jakosc: Losowanie");
            } else if (quality.equals(RpgQuality.NORMALNY)) {
                s = Utils.colored("&7Jakosc: Normalny");
            } else if (quality.equals(RpgQuality.RZADKI)) {
                s = Utils.colored("&7Jakosc: Rzadki");
            } else if (quality.equals(RpgQuality.EPICKI)) {
                s = Utils.colored("&7Jakosc: Epicki");
            }else if (quality.equals(RpgQuality.LEGENDARNY)) {
                s = Utils.colored("&7Jakosc: Legendarny");
            }else if (quality.equals(RpgQuality.ARTEFAKT)) {
                s = Utils.colored("&7Jakosc: Artefakt");
            }
        }

        return s;
    }
}

package com.example.test3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//i spent a solid four hours trying to get this idea to work
//one hour on stackoverflow, another hour and a half on reddit, and god only knows how long
//scrolling through android studio documentation
//but finally... i THINK we got it...

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {

    private static final String WEAPON_DATA_FILE = "weapon_data.xml";
    private static final String ARMOR_DATA_FILE = "armor_data.xml";
    private static final String TRINKET_DATA_FILE = "trinket_data.xml";

    public static List<Weapon> loadWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(WEAPON_DATA_FILE));
            NodeList weaponNodes = document.getElementsByTagName("weapon");
            for (int i = 0; i < weaponNodes.getLength(); i++) {
                Node weaponNode = weaponNodes.item(i);
                if (weaponNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element weaponElement = (Element) weaponNode;
                    String name = weaponElement.getElementsByTagName("name").item(0).getTextContent();
                    int attackBonus = Integer.parseInt(weaponElement.getElementsByTagName("attackBonus").item(0).getTextContent());
                    weapons.add(new Weapon(name, "", 0, 0, attackBonus));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return weapons;
    }

    public static List<Armor> loadArmor() {
        List<Armor> armors = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(ARMOR_DATA_FILE));
            NodeList armorNodes = document.getElementsByTagName("item");
            for (int i = 0; i < armorNodes.getLength(); i++) {
                Node armorNode = armorNodes.item(i);
                if (armorNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element armorElement = (Element) armorNode;
                    String name = armorElement.getElementsByTagName("name").item(0).getTextContent();
                    int defenseBonus = Integer.parseInt(armorElement.getElementsByTagName("defenseBonus").item(0).getTextContent());
                    String armorWeightStr = armorElement.getElementsByTagName("armorWeight").item(0).getTextContent();
                    Armor.ArmorWeight armorWeight = Armor.ArmorWeight.valueOf(armorWeightStr);
                    armors.add(new Armor(name, "", 0, 0, defenseBonus, armorWeight));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return armors;
    }

    public static List<Trinket> loadTrinkets() {
        List<Trinket> trinkets = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(TRINKET_DATA_FILE));
            NodeList trinketNodes = document.getElementsByTagName("item");

            int totalWeight = 0;
            List<WeightedTrinketData> weightedTrinketData = new ArrayList<>();

            for (int i = 0; i < trinketNodes.getLength(); i++) {
                Node trinketNode = trinketNodes.item(i);
                if (trinketNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element trinketElement = (Element) trinketNode;
                    String name = trinketElement.getElementsByTagName("name").item(0).getTextContent();
                    int statBonus = Integer.parseInt(trinketElement.getElementsByTagName("statBonus").item(0).getTextContent());
                    int weight = Integer.parseInt(trinketElement.getElementsByTagName("weight").item(0).getTextContent());
                    TrinketType type = TrinketType.valueOf(trinketElement.getElementsByTagName("type").item(0).getTextContent());
                    totalWeight += weight;
                    weightedTrinketData.add(new WeightedTrinketData(name, statBonus, weight, type));
                }
            }

            for (WeightedTrinketData data : weightedTrinketData) {
                int roll = (int) (Math.random() * totalWeight);
                int cumulativeWeight = 0;
                for (WeightedTrinketData item : weightedTrinketData) {
                    cumulativeWeight += item.weight;
                    if (roll < cumulativeWeight) {
                        switch (data.type) {
                            case HEALTH_REGEN:
                                trinkets.add(new HealthRegenTrinket(data.name, "", data.value, 0));
                                break;
                            case DAMAGE_OVER_TIME:
                                trinkets.add(new DamageOverTimeTrinket(data.name, "", data.value, 0));
                                break;
                            case EXTRA_LIFE:
                                trinkets.add(new ExtraLifeTrinket(data.name, "", data.value, 0));
                                break;
                        }
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return trinkets;
    }

    private static class WeightedTrinketData {
        String name;
        int value;
        int weight;
        TrinketType type;

        WeightedTrinketData(String name, int value, int weight, TrinketType type) {
            this.name = name;
            this.value = value;
            this.weight = weight;
            this.type = type;
        }
    }

    public enum TrinketType {
        HEALTH_REGEN,
        DAMAGE_OVER_TIME,
        EXTRA_LIFE
    }
}
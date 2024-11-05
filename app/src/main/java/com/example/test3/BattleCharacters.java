package com.example.test3;

public class BattleCharacters {
    Character character;  // This can be either a Hero or an Enemy
    SpriteSheetImageView characterView;  // The corresponding view

    BattleCharacters(Character character, SpriteSheetImageView characterView) {
        this.character = character;
        this.characterView = characterView;
    }

    public String getName() { return character.getName(); }

}

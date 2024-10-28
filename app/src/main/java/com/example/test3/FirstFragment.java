package com.example.test3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.test3.databinding.FragmentFirstBinding;

import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private SpriteSheetImageView spriteSheetImageViewHero;
    private SpriteSheetImageView spriteSheetImageViewGoblin;
    private SpriteSheetImageView spriteSheetImageViewEliteGoblin;
    private Hero hero;
    private Enemy goblin;
    private Enemy eliteGoblin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        spriteSheetImageViewHero = binding.spriteImageViewHero; // Use binding to reference views
        spriteSheetImageViewGoblin = binding.spriteImageViewGoblin;
        spriteSheetImageViewEliteGoblin = binding.spriteImageViewEliteGoblin;

        // Define sprite sheets for the knight character
        Map<Character.SpriteState, String> knightSpriteSheets = new HashMap<>();
        knightSpriteSheets.put(Character.SpriteState.IDLE, "knight_idle");
        knightSpriteSheets.put(Character.SpriteState.ATTACK, "knight_attack01");
        knightSpriteSheets.put(Character.SpriteState.HIT, "knight_hurt");
        knightSpriteSheets.put(Character.SpriteState.DEATH, "knight_death");
        // Define sprite sheet frames for states
        Map<Character.SpriteState, Integer> knightFrameCounts = new HashMap<>();
        knightFrameCounts.put(Character.SpriteState.IDLE, 6);     // 4 frames for IDLE
        knightFrameCounts.put(Character.SpriteState.ATTACK, 7);   // 6 frames for ATTACK
        knightFrameCounts.put(Character.SpriteState.HIT, 4);      // 2 frames for HIT
        knightFrameCounts.put(Character.SpriteState.DEATH, 4);    // 5 frames for Death

        Hero knight = new Hero("Knight", 200, 30, 10, 1, 10, 4, 5, 0, "Knight", knightSpriteSheets, knightFrameCounts);

        // Define sprite sheets for the goblin character
        Map<Character.SpriteState, String> goblinSpriteSheets = new HashMap<>();
        goblinSpriteSheets.put(Character.SpriteState.IDLE, "orc_idle");
        goblinSpriteSheets.put(Character.SpriteState.ATTACK, "orc_attack01");
        goblinSpriteSheets.put(Character.SpriteState.HIT, "orc_hurt");
        goblinSpriteSheets.put(Character.SpriteState.DEATH, "orc_death");

        Map<Character.SpriteState, Integer> goblinFrameCounts = new HashMap<>();
        goblinFrameCounts.put(Character.SpriteState.IDLE, 6);
        goblinFrameCounts.put(Character.SpriteState.ATTACK, 6);
        goblinFrameCounts.put(Character.SpriteState.HIT, 4);
        goblinFrameCounts.put(Character.SpriteState.DEATH, 4);

        Enemy goblin = new Enemy("Goblin", 100, 20, 5, 1, 10, 5, 5, 0, false, goblinSpriteSheets, goblinFrameCounts);

        // Define sprite sheets for the elite goblin character
        Map<Character.SpriteState, String> goblinEliteSpriteSheets = new HashMap<>();
        goblinEliteSpriteSheets.put(Character.SpriteState.IDLE, "orc_rider_idle");
        goblinEliteSpriteSheets.put(Character.SpriteState.ATTACK, "orc_rider_attack01");
        goblinEliteSpriteSheets.put(Character.SpriteState.HIT, "orc_rider_hurt");
        goblinEliteSpriteSheets.put(Character.SpriteState.DEATH, "orc_rider_death");

        Map<Character.SpriteState, Integer> goblinEliteFrameCounts = new HashMap<>();
        goblinEliteFrameCounts.put(Character.SpriteState.IDLE, 6);
        goblinEliteFrameCounts.put(Character.SpriteState.ATTACK, 8);
        goblinEliteFrameCounts.put(Character.SpriteState.HIT, 4);
        goblinEliteFrameCounts.put(Character.SpriteState.DEATH, 4);

        Enemy eliteGoblin = new Enemy("Goblin Warlord", 150, 25, 10, 1, 15, 6, 7, 10, true, goblinEliteSpriteSheets, goblinFrameCounts);

        // Set the hero’s initial state and display it
        spriteSheetImageViewHero.setCharacter(knight);
        spriteSheetImageViewGoblin.setCharacter(goblin);
        spriteSheetImageViewEliteGoblin.setCharacter(eliteGoblin);

        // **Initialize battles with sprite views for hero and enemies**
        Battle battle1 = new Battle(knight, goblin, spriteSheetImageViewHero, spriteSheetImageViewGoblin);
        Battle battle2 = new Battle(knight, eliteGoblin, spriteSheetImageViewHero, spriteSheetImageViewEliteGoblin);

        // **Start the battles sequentially**
        //battle1.start();
        //battle2.start();

        return binding.getRoot();
    }

    // Method to update SpriteSheetImageView based on the character's state
    /*private void updateSpriteImageView(Character character) {
        // Set character's state to IDLE (or another state as needed)
        character.setSpriteState(Character.SpriteState.IDLE);

        // Get the current sprite resource based on the character's state
        String spriteResource = character.getCurrentSpriteSheetResource();
        int resourceId = getResources().getIdentifier(spriteResource, "drawable", requireActivity().getPackageName());
        spriteSheetImageView.setImageResource(resourceId);

        // Set the SpriteSheetImageView's state for animation control
        spriteSheetImageView.setSpriteState(character.getSpriteState());
    }*/

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

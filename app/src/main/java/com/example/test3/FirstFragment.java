package com.example.test3;

import android.os.Bundle;
import android.os.Handler;
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
    private final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        spriteSheetImageViewHero = binding.spriteImageViewHero; // Use binding to reference views
        spriteSheetImageViewGoblin = binding.spriteImageViewGoblin;
        spriteSheetImageViewEliteGoblin = binding.spriteImageViewEliteGoblin;

       Hero warrior = CharacterFactory.createWarrior();

       Enemy goblin = CharacterFactory.createGoblin();
       Enemy eliteGoblin = CharacterFactory.createGoblinElite();


        // Set the hero’s initial state and display it
        spriteSheetImageViewHero.setCharacter(warrior);
        spriteSheetImageViewGoblin.setCharacter(goblin);
        spriteSheetImageViewEliteGoblin.setCharacter(eliteGoblin);

        // **Initialize battles with sprite views for hero and enemies**
        Battle battle1 = new Battle(warrior, goblin, spriteSheetImageViewHero, spriteSheetImageViewGoblin);
        Battle battle2 = new Battle(warrior, eliteGoblin, spriteSheetImageViewHero, spriteSheetImageViewEliteGoblin);

        // **Start the battles sequentially**
        //battle1.start();
        //battle2.start();

        // Schedule the second battle with a delay (e.g., 5 seconds after the first battle starts)
        handler.postDelayed(battle2::start, 2000);  // 5000 ms = 5 seconds

        return binding.getRoot();
    }

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

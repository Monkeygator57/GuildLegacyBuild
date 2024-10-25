package com.example.test3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.test3.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(



            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {



        binding = FragmentFirstBinding.inflate(inflater, container, false);

        Hero knight = new Hero("Knight", 200, 30,10, 1, 10, 4, 5, 0, "Knight");

        Enemy goblin = new Enemy("Goblin", 100, 20, 5, 1, 10, 5, 5, 0, false);
        Enemy eliteGoblin = new Enemy("Goblin Warlord", 100, 20, 5, 1, 10, 5,5,10,true);

        Battle battle1 = new Battle(knight, goblin);
        battle1.start();

        Battle battle2 = new Battle(knight, eliteGoblin);
        battle2.start();

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        //Reference the SpriteSheetImageView in this Fragment
        SpriteSheetImageView spriteSheetImageView = view.findViewById(R.id.spriteImageView);
        // The animation will start automatically when drawn
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
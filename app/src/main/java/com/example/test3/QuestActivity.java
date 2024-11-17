package com.example.test3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class QuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        // Sample quest data
        List<Quest> questList = new ArrayList<>();
        questList.add(new Quest("Save the Village", "Defeat the goblins attacking the village."));
        questList.add(new Quest("Find the Lost Artifact", "Locate the ancient artifact in the forest."));
        questList.add(new Quest("Rescue the Princess", "Rescue the princess from the dragon's lair."));

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.quests_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new QuestAdapter(questList));
    }
}

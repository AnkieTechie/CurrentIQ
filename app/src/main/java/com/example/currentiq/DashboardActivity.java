package com.example.currentiq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.currentiq.databinding.ActivityDashboardBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    List<Subject> subjectList;
    DashboardAdapter dashboardAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearToolbar.setOnClickListener(v -> binding.drawer.open());

        // Change status bar color
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#FE190632"));

        binding.recycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        subjectList = new ArrayList<>();

        fetchSubjects();

        // Initialize the adapter with the listener
        dashboardAdapter = new DashboardAdapter(subjectList, sub -> startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("catId", sub.getname())));

        binding.recycler.setAdapter(dashboardAdapter);
        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(String.valueOf(s));

            }
        });

    }

    private void filter(String newText) {
        List<Subject> list=new ArrayList<>();
        for (Subject data:subjectList){
            if (data.getname().toLowerCase().contains(newText.toLowerCase())){
                list.add(data);
            }
        } dashboardAdapter.filteredList(list);
    }

    private void fetchSubjects() {
        db.collection("category").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot snapshot : task.getResult()) {
                    // Fetch the name of the subject
                    String subjectName = snapshot.getId();
                    // Create a Subject object and add it to the list
                    Subject subject = new Subject(subjectName);
                    subjectList.add(subject);
                }

                // Notify the adapter that the data has changed
                dashboardAdapter.notifyDataSetChanged();
                binding.lottieAnimationView.setVisibility(View.GONE);
                binding.recycler.setVisibility(View.VISIBLE);
            } else {
                // Handle error
                Toast.makeText(DashboardActivity.this, "Error fetching subjects", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
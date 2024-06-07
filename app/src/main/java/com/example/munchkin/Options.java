package com.example.munchkin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.munchkin.activity.MainGameActivity;

public class Options extends Fragment {
private Button goBack;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options, container, false);
        goBack = view.findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.main);
        });


        return view;
    }
}
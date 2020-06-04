package com.android.exercise.ui.activity.jetpack.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.exercise.R;

public class BlankFragment2 extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_blank2, container, false);
        inflate.findViewById(R.id.btn_jump).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Toast.makeText(getActivity(), getArguments().getString("data"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigateUp();
    }
}
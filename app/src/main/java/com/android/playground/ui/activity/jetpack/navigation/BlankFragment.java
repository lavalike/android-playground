package com.android.playground.ui.activity.jetpack.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.playground.R;

public class BlankFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        inflate.findViewById(R.id.btn_jump).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("data", "data from BlankFragment");
        Navigation.findNavController(v).navigate(R.id.blankFragment2, bundle);
    }
}
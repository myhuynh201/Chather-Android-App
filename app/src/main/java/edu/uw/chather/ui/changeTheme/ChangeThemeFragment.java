package edu.uw.chather.ui.changeTheme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChangeThemeBinding;
import edu.uw.chather.utils.SharedPreferencesManager;
import edu.uw.chather.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeThemeFragment extends Fragment {

    /**
     * Binding for changing the theme.
     */
    FragmentChangeThemeBinding binding;

    public ChangeThemeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeThemeBinding.inflate(inflater);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.blueThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(),
                new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.BLUE_THEME)));
        binding.greenThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(),
                new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.GREEN_THEME)));
        binding.pinkThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(),
                new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.PINK_THEME)));
        binding.purpleThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(),
                new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.PURPLE_THEME)));
        binding.darkThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(),
                new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.DARK_THEME)));
        }
}
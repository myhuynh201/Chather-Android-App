/*
  WeatherFragment

  TCSS 450 - Spring 2021
  Project: Chather
 */
package edu.uw.chather.ui.weather;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uw.chather.R;

/**
 * The weather fragment houses the overall weather display
 *
 * @author Alejandro Cossio Olono
 * @version 5/5/21
 */
public class WeatherFragment extends Fragment {

    /**
     * Creates the display of the fragment.
     * @param inflater The layout to be inflated.
     * @param container The encompassing viewgroup
     * @param savedInstanceState The previous state of the activity.
     * @return The view to diplay the WeatherFragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }
}
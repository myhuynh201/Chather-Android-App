package edu.uw.chather.ui.location;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentLocationBinding;

public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {


    private LocationViewModel mModel;

    private GoogleMap mMap;

    private LatLng mLatLng;

    public LocationFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        FragmentLocationBinding binding = FragmentLocationBinding.bind(getView());
        LocationViewModel model = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);
        model.addLocationObserver(getViewLifecycleOwner(), location -> {
            if(location != null) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMyLocationEnabled(true);
                final LatLng c = new LatLng(location.getLatitude(), location.getLongitude());
                mLatLng = c;
                //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 15.0f));
//                binding.textLatLong.setText("Latitude:" + Double.toString(c.latitude) +
//                        "\nLongitude:" + Double.toString(c.longitude));
            }
        });

        mMap.setOnMapClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentLocationBinding binding = FragmentLocationBinding.bind(getView());
        mModel = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);
        //mModel.addLocationObserver(getViewLifecycleOwner(), location ->
        //        binding.textLatLong.setText(location.toString()));
        binding.buttonSearch.setOnClickListener(this::searchLatLong);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //add this fragment as the OnMapReadyCallback -> See onMapReady()
        mapFragment.getMapAsync(this);

    }

    private void searchLatLong(View view) {
        LocationFragmentDirections.ActionLocationFragmentToWeatherFragment directions =
                LocationFragmentDirections.actionLocationFragmentToWeatherFragment();
        directions.setLat(Double.toString(mLatLng.latitude));
        directions.setLng(Double.toString(mLatLng.longitude));
        Navigation.findNavController(getView()).navigate(directions);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        FragmentLocationBinding binding = FragmentLocationBinding.bind(getView());
        Log.d("LAT/LONG", latLng.toString());
        mMap.clear();
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Marker"));

        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        latLng, mMap.getCameraPosition().zoom));
        //binding.textLatLong.setText("Latitude:" + Double.toString(latLng.latitude) +
        //        "\nLongitude:" + Double.toString(latLng.longitude));
        mLatLng = latLng;
    }
}

package edu.uw.chather.ui.location;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * View model for the location fragment.
 */
public class LocationViewModel extends ViewModel {

    /*
    Mutable live data for the current location.
     */
    private MutableLiveData<Location> mLocation;


    public LocationViewModel() {
        mLocation = new MediatorLiveData<>();
    }

    public void addLocationObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Location> observer) {
        mLocation.observe(owner, observer);
    }

    /**
     * Set the current location.
     * @param location Current location.
     */
    public void setLocation(final Location location) {
        if (!location.equals(mLocation.getValue())) {
            mLocation.setValue(location);
        }
    }

    /**
     * Get the current location.
     * @return Location coordinates.
     */
    public Location getCurrentLocation() {
        return new Location(mLocation.getValue());
    }


}

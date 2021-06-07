package edu.uw.chather.ui.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * View model to store information about newly received chat messages
 */
public class NewMessageCountViewModel extends ViewModel {

    /**
     *  Mutable count of new messages
     */
    private final MutableLiveData<Integer> mNewMessageCount;

    /**
     * Constructor that initializes the viewmodel
     */
    public NewMessageCountViewModel() {
        mNewMessageCount = new MutableLiveData<>();
        mNewMessageCount.setValue(0);
    }

    /**
     * Register as an observer to listen to new received messages.
     * @param owner the fragments lifecycle owner
     * @param observer the observer
     */
    public void addMessageCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewMessageCount.observe(owner, observer);
    }

    /**
     * Increases new message count
     */
    public void increment() {
        mNewMessageCount.setValue(mNewMessageCount.getValue() + 1);
    }

    /**
     * Set message count to zero
     */
    public void reset() {
        mNewMessageCount.setValue(0);
    }
}

package com.ask.vitevents.RoomDb;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ask.vitevents.Classes.Tshirt;

import java.util.List;

public class TshirtViewModel extends AndroidViewModel {

    private TshirtRepositry mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Tshirt>> mAllTshirt;

    public TshirtViewModel(Application application)
    {
        super(application);
        mRepository = new TshirtRepositry(application);
        mAllTshirt = mRepository.getAllTshirt();
    }

    public LiveData<List<Tshirt>> getAllTshirt() { return mAllTshirt; }

    public void insert(Tshirt tshirt) { mRepository.insertTshirt(tshirt); }

    public LiveData<Tshirt> getTshirtById(String id)
    {
        return mRepository.getTshirtById(id);
    }

    public String getTshirtFront(String id)
    {
        return mRepository.getTshirtFront(id);
    }

    public String getTshirtBack(String id)
    {
        return mRepository.getTshirtBack(id);
    }
    public String getTshirtSide(String id)
    {
        return mRepository.getTshirtSide(id);
    }
}

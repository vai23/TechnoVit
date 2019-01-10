package com.ask.vitevents.RoomDb;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ask.vitevents.Classes.Event;

import java.util.List;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class EventViewModel extends AndroidViewModel {

    private EventRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Event>> mAllWords;

    public EventViewModel(Application application) {
        super(application);
        mRepository = new EventRepository(application);
        mAllWords = mRepository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() { return mAllWords; }

    public void insert(Event event) { mRepository.insert(event); }

    public LiveData<Event> getEventById(String id)
    {
        return mRepository.getEventById(id);
    }

    public LiveData<List<Event>> getEventBySchool(String id)
    {
        return mRepository.getEventBySchool(id);
    }

    public LiveData<List<Event>> getEventByClub(String id)
    {
        return mRepository.getEventByClub(id);
    }

    public LiveData<List<Event>> getTrendingEvents()
    {
        return mRepository.getTrendingEvents();
    }

}
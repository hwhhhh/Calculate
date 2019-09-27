package com.hwhhhh.calculator.ui.Length;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class LengthViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LengthViewModel() {
        mText = new MutableLiveData<>();
    }
}
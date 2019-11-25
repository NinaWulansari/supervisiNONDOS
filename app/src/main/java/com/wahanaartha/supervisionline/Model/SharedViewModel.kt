package com.wahanaartha.supervisionline.Model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class SharedViewModel:ViewModel(){
    val inputNumber = MutableLiveData<Int>()
}
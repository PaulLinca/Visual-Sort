package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry

class SelectionSort(override val list: MutableLiveData<List<BarEntry>>) : ISortingAlgorithm
{
    override suspend fun sort(list: MutableLiveData<List<BarEntry>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")
    }
}
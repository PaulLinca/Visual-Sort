package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry

class SelectionSort : ISortingAlgorithm
{
    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")
    }
}
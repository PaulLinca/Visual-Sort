package ro.lincap.visualsort.data.model

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry

interface ISortingAlgorithm
{
    suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>)
}
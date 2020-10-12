package ro.lincap.visualsort.data.model

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry

interface ISortingAlgorithm
{
    val list: MutableLiveData<List<BarEntry>>

    suspend fun sort(list: MutableLiveData<List<BarEntry>>)
}
package ro.lincap.visualsort.data.model

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry

interface ISortingAlgorithm
{
    val properName : String
    val description: String

    val complexityAverage: String
    val complexityBest: String
    val complexityWorst: String
    val complexitySpace: String

    suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
}
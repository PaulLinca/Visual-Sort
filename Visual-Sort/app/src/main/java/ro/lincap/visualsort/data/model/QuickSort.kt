package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class QuickSort: ISortingAlgorithm
{
    override val properName : String = "Quicksort"
    override val description : String = ""

    override val complexityAverage: String = "n log(n)"
    override val complexityBest: String = "n log(n)"
    override val complexityWorst: String = "n^2"
    override val complexitySpace: String = "n"

    private val sortedEntries = arrayListOf<Pair<Float, Int>>()

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        quicksort(listToSort, 0, listToSort.value!!.lastIndex, speed, highlightedValues)
    }

    private suspend fun quicksort(listToSort: MutableLiveData<List<BarEntry>>, low: Int, high: Int, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        if(low < high)
        {
            val partitioningIndex = partition(listToSort, low, high, speed, highlightedValues)

            quicksort(listToSort, low, partitioningIndex - 1, speed, highlightedValues)
            quicksort(listToSort, partitioningIndex + 1, high, speed, highlightedValues)
        }
    }

    private suspend fun partition(listToSort: MutableLiveData<List<BarEntry>>, low: Int, high: Int, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>): Int
    {
        val listCopy = listToSort.value!!

        val pivot = listCopy[high]
        var smallerIndex = low - 1
        for(index in low until high)
        {
            if(listCopy[index].y < pivot.y)
            {
                smallerIndex++

                val temp = listCopy[smallerIndex].y
                listCopy[smallerIndex].y = listCopy[index].y
                listCopy[index].y = temp

                listToSort.postValue(listCopy)
                delay(500 - speed.value!!.toLong() + 1)
            }
        }

        val temp = listCopy[smallerIndex + 1].y
        listCopy[smallerIndex + 1].y = listCopy[high].y
        listCopy[high].y = temp

        listToSort.postValue(listCopy)
        delay(500 - speed.value!!.toLong() + 1)

        return smallerIndex + 1
    }
}
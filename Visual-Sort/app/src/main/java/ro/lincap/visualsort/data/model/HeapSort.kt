package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class HeapSort: ISortingAlgorithm
{
    override val properName : String = Constants.HEAPSORT
    override val description : String = ""

    override val complexityAverage: String = "n log(n)"
    override val complexityBest: String = "n log(n)"
    override val complexityWorst: String = "n log(n)"
    override val complexitySpace: String = "1"

    private val sortedEntries = arrayListOf<Pair<Float, Int>>()

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        sortedEntries.clear()

        var listCopy = listToSort.value!!
        for(index in listCopy.size / 2 - 1 downTo 0)
        {
            heapify(listToSort, listCopy.size, index, speed, highlightedValues)
        }

        listCopy = listToSort.value!!
        for(index in listCopy.size - 1 downTo 0)
        {
            val temp = listCopy[0].y
            listCopy[0].y = listCopy[index].y
            listCopy[index].y = temp

            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)

            heapify(listToSort, index,0, speed, highlightedValues)
        }
    }

    private suspend fun heapify(listToSort: MutableLiveData<List<BarEntry>>, size: Int, index: Int, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        val listCopy = listToSort.value!!
        var largest = index
        val left = 2 * index + 1
        val right = 2 * index + 2

        if(left < size && listCopy[left].y > listCopy[largest].y)
        {
            largest = left
        }

        if(right < size && listCopy[right].y > listCopy[largest].y)
        {
            largest = right
        }

        if(largest != index)
        {
            val temp = listCopy[index].y
            listCopy[index].y = listCopy[largest].y
            listCopy[largest].y = temp

            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)

            heapify(listToSort, size, largest, speed, highlightedValues)
        }
        listToSort.postValue(listCopy)
        delay(500 - speed.value!!.toLong() + 1)
    }
}
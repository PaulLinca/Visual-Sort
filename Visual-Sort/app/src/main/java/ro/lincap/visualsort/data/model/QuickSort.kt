package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class QuickSort: ISortingAlgorithm
{
    override val properName : String = "Quicksort"
    override val description : String = "Quicksort is a divide-and-conquer algorithm that selects a \"pivot\" element and partitions the other elements into two sublists, one having elements lesser than the pivot, the other having elements greater than the pivot. Upon partitioning, the pivot will be placed between them, giving it its final ordered position. The two sublists are then sorted recursively using the same technique."

    override val complexityAverage: String = "n log(n)"
    override val complexityBest: String = "n log(n)"
    override val complexityWorst: String = "n^2"
    override val complexitySpace: String = "n"

    private val sortedEntries = arrayListOf<Pair<Float, Int>>()

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        sortedEntries.clear()
        quicksort(listToSort, 0, listToSort.value!!.lastIndex, speed, highlightedValues)
    }

    private suspend fun quicksort(listToSort: MutableLiveData<List<BarEntry>>, low: Int, high: Int, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        if(low < high)
        {
            val partitioningIndex = partition(listToSort, low, high, speed, highlightedValues)
            sortedEntries.add(Pair(partitioningIndex.toFloat(), Constants.PURPLE))

            quicksort(listToSort, low, partitioningIndex - 1, speed, highlightedValues)
            quicksort(listToSort, partitioningIndex + 1, high, speed, highlightedValues)
        }
        else
        {
            sortedEntries.add(Pair(low.toFloat(), Constants.PURPLE))
        }
    }

    private suspend fun partition(listToSort: MutableLiveData<List<BarEntry>>, low: Int, high: Int, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>): Int
    {
        val listCopy = listToSort.value!!

        val pivot = listCopy[high]
        var smallerIndex = low - 1
        for(index in low until high)
        {
            highlightedValues.postValue(arrayListOf(Pair((pivot.x), Constants.GREEN), Pair(index.toFloat(), Constants.ORANGE), Pair(smallerIndex + 1f, Constants.YELLOW)) + sortedEntries)
            delay(500 - speed.value!!.toLong() + 1)

            if(listCopy[index].y < pivot.y)
            {
                highlightedValues.postValue(arrayListOf(Pair((pivot.x), Constants.RED), Pair(smallerIndex + 1f, Constants.YELLOW), Pair(index.toFloat(), Constants.RED)) + sortedEntries)
                delay(500 - speed.value!!.toLong() + 1)

                smallerIndex++


                val temp = listCopy[smallerIndex].y
                listCopy[smallerIndex].y = listCopy[index].y
                listCopy[index].y = temp

                highlightedValues.postValue(arrayListOf(Pair((pivot.x), Constants.GREEN), Pair(index.toFloat(), Constants.ORANGE), Pair(smallerIndex + 1f, Constants.YELLOW)) + sortedEntries)
                delay(500 - speed.value!!.toLong() + 1)
            }

            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)
        }

        val temp = listCopy[smallerIndex + 1].y
        listCopy[smallerIndex + 1].y = listCopy[high].y
        listCopy[high].y = temp

        listToSort.postValue(listCopy)
        delay(500 - speed.value!!.toLong() + 1)

        highlightedValues.postValue(sortedEntries)

        return smallerIndex + 1
    }
}
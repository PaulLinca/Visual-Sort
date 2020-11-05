package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class HeapSort: ISortingAlgorithm
{
    override val properName : String = Constants.HEAPSORT
    override val description : String = "Heap sort works by dividing the list into an unsorted part and a sorted part. The unsorted part will always be modified to make a max heap. Once the max heap is built, its root (the largest element) will be moved to the beginning of the sorted part and any heap conflicts will be resolved. This is repeated until there are no more elements in the usorted part."

    override val complexityAverage: String = "n log(n)"
    override val complexityBest: String = "n log(n)"
    override val complexityWorst: String = "n log(n)"
    override val complexitySpace: String = "1"

    private val sortedEntries = arrayListOf<Pair<Float, Int>>()

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        sortedEntries.clear()

        // build a heap out of the original list
        var listCopy = listToSort.value!!
        for(index in listCopy.size / 2 - 1 downTo 0)
        {
            buildMaxHeap(listToSort, listCopy.size, index, speed, highlightedValues)
        }

        // extraction phase (perform placements)
        listCopy = listToSort.value!!
        for(index in listCopy.size - 1 downTo 0)
        {
            // move root of the heap to the end of the list
            val temp = listCopy[0].y
            listCopy[0].y = listCopy[index].y
            listCopy[index].y = temp

            // root is in the correct position -> add to sorted entries
            sortedEntries.add(Pair(index.toFloat(), Constants.PURPLE))
            highlightedValues.postValue(sortedEntries)
            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)

            // rebuild the heap without the root
            buildMaxHeap(listToSort, index,0, speed, highlightedValues)
        }
    }

    private suspend fun buildMaxHeap(listToSort: MutableLiveData<List<BarEntry>>, size: Int, currentRoot: Int, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        val listCopy = listToSort.value!!
        var largestElementIndex = currentRoot
        val leftChildIndex = 2 * currentRoot + 1
        val rightChildIndex = 2 * currentRoot + 2

        highlightedValues.postValue(arrayListOf(Pair(largestElementIndex.toFloat(), Constants.GREEN), Pair(leftChildIndex.toFloat(), Constants.YELLOW), Pair(rightChildIndex.toFloat(), Constants.YELLOW)) + sortedEntries)
        delay(500 - speed.value!!.toLong() + 1)

        if(leftChildIndex < size && listCopy[leftChildIndex].y > listCopy[largestElementIndex].y)
        {
            largestElementIndex = leftChildIndex
        }

        if(rightChildIndex < size && listCopy[rightChildIndex].y > listCopy[largestElementIndex].y)
        {
            largestElementIndex = rightChildIndex
        }

        if(largestElementIndex != currentRoot)
        {
            val temp = listCopy[currentRoot].y
            listCopy[currentRoot].y = listCopy[largestElementIndex].y
            listCopy[largestElementIndex].y = temp

            highlightedValues.postValue(arrayListOf(Pair(currentRoot.toFloat(), Constants.GREEN), Pair(largestElementIndex.toFloat(), Constants.RED)) + sortedEntries)
            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)

            buildMaxHeap(listToSort, size, largestElementIndex, speed, highlightedValues)
        }
        else
        {
            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)
        }
    }
}
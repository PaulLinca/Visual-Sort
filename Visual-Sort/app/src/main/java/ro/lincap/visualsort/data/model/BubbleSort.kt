package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class BubbleSort : ISortingAlgorithm
{
    override val properName : String = "Bubble Sort"
    override val description : String = "Description"

    override val complexityAverage: String = "O(n^2)"
    override val complexityBest: String = "O(n)"
    override val complexityWorst: String = "O(n^2)"
    override val complexitySpace: String = "O(1)"

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        var keepGoing = false
        val sortedEntries = arrayListOf<Pair<Float, Int>>()
        // Make a copy of the list
        val listCopy = listToSort.value!!
        for(currentPass in 0 until (listCopy.size - 1))
        {
            keepGoing = false

            for(currentPosition in 0 until (listCopy.size - currentPass - 1))
            {
                highlightedValues.postValue(arrayListOf(Pair(currentPosition.toFloat(), Constants.YELLOW), Pair((currentPosition + 1).toFloat(), Constants.YELLOW)) + sortedEntries)
                delay(500 - speed.value!!.toLong() + 1)

                if(listCopy[currentPosition].y > listCopy[currentPosition + 1].y)
                {
                    highlightedValues.postValue(arrayListOf(Pair(currentPosition.toFloat(), Constants.RED), Pair((currentPosition + 1).toFloat(), Constants.RED)) + sortedEntries)

                    val temp = listCopy[currentPosition].y
                    listCopy[currentPosition].y = listCopy[currentPosition+1].y
                    listCopy[currentPosition+1].y = temp

                    keepGoing = true
                }
                else
                {
                    highlightedValues.postValue(arrayListOf(Pair(currentPosition.toFloat(), Constants.GREEN), Pair((currentPosition + 1).toFloat(), Constants.GREEN)) + sortedEntries)
                }

                listToSort.postValue(listCopy)
                delay(500 - speed.value!!.toLong() + 1)

            }

            if(!keepGoing)
            {
                val allEntriesSorted = arrayListOf<Pair<Float, Int>>()
                for(index in 0 until (listCopy.size))
                {
                    allEntriesSorted.add(Pair(index.toFloat(), Constants.PURPLE))
                }
                highlightedValues.postValue(allEntriesSorted)
                delay(500 - speed.value!!.toLong() + 1)

                break
            }

                sortedEntries.add(Pair(listCopy.size.toFloat() - 1 - currentPass, Constants.PURPLE))
        }
        if(keepGoing)
        {
            highlightedValues.postValue(sortedEntries + Pair(0f, Constants.PURPLE))
        }
    }
}
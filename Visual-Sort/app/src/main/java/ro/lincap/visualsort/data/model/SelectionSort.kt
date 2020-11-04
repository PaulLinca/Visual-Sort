package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class SelectionSort : ISortingAlgorithm
{
    override val properName : String = Constants.SELECTIONSORT
    override val description : String = "Selection sort divides the input list into two sublists. The algorithm repeatedly iterates through the second, unsorted sublist, finds the minimum element and swaps it with the leftmost unsorted element, moving the sublist boundaries one element to the right."

    override val complexityAverage: String = "n^2"
    override val complexityBest: String = "n^2"
    override val complexityWorst: String = "n^2"
    override val complexitySpace: String = "1"

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        val sortedEntries = arrayListOf<Pair<Float, Int>>()
        // Make a copy of the list
        val listCopy = listToSort.value!!
        for(currentElement in 0 until (listCopy.size - 1))
        {
            var minimumIndex = currentElement
            for(elementToCompare in currentElement + 1 until (listCopy.size))
            {
                highlightedValues.postValue(arrayListOf(Pair(elementToCompare.toFloat(), Constants.YELLOW), Pair((minimumIndex).toFloat(), Constants.GREEN)) + sortedEntries)
                delay(500 - speed.value!!.toLong() + 1)

                if(listCopy[minimumIndex].y > listCopy[elementToCompare].y)
                {
                    highlightedValues.postValue(arrayListOf(Pair((elementToCompare).toFloat(), Constants.GREEN), Pair((minimumIndex).toFloat(), Constants.RED)) + sortedEntries)
                    delay(500 - speed.value!!.toLong() + 1)

                    minimumIndex = elementToCompare
                }
                else
                {
                    highlightedValues.postValue(arrayListOf(Pair((elementToCompare).toFloat(), Constants.RED), Pair((minimumIndex).toFloat(), Constants.GREEN)) + sortedEntries)
                    delay(500 - speed.value!!.toLong() + 1)
                }
            }

            if(minimumIndex != currentElement)
            {
                val temp = listCopy[currentElement].y
                listCopy[currentElement].y = listCopy[minimumIndex].y
                listCopy[minimumIndex].y = temp
            }

            listToSort.postValue(listCopy)
            sortedEntries.add(Pair(currentElement.toFloat(), Constants.PURPLE))
            delay(500 - speed.value!!.toLong() + 1)
        }
        highlightedValues.postValue(sortedEntries + Pair(listCopy.lastIndex.toFloat(), Constants.PURPLE))
    }
}
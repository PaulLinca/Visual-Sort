package ro.lincap.visualsort.data.model

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay

class SelectionSort : ISortingAlgorithm
{
    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        // Make a copy of the list
        val listCopy = listToSort.value!!
        for(currentElement in 0 until (listCopy.size - 1))
        {
            var minimumIndex = currentElement
            for(elementToCompare in currentElement + 1 until (listCopy.size))
            {
                highlightedValues.postValue(arrayListOf(Pair(elementToCompare.toFloat(), Color.YELLOW), Pair((minimumIndex).toFloat(), Color.GREEN)))
                delay(speed.value!!.toLong())

                if(listCopy[minimumIndex].y > listCopy[elementToCompare].y)
                {

                    highlightedValues.postValue(arrayListOf(Pair((elementToCompare).toFloat(), Color.RED), Pair((minimumIndex).toFloat(), Color.RED)))
                    delay(speed.value!!.toLong())

                    minimumIndex = elementToCompare
                }
                else
                {
                    highlightedValues.postValue(arrayListOf(Pair((elementToCompare).toFloat(), Color.GREEN), Pair((minimumIndex).toFloat(), Color.GREEN)))
                    delay(speed.value!!.toLong())
                }
            }

            if(minimumIndex != currentElement)
            {
                val temp = listCopy[currentElement].y
                listCopy[currentElement].y = listCopy[minimumIndex].y
                listCopy[minimumIndex].y = temp
            }

            listToSort.postValue(listCopy)
            delay(speed.value!!.toLong())
        }
        highlightedValues.postValue(arrayListOf())
    }
}
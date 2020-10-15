package ro.lincap.visualsort.data.model

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay

class BubbleSort : ISortingAlgorithm
{
    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        // Make a copy of the list
        val listCopy = listToSort.value!!
        for(currentPass in 0 until (listCopy.size - 1))
        {
            for(currentPosition in 0 until (listCopy.size - currentPass - 1))
            {
                highlightedValues.postValue(arrayListOf(Pair(currentPosition.toFloat(), Color.YELLOW), Pair((currentPosition + 1).toFloat(), Color.YELLOW)))
                delay(speed.value!!.toLong())

                if(listCopy[currentPosition].y > listCopy[currentPosition + 1].y)
                {
                    highlightedValues.postValue(arrayListOf(Pair(currentPosition.toFloat(), Color.RED), Pair((currentPosition + 1).toFloat(), Color.RED)))

                    val temp = listCopy[currentPosition].y
                    listCopy[currentPosition].y = listCopy[currentPosition+1].y
                    listCopy[currentPosition+1].y = temp
                }
                else
                {
                    highlightedValues.postValue(arrayListOf(Pair(currentPosition.toFloat(), Color.GREEN), Pair((currentPosition + 1).toFloat(), Color.GREEN)))
                }

                highlightedValues.postValue(arrayListOf(Pair(currentPosition.toFloat(), Color.GREEN), Pair((currentPosition + 1).toFloat(), Color.GREEN)))

                listToSort.postValue(listCopy)
                delay(speed.value!!.toLong())
            }
        }
        highlightedValues.postValue(arrayListOf())
    }
}
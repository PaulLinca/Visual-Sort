package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay

class BubbleSort : ISortingAlgorithm
{
    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        val listCopy = listToSort.value!!
        for(currentPass in 0 until (listCopy.size - 1))
        {
            for(currentPosition in 0 until (listCopy.size - currentPass - 1))
            {
                if(listCopy[currentPosition].y > listCopy[currentPosition + 1].y)
                {
                    val temp = listCopy[currentPosition].y
                    listCopy[currentPosition].y = listCopy[currentPosition+1].y
                    listCopy[currentPosition+1].y = temp
                }
                listToSort.postValue(listCopy)
                delay(10)
            }
        }
    }
}
package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class BubbleSort(override val list: MutableLiveData<List<BarEntry>>) : ISortingAlgorithm
{
    override suspend fun sort(list: MutableLiveData<List<BarEntry>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        val listToSort = list.value!!
        for(currentPass in 0 until (listToSort.size - 1))
        {
            for(currentPosition in 0 until (listToSort.size - currentPass - 1))
            {
                if(listToSort[currentPosition].y > listToSort[currentPosition + 1].y)
                {
                    val temp = listToSort[currentPosition].y
                    listToSort[currentPosition].y = listToSort[currentPosition+1].y
                    listToSort[currentPosition+1].y = temp
                }

                list.postValue(listToSort)
                delay(5)
            }
        }
    }
}
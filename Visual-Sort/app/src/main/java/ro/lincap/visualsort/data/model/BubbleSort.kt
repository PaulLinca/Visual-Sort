package ro.lincap.visualsort.data.model

import android.util.Log
import com.github.mikephil.charting.data.BarEntry

class BubbleSort : ISortingAlgorithm
{
    override fun sort(listToSort: List<BarEntry>): List<BarEntry>
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

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
            }
        }

        return listToSort
    }
}
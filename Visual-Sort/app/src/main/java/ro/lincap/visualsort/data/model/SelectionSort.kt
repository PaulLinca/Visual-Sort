package ro.lincap.visualsort.data.model

import android.util.Log
import com.github.mikephil.charting.data.BarEntry

class SelectionSort : ISortingAlgorithm
{
    override fun sort(listToSort: List<BarEntry>): List<BarEntry>
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        return  listToSort
    }
}
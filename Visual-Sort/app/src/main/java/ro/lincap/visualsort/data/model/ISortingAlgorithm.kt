package ro.lincap.visualsort.data.model

import com.github.mikephil.charting.data.BarEntry

interface ISortingAlgorithm
{
    fun sort(listToSort: List<BarEntry>): List<BarEntry>
}
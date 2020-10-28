package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay

class MergeSort : ISortingAlgorithm
{
    override val properName : String = "Merge Sort"
    override val description : String = "Merge sort is a Divide and Conquer sorting algorithm. It works by recursively dividing the list of elements into two equal sublists and them combining them in a sorted manner."

    override val complexityAverage: String = "n log(n)"
    override val complexityBest: String = "n log(n)"
    override val complexityWorst: String = "n log(n)"
    override val complexitySpace: String = "n"

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        mergeSort(listToSort, 0, listToSort.value!!.lastIndex, speed)
    }

    private suspend fun mergeSort(listToSort: MutableLiveData<List<BarEntry>>, leftBound: Int, rightBound: Int, speed: MutableLiveData<Float>)
    {
        if(leftBound < rightBound)
        {
            val middle = (leftBound + rightBound) / 2

            mergeSort(listToSort, leftBound, middle, speed)
            mergeSort(listToSort, middle + 1, rightBound, speed)

            merge(listToSort, leftBound, middle, rightBound, speed)
        }
    }

    private suspend fun merge(listToSort: MutableLiveData<List<BarEntry>>, leftBound: Int, middleBound: Int, rightBound: Int, speed: MutableLiveData<Float>)
    {
        val listCopy = listToSort.value!!

        val leftSublistSize = middleBound - leftBound + 1
        val rightSublistSize = rightBound - middleBound

        val tempLeftSublist = arrayListOf<Float>()
        val tempRightSublist = arrayListOf<Float>()

        for(i in 0 until leftSublistSize)
        {
            tempLeftSublist.add(listCopy[leftBound + i].y)
        }
        for(j in 0 until rightSublistSize)
        {
            tempRightSublist.add(listCopy[middleBound + 1 + j].y)
        }

        var i = 0
        var j = 0
        var k = leftBound
        while(i < leftSublistSize && j < rightSublistSize)
        {
            if(tempLeftSublist[i] <= tempRightSublist[j])
            {
                listCopy[k].y = tempLeftSublist[i]
                i++
            }
            else
            {
                listCopy[k].y = tempRightSublist[j]
                j++
            }
            k++

            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)
        }

        while(i < leftSublistSize)
        {
            listCopy[k].y = tempLeftSublist[i]
            i++
            k++

            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)
        }

        while(j < rightSublistSize)
        {
            listCopy[k].y = tempRightSublist[j]
            j++
            k++

            listToSort.postValue(listCopy)
            delay(500 - speed.value!!.toLong() + 1)
        }

        listToSort.postValue(listCopy)
        delay(500 - speed.value!!.toLong() + 1)
    }
}
package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class InsertionSort : ISortingAlgorithm
{
    override val properName : String = Constants.INSERTIONSORT
    override val description : String = "The list is split into a sorted and an unsorted part. Values from the unsorted part are picked and moved to the correct position in the sorted part."

    override val complexityAverage: String = "n^2"
    override val complexityBest: String = "n"
    override val complexityWorst: String = "n^2"
    override val complexitySpace: String = "1"

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        val listCopy = listToSort.value!!
        for(currentIndex in 1 until listCopy.size)
        {
            highlightedValues.postValue(arrayListOf(Pair((currentIndex).toFloat(), Constants.YELLOW)))
            delay(500 - speed.value!!.toLong() + 1)

            val key = listCopy[currentIndex].y
            var j = currentIndex - 1
            while(j >= 0 && listCopy[j].y > key)
            {
                val temp = listCopy[j].y
                listCopy[j].y = listCopy[j + 1].y
                listCopy[j + 1].y = temp

                highlightedValues.postValue(arrayListOf(Pair((currentIndex).toFloat(), Constants.YELLOW), Pair(j.toFloat(), Constants.GREEN)))
                listToSort.postValue(listCopy)
                delay(500 - speed.value!!.toLong() + 1)

                j--
            }
        }

        highlightedValues.postValue((0..listCopy.lastIndex).toMutableList().map { int -> Pair(int.toFloat(), Constants.PURPLE) })
    }
}
package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class CombSort : ISortingAlgorithm
{
    override val properName : String = Constants.COMBSORT
    override val description : String = "Comb Sort is an improvement over Bubble Sort. While bubble sort compares adjacent values, comb sort uses a gap between values compared. The gaps starts with a large value and gradually shrinks by a factor of 1.3 at every iteration until it reaches 1.\n\nThe algorithm is similar to shell sort as well, but shell sort completely sorts the list at each pass before reducing the gap, while comb sort does not."

    override val complexityAverage: String = "n^2"
    override val complexityBest: String = "n log(n)"
    override val complexityWorst: String = "n^2"
    override val complexitySpace: String = "1"

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        val listCopy = listToSort.value!!
        var swapped = false
        var gap = listCopy.size
        while(gap != 1 || swapped)
        {
            gap = getNextGap(gap)
            swapped = false

            for(index in 0 until listCopy.size - gap)
            {
                highlightedValues.postValue(arrayListOf(Pair(gap.toFloat(), Constants.ORANGE), Pair(index.toFloat(), Constants.YELLOW), Pair((index + gap).toFloat(), Constants.YELLOW)))
                delay(500 - speed.value!!.toLong() + 1)

                if(listCopy[index].y > listCopy[index + gap].y)
                {
                    highlightedValues.postValue(arrayListOf(Pair(gap.toFloat(), Constants.ORANGE), Pair(index.toFloat(), Constants.RED), Pair((index + gap).toFloat(), Constants.RED)))

                    val temp = listCopy[index].y
                    listCopy[index].y = listCopy[index + gap].y
                    listCopy[index + gap].y = temp

                    swapped = true
                }
                else
                {
                    highlightedValues.postValue(arrayListOf(Pair(gap.toFloat(), Constants.ORANGE), Pair(index.toFloat(), Constants.GREEN), Pair((index + gap).toFloat(), Constants.GREEN)))
                }

                listToSort.postValue(listCopy)
                delay(500 - speed.value!!.toLong() + 1)
            }
        }

        highlightedValues.postValue((0..listCopy.lastIndex).toMutableList().map { int -> Pair(int.toFloat(), Constants.PURPLE) })
    }

    private fun getNextGap(gap: Int): Int
    {
        val newGap = (gap * 10 / 13)
        if(newGap < 1)
        {
            return 1
        }

        return newGap
    }
}
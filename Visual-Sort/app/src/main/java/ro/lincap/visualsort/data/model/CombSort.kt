package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants
import kotlin.math.roundToInt

class CombSort : ISortingAlgorithm
{
    override val properName : String = Constants.COMBSORT
    override val description : String = ""

    override val complexityAverage: String = "n^2"
    override val complexityBest: String = "n"
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
                if(listCopy[index].y > listCopy[index + gap].y)
                {
                    val temp = listCopy[index].y
                    listCopy[index].y = listCopy[index + gap].y
                    listCopy[index + gap].y = temp

                    swapped = true
                }

                listToSort.postValue(listCopy)
                delay(500 - speed.value!!.toLong() + 1)
            }
        }
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
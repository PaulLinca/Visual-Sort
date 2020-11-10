package ro.lincap.visualsort.data.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import ro.lincap.visualsort.util.Constants

class ShellSort : ISortingAlgorithm
{
    override val properName : String = Constants.SHELLSORT
    override val description : String = "The algorithm works by comparing pairs of elements that are far apart from eachother, swapping them if they're in the wrong order, and then progressively reducing the gap between elements to be compared. \n\nThe gap sequence in the visualization example consists of constantly halving the size of the input. Many gap sequences have been proposed over the years, each one affecting the final time complexity of the algorithm."

    override val complexityAverage: String = "n log(n)^2"
    override val complexityBest: String = "n log(n)"
    override val complexityWorst: String = "n^2"
    override val complexitySpace: String = "1"

    override suspend fun sort(listToSort: MutableLiveData<List<BarEntry>>, speed: MutableLiveData<Float>, highlightedValues: MutableLiveData<List<Pair<Float, Int>>>)
    {
        Log.d(this::class.java.canonicalName, "Applying sort")

        highlightedValues.postValue(arrayListOf())

        val listCopy = listToSort.value!!
        var gap = listCopy.size / 2
        while (gap > 0)
        {
            for(index in gap until listCopy.size)
            {
                val temp = listCopy[index].y
                var j = index
                highlightedValues.postValue(arrayListOf(Pair(gap.toFloat(), Constants.ORANGE), Pair(j.toFloat(), Constants.YELLOW), Pair(j.toFloat() - gap, Constants.YELLOW)))

                while(j >= gap && listCopy[j - gap].y > temp)
                {
                    listCopy[j].y = listCopy[j - gap].y

                    highlightedValues.postValue(arrayListOf(Pair(gap.toFloat(), Constants.ORANGE), Pair(j.toFloat(), Constants.YELLOW), Pair(j.toFloat() - gap, Constants.YELLOW)))
                    listToSort.postValue(listCopy)
                    delay(500 - speed.value!!.toLong() + 1)

                    j -= gap
                }
                listCopy[j].y = temp

                listToSort.postValue(listCopy)
                delay(500 - speed.value!!.toLong() + 1)
            }
            gap /= 2
        }

        highlightedValues.postValue((0..listCopy.lastIndex).toMutableList().map { int -> Pair(int.toFloat(), Constants.PURPLE) })
    }
}
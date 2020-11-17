package ro.lincap.visualsort.util

import android.text.Html
import android.text.Spanned
import androidx.core.text.toSpanned
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.fragment_home.*

fun String.toComplexityFormat(): Spanned?
{
    val splitString = this.split("^")
    if(splitString.size == 1)
    {
        return "O(${splitString[0]})".toSpanned()
    }

    return Html.fromHtml("O(${splitString[0]}<sup>${splitString[1]}</sup>)")
}

/**
 * Defines how the chart should look and behave
 */
fun BarChart.configureChartAppearance()
{
    // disable zoom
    this.setScaleEnabled(false)

    // Disable touch
    this.setTouchEnabled(false)

    // disable labels
    this.axisLeft.setDrawLabels(false)
    this.axisRight.setDrawLabels(false)
    this.legend.isEnabled = false
    this.description.isEnabled = false

    // disable grid lines
    this.axisLeft.setDrawGridLines(false)
    this.axisRight.setDrawGridLines(false)
    this.xAxis.isEnabled = false

    // remove side padding
    this.setViewPortOffsets(0f, 0f, 0f, 0f)
    // remove bottom padding
    this.axisLeft.axisMinimum = 0f
}
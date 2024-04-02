package com.pbd.psi.ui.graph

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.pbd.psi.databinding.FragmentGraphBinding
import com.pbd.psi.repository.GraphRepository
import com.pbd.psi.room.AppDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraphFragment : Fragment() {
    private lateinit var binding: FragmentGraphBinding
    private val entries: ArrayList<PieEntry> = ArrayList()
    private val viewModel: GraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraphBinding.inflate(layoutInflater)

        val pieChart = binding.pieChart
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.setDragDecelerationFrictionCoef(0.95f)
        pieChart.setTransparentCircleColor(Color.BLACK)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        viewModel.fetchIncomeTotal()
        viewModel.fetchExpenseTotal()

        viewModel.incomeTotal.observe(viewLifecycleOwner) { incomeTotal ->
            Log.d("GraphFragment", "Income total: $incomeTotal")
            binding.textPemasukanNominal?.text = incomeTotal.toString()
            addEntries(incomeTotal.toFloat(),"Pemasukan")
            updatePieChart(-65536)
        }

        viewModel.expenseTotal.observe(viewLifecycleOwner) { expenseTotal ->
            Log.d("GraphFragment", "Expense total: $expenseTotal")
            binding.textPengeluaranNominal?.text = expenseTotal.toString()
            addEntries(expenseTotal.toFloat(),"Pengeluaran")
            updatePieChart(-16711936)
        }

        return binding.root
    }

    private fun addEntries(value: Float,label: String) {
        entries.add(PieEntry(value,label))
    }

    private fun updatePieChart(color:Int) {
        val pieChart = binding.pieChart
        val dataSet = PieDataSet(entries, "Mobile OS")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        Log.d("Entries", entries.toString())
        val colors = ArrayList<Int>()
        if(entries.count() == 2){
            if(entries.get(0).label == "Pemasukan"){
                colors.add(-16711936)
                colors.add(-65536)

            }
            else{
                colors.add(-65536)
                colors.add(-16711936)

            }
        }
        else{
            colors.add(color)
        }
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.setHoleColor(Color.parseColor("#121433"))
        pieChart.invalidate()
    }
}

package ro.lincap.visualsort.presentation.comparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comparison_fragment.*
import kotlinx.android.synthetic.main.fragment_home.*
import ro.lincap.visualsort.databinding.ComparisonFragmentBinding
import ro.lincap.visualsort.R as R1

class ComparisonFragment : Fragment()
{
    private val viewModel: ComparisonViewModel by viewModels()
    private lateinit var binding: ComparisonFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = ComparisonFragmentBinding.inflate(inflater, container, false).apply {
            comparisonViewModel = viewModel
            lifecycleOwner = this@ComparisonFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        setupUI()
    }

    private fun setupUI()
    {
        backButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }

        viewModel.algorithms.observe(viewLifecycleOwner, Observer { currentAlgorithms ->
            algorithmsList.also {
                it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                it.adapter =
                    AlgorithmListAdapter(currentAlgorithms)
            }
        })
    }

}
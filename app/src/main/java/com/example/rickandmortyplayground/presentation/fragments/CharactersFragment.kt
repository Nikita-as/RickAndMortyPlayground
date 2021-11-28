package com.example.rickandmortyplayground.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyplayground.R
import com.example.rickandmortyplayground.databinding.CharactersFragmentBinding
import com.example.rickandmortyplayground.domain.adapters.CharacterAdapter
import com.example.rickandmortyplayground.presentation.viewmodels.CharactersViewModel
import com.example.rickandmortyplayground.util.Constants.Companion.QUERY_PAGE_SIZE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.characters_fragment) {

    private var _binding: CharactersFragmentBinding? = null
    private val binding get() = _binding!!
    private val charactersViewModel: CharactersViewModel by viewModels()
    private var charactersAdapter: CharacterAdapter? = null
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharactersFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        setUpRecyclerView()
        charactersAdapter?.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("character", it)
            }
            findNavController().navigate(
                R.id.action_charactersFragment_to_characterDetailFragment,
                bundle
            )
        }
        fetchingData()
    }

    private fun setUpRecyclerView() {
        charactersAdapter = CharacterAdapter()
        binding.charactersRv.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@CharactersFragment.scrollListener)
        }
    }

    private fun fetchingData() {
        activity?.let {
            charactersViewModel.newCharacters
                .observe(viewLifecycleOwner, { characters ->
                    charactersAdapter?.differ?.submitList(characters.results)
                    hideProgressBar()
                })
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                charactersViewModel.nextPage()
                isScrolling = false
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

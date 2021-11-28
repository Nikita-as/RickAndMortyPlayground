package com.example.rickandmortyplayground.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.rickandmortyplayground.R
import com.example.rickandmortyplayground.databinding.CharacterDetailFragmentBinding
import com.example.rickandmortyplayground.presentation.viewmodels.CharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.character_detail_fragment) {

    private var _binding: CharacterDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharacterDetailFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterId = args.character?.id
        characterDetailViewModel.getCharacterById(characterId)
        showProgressBar()
        fetchingData()
        hideProgressBar()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchingData() {
        activity?.let {
            characterDetailViewModel.newCharacterDetail
                .observe(viewLifecycleOwner, { character ->
                    binding.nameDetail.text = character.name
                    binding.genderDetail.text = character.gender.toString()
                    binding.dimensionDetail.text = binding.dimensionDetail.text
                    binding.originDetail.text = character.origin?.name
                    binding.locationDetail.text = character.location?.name
                    binding.typeDetail.text = character.type
                    binding.episodesDetail.text = character.episode?.size.toString()
                    binding.let {
                        Glide.with(it.root)
                            .load(character.image)
                            .into(binding.characterImageDetail)
                    }
                    binding.characterSpeciesAndStatusDetail.text =
                        "${character.species} - ${character.status}"
                })

        }
    }

    private fun hideProgressBar() {
        binding.progressBarDetail.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarDetail.visibility = View.VISIBLE
    }

}
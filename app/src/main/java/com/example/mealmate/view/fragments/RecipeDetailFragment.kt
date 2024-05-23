package com.example.mealmate.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.R
import com.example.mealmate.databinding.FragmentRecipeDetailBinding
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.view.activities.IngredientsSelectorActivity
import com.example.mealmate.view.adapters.IngredientsAdapter
import com.example.mealmate.view.adapters.StepsAdapter
import com.example.mealmate.view.util.ImageUtil
import com.example.mealmate.viewmodel.RecipeDetailMode
import com.example.mealmate.viewmodel.RecipeDetailViewModel
import java.io.File
import java.util.UUID

class RecipeDetailFragment : Fragment(), PopupMenu.OnMenuItemClickListener{

    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: RecipeDetailViewModel by viewModels()
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onGalleryResult(result)
    }
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onCameraResult(result)
    }
    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        onCameraPermissionResult(permissions)
    }
    private val galleryPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        onGalleryPermissionResult(isGranted)
    }
    private val ingredientSelectorLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onIngredientResult(result)
    }

    private fun onIngredientResult(result: ActivityResult?) {
        result?.data?.extras?.getString("ingredient")?.let { ingredient ->

            viewModel.addIngredient(ingredient)
        }
    }

    private lateinit  var ingredientsAdapter: IngredientsAdapter
    private lateinit  var stepsAdapter: StepsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("id")?.let {
            viewModel.downloadRecipe(it)
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
        viewModel.recipeId.value = UUID.randomUUID().toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerViews()
        setupAddStep()
        viewModel.mode.observe(viewLifecycleOwner) { mode ->
            when(mode){
                RecipeDetailMode.VISUALIZATION -> setupVisualization()
                RecipeDetailMode.EDITION -> setupEdition()
                else -> {}
            }
        }

        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let { displayRecipe(it) }
        }
        viewModel.ingredients.observe(viewLifecycleOwner){ingredients ->
            ingredients?.let {displayIngredients(it)}
        }

        viewModel.steps.observe(viewLifecycleOwner){steps ->
            steps?.let {displaySteps(it)}
        }
        viewModel.imageSelected.observe(viewLifecycleOwner) { uri ->
            ImageUtil().renderImageCenterCrop(requireContext(), uri, binding.imageView)
        }
        binding.btnUpload.setOnClickListener {
            viewModel.uploadImage()
        }
        binding.imageView.setOnClickListener{
            showPopup(it)
        }
        binding.addIngredient.setOnClickListener {
            ingredientSelectorLauncher.launch(Intent(activity, IngredientsSelectorActivity::class.java))
        }
        binding.editRecipe.setOnClickListener{
            viewModel.tryToEdit()
        }
        binding.btnUpload.setOnClickListener{
            viewModel.uploadRecipe()
        }

    }

    private fun displaySteps(steps: List<String>) {
        stepsAdapter.updateStepsList(steps)
    }

    private fun setupAddStep(){
        binding.btnAddStep.setOnClickListener{
            binding.addStepContainer.root.visibility = View.VISIBLE
            it.visibility = View.GONE
        }

        binding.addStepContainer.cancelStep.setOnClickListener{
            binding.btnAddStep.visibility = View.VISIBLE
            it.visibility = View.GONE
        }

        binding.addStepContainer.addStep.setOnClickListener{
            viewModel.addStep(binding.addStepContainer.newStep.text.toString())
            binding.btnAddStep.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
    }

    private fun setupEdition() {
        with(binding){
            kalRecipe.isEnabled = true
            weightRecipe.isEnabled = true
            timeRecipe.isEnabled = true
            portionRecipe.isEnabled = true
            editRecipe.visibility = View.GONE
            imageCard.setOnClickListener{showPopup(it)}
            addIngredient.visibility = View.VISIBLE
            btnAddStep.visibility = View.VISIBLE
            addStepContainer.root.visibility = View.GONE
            btnUpload.visibility = View.VISIBLE

        }
    }

    private fun setupVisualization() {
        with(binding){
            kalRecipe.isEnabled = false
            weightRecipe.isEnabled = false
            timeRecipe.isEnabled = false
            portionRecipe.isEnabled = false
            editRecipe.visibility = View.VISIBLE
            imageCard.setOnClickListener(null)
            addIngredient.visibility = View.GONE
            btnAddStep.visibility = View.GONE
            addStepContainer.root.visibility = View.GONE
            btnUpload.visibility = View.GONE
        }
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.media_menu)
        popup.show()
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_camera -> openCamera()
            R.id.action_gallery -> openGallery()
        }
        return true
    }


    private fun setupRecyclerViews(){
        ingredientsAdapter = IngredientsAdapter(
            viewModel.recipe.value?.ingredients ?: emptyList()
        )
        stepsAdapter = StepsAdapter(
            viewModel.recipe.value?.steps ?: emptyList()
        )

        binding.ingredientsRecipe.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
           adapter = ingredientsAdapter
        }
        binding.stepsRecipe.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = stepsAdapter
        }
    }

    private fun displayRecipe(recipe: Recipe) {
        binding.titleRecipe.text = recipe.title
        binding.weightRecipe.setText(recipe.weight)
        binding.kalRecipe.setText(recipe.kal)
        binding.portionRecipe.setText(recipe.portion)
        binding.timeRecipe.setText(recipe.time)
    }

    private fun displayIngredients(ingredients:List<String>){
        ingredientsAdapter.updateIngredientsList(ingredients)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File("${activity?.getExternalFilesDir(null)}/profile.png")
        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireActivity().packageName,
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        viewModel.tempCameraImage.postValue(uri)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    private fun isCameraPermissionsGranted(): Boolean {
        val permissions = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()
        var allGranted = true
        permissions.forEach {
            allGranted = ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_DENIED && allGranted
        }
        return allGranted
    }

    private fun isGalleryPermissionsGranted(): Boolean {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED
        }
        return true
    }

    private fun requestCameraPermissions() {
        cameraPermissionLauncher.launch(
            mutableListOf(
                Manifest.permission.CAMERA
            ).toTypedArray()
        )
    }

    private fun requestGalleryPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun onCameraResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.imageSelected.postValue(viewModel.tempCameraImage.value)
        }
    }

    private fun onGalleryResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            viewModel.imageSelected.postValue(uri)
        }
    }

    private fun onCameraPermissionResult(permissions: Map<String, Boolean>) {
        permissions.entries.forEach {
            if (!it.value) {
                Toast.makeText(requireContext(), "Permission ${it.key} denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onGalleryPermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            Toast.makeText(requireContext(), "Permission GALLERY denied", Toast.LENGTH_SHORT).show()
        }
    }
}

package com.example.mealmate.view.fragments

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.Code

class NewPostFragment : Fragment() {

    private val pick_image = 1
    private lateinit var selectedImageUri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_newpost, container, false)

        val tituloEditText = view.findViewById<EditText>(R.id.editTextTitulo)
        val descripcionEditText = view.findViewById<EditText>(R.id.editTextDescripcion)
        val imageViewPrevisualizacion = view.findViewById<ImageView>(R.id.imageViewPrevisualizacion)
        val botonSeleccionarImagen = view.findViewById<Button>(R.id.buttonSeleccionarImagen)
        val botonGuardar = view.findViewById<Button>(R.id.buttonGuardar)

        botonSeleccionarImagen.setOnClickListener{
            seleccionarImagen()
        }

        botonGuardar.setOnClickListener {
            var titulo = tituloEditText.text.toString()
            var descripcion = descripcionEditText.text.toString()

            if (::selectedImageUri.isInitialized){
                guardarPostEnFirebase(titulo, descripcion, selectedImageUri.toString())
            }else{
                Toast.makeText(requireContext(), "Por favor, selecciona una imagen", Toast.LENGTH_SHORT).show()
            }
        }

        return view

    }

    private fun seleccionarImagen() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), pick_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == pick_image && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            selectedImageUri = data.data!!
            val imageViewPrevisualizacion = view?.findViewById<ImageView>(R.id.imageViewPrevisualizacion)
            imageViewPrevisualizacion?.setImageURI(selectedImageUri)
            imageViewPrevisualizacion?.visibility = View.VISIBLE
        }
    }

    private fun guardarPostEnFirebase(titulo: String, descripcion: String, imageUrl: String){
        val db = FirebaseFirestore.getInstance()

        val post = hashMapOf(
            "titulo" to titulo,
            "descripcion" to descripcion,
            "imageUrl" to imageUrl
        )

        db.collection("posts")
            .add(post)
            .addOnSuccessListener { documentReference ->
                Log.d("FragmentNewPost", "Post añadido con ID: ${documentReference.id}")
                Toast.makeText(requireContext(), "Post guardado exitosamente", Toast.LENGTH_SHORT).show()
                limpiarCampos()    
            }
            .addOnFailureListener{ e ->
                 Log.w("FragmentNewPost", "Error al añadir el post", e)
                 Toast.makeText(requireContext(), "Error al guardar el post", Toast.LENGTH_SHORT).show()
                 }
    }

    private fun limpiarCampos(){
        val tituloEditText = view?.findViewById<EditText>(R.id.editTextTitulo)
        val descripcionEditText = view?.findViewById<EditText>(R.id.editTextDescripcion)
        val imageViewPrevisualizacion = view?.findViewById<ImageView>(R.id.imageViewPrevisualizacion)

        tituloEditText?.text?.clear()
        descripcionEditText?.text?.clear()
        imageViewPrevisualizacion?.setImageURI(null)
        imageViewPrevisualizacion?.visibility = View.GONE
    }

}
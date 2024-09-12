package com.example.daenotes.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daenotes.DB.Nota
import com.example.daenotes.R
import com.example.daenotes.databinding.ActivityMainBinding
import com.example.daenotes.ui.adapter.NoteRegister

class MainActivity : AppCompatActivity(), NoteRegister.OnNoteClickListener {
    private lateinit var binding: ActivityMainBinding

    // Lista inicial para la app de Notas
    private val d = arrayListOf(
        Nota("Nota 1", "Contenido de la nota 1", "#FFFAD2")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Partes del Main para ejecutar los procesos
        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupRecyclerView() {
        // Inicializa el recycler para que funcione correctamente
        binding.lstNote.adapter = NoteRegister(d,this)
        binding.lstNote.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun setupEventListeners() {
        // boton de nota, agrega una nueva nota
        binding.bttAddNote.setOnClickListener {
            buildNoteDialog()
        }

    }

    private fun buildNoteDialog(note: Nota? = null) {
        // crea una ventana emergente donde se usa el XML add_note
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Nota")

        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.activity_add_note, null, false)

        // obtiene los datos de los campos del add_note y su edicion
        val title: EditText = viewInflated.findViewById(R.id.txtTitleNote)
        val content: EditText = viewInflated.findViewById(R.id.txtContentNote)
        title.setText(note?.titulo)
        content.setText(note?.contenido)
        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
            val name = title.text.toString()
            val content = content.text.toString()

            if (note != null) { // si es una edicion, edita
                note.titulo = name
                note.contenido = content
                editNote(note)
            }else{ // si no, agrega
                addNote(name, content)
            }

        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }
        builder.show() // muestra la ventana emergente
    }

    private fun buildColorNote(note: Nota? = null) {
        // crea una ventana emergente donde se usa el XML add_note
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Color Nota")

        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.color_item_layout, null, false)

        val colorRed: Button = viewInflated.findViewById(R.id.bttRed)
        val colorYellow: Button = viewInflated.findViewById(R.id.bttYellow)
        val colorBlue: Button = viewInflated.findViewById(R.id.bttBlue)
        val colorPink: Button = viewInflated.findViewById(R.id.bttPink)
        val colorGreen: Button = viewInflated.findViewById(R.id.bttGreen)
        val colorCian: Button = viewInflated.findViewById(R.id.bttCian)
        val colorOrange: Button = viewInflated.findViewById(R.id.bttOrange)
        val colorBlack: Button = viewInflated.findViewById(R.id.bttBlack)
        val colorNt: Button = viewInflated.findViewById(R.id.bttColorNt)

        fun setColor(color: String){ // funcion para cambiar el color del layout
            note?.color = color
            val adapter = binding.lstNote.adapter as NoteRegister
            adapter.updateNote(note!!)
        }

        colorRed.setOnClickListener {
            setColor("#FF0000")  }
        colorRed.setOnClickListener {
            setColor("#FF0000") }
        colorYellow.setOnClickListener {
            setColor("#FFFF00") }
        colorBlue.setOnClickListener {
            setColor("#0000FF") }
        colorPink.setOnClickListener {
            setColor("#FFC0CB") }
        colorGreen.setOnClickListener {
            setColor("#008000") }
        colorCian.setOnClickListener {
            setColor("#00FFFF") }
        colorOrange.setOnClickListener {
            setColor("#FFA500") }
        colorBlack.setOnClickListener {
            setColor("#000000") }
        colorNt.setOnClickListener {
            setColor("#FFFAD2") }

        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            //setColor("#FFFAD2")
            dialog.cancel()
        }
        builder.show()
    }

    private fun editNote(note: Nota) { // editar nota
        val adapter = binding.lstNote.adapter as NoteRegister
        adapter.updateNote(note)
    }

    private fun addNote(name: String, content: String) { //agregar nota
        val note = Nota(name, content)
        val adapter = binding.lstNote.adapter as NoteRegister
        adapter.addNote(note)
    }

    override fun onNoteEdit(note: Nota) {
        buildNoteDialog(note)
    }

    override fun onNoteDelete(note: Nota) {
        val adapter = binding.lstNote.adapter as NoteRegister
        adapter.deleteNote(note)
    }

    override fun onNoteColor(note: Nota) {
        buildColorNote(note)
    }
}
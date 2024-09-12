package com.example.daenotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.daenotes.DB.Nota
import com.example.daenotes.R
import com.example.daenotes.databinding.NoteRegisterLayoutBinding

class NoteRegister(
    private val notes: ArrayList<Nota>,
    private val listener: OnNoteClickListener
):RecyclerView.Adapter<NoteRegister.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_register_layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], listener)
    }

    fun addNote(note: Nota) {
        notes.add(1, note)
        notifyItemInserted(1)
    }
    fun updateNote(note: Nota) {
        val index = notes.indexOf(note)
        notes[index] = note
        notifyItemChanged(index)
    }
    fun deleteNote(note: Nota) {
        val index = notes.indexOf(note)
        notes.removeAt(index)
        notifyItemRemoved(index)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Nota, listener: OnNoteClickListener) {
            val binding = NoteRegisterLayoutBinding.bind(itemView)
            binding.pgBackNote.setBackgroundColor(android.graphics.Color.parseColor(note.color))

            binding.ntTxtTittle.text = note.titulo
            binding.ntTxtContent.text = note.contenido
            binding.bttEdit.setOnClickListener {
                listener.onNoteEdit(note)
            }
            binding.bttDelete.setOnClickListener {
                listener.onNoteDelete(note)
            }
            binding.bttColor.setOnClickListener {
                listener.onNoteColor(note)
            }

        }
    }
    public interface OnNoteClickListener {
        fun onNoteEdit(note: Nota)
        fun onNoteDelete(note: Nota)
        fun onNoteColor(note: Nota)
    }

}
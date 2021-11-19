package com.example.guessnumber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guessnumber.data.GameDataBase
import com.example.guessnumber.data.Record
import kotlinx.android.synthetic.main.row_record_list.view.*

class RecyclerAdapter(var record: List<Record>): RecyclerView.Adapter<RecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_record_list, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.name.setText(record[position].nickname)
        holder.count.setText(record[position].counter.toString())
    }

    override fun getItemCount(): Int {
        return record.size
    }

}
class RecordViewHolder(v: View): RecyclerView.ViewHolder(v){
    val name: TextView = v.record_list_name;
    val count: TextView = v.recycler_list_count;
}
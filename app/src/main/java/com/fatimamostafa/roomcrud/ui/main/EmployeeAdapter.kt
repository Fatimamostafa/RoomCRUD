package com.fatimamostafa.roomcrud.ui.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fatimamostafa.roomcrud.R
import com.fatimamostafa.roomcrud.database.EmployeeModel
import com.fatimamostafa.roomcrud.utils.CircleTransformation
import com.squareup.picasso.Picasso

import java.util.*

class EmployeeAdapter(
    private var employees: List<EmployeeModel?>
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    private var listener: ((EmployeeModel) -> Unit)? = null

    fun swapData(it: List<EmployeeModel?>?) {
        it?.let {
            employees = it
        }
        notifyDataSetChanged()
    }

    fun setOnPlayerTapListener(listener: ((EmployeeModel) -> Unit)) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = employees.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text =
            String.format(
                Locale.getDefault(), "%s %s", employees[position]?.firstName,
                employees[position]?.lastName
            )
        holder.tvAgeGender.text = employees[position]?.age.toString()


        Picasso.get()
            .load(employees[position]?.imageUrl)
            .error(R.drawable.error_list_image)
            .placeholder(R.drawable.default_list_image)
            .transform(CircleTransformation())
            .into(holder.imageEmployee)

        holder.itemView.setOnClickListener {
            listener?.invoke(employees[position]!!)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageEmployee: ImageView = itemView.findViewById(R.id._iv_employee)
        val tvName: TextView = itemView.findViewById(R.id.tv_employee_name)
        val tvAgeGender: TextView = itemView.findViewById(R.id.tv_age_gender)
    }
}
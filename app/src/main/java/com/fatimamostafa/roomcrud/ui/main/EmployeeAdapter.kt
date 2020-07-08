package com.fatimamostafa.roomcrud.ui.main

import android.app.Application
import android.graphics.BitmapFactory
import android.util.Base64
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
    private var employees: List<EmployeeModel?>,
    private val application: Application,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {


    companion object {
        var mClickListener: ItemClickListener? = null
    }

    interface ItemClickListener {
        fun clickEdit(item: EmployeeModel)
        fun clickDelete(item: EmployeeModel)
    }

    fun swapData(it: List<EmployeeModel?>?) {
        it?.let {
            employees = it
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = employees.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mClickListener = listener
        holder.tvName.text =
            String.format(
                Locale.getDefault(), "%s %s", employees[position]?.firstName,
                employees[position]?.lastName
            )
        holder.tvAgeGender.text = application.getString(
            R.string.employee_age_gender,
            employees[position]?.age,
            if (employees[position]?.gender == "M" || employees[position]?.gender == "Male") "Male" else "Female"
        )


        if (employees[position]?.imageUrl?.contains("https://")!!) {
            Picasso.get()
                .load(employees[position]?.imageUrl)
                .error(R.drawable.error_list_image)
                .placeholder(R.drawable.default_list_image)
                .transform(CircleTransformation())
                .into(holder.imageEmployee)

        } else {
            val imageBytes = Base64.decode(employees[position]?.imageUrl, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            holder.imageEmployee.setImageBitmap(decodedImage)
        }


        holder.ivDelete.setOnClickListener {
            listener.clickDelete(employees[position]!!)
        }
        holder.ivEdit.setOnClickListener {
            listener.clickEdit(employees[position]!!)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageEmployee: ImageView = itemView.findViewById(R.id._iv_employee)
        val tvName: TextView = itemView.findViewById(R.id.tv_employee_name)
        val tvAgeGender: TextView = itemView.findViewById(R.id.tv_age_gender)
        val ivEdit: ImageView = itemView.findViewById(R.id.iv_edit)
        val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete)
    }
}
package com.example.birthdayreminder

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NameDateDialogFragment : DialogFragment() {

    private var selectedDate : String = ""
    private var onNameDateSetListener : ( ( name : String, birthday : String ) -> Unit )? = null

    override fun onCreateView(

        inflater : LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate( R.layout.fragment_name_date_dialog, container, false )

        val nameEditText     = view.findViewById< EditText >( R.id.nameEditText )
        val selectDateButton = view.findViewById< Button >( R.id.selectDateButton )
        val saveButton       = view.findViewById< Button >( R.id.saveButton )

        selectDateButton.setOnClickListener {

            showDatePickerDialog()

        }

        saveButton.setOnClickListener {

            val enteredName = nameEditText.text.toString()
            if ( enteredName.isNotBlank() && selectedDate.isNotBlank() ) {

//                ( activity as MainActivity ).updateNameAndDate( enteredName, selectedDate )
                onNameDateSetListener?.invoke( enteredName, selectedDate )
                dismiss()

            }
        }

        return view

    }

    fun setOnNameDateSetListener( listener : ( name : String, birthday : String ) -> Unit ) {

        onNameDateSetListener = listener

    }

    private fun showDatePickerDialog() {

        val calendar = Calendar.getInstance()
        val year     = calendar.get( Calendar.YEAR )
        val month    = calendar.get( Calendar.MONTH )
        val day      = calendar.get( Calendar.DAY_OF_MONTH )

        val datePickerDialog = DatePickerDialog(

            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->

                val sdf = SimpleDateFormat( "MMM. dd, yyyy", Locale.getDefault() )
                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set( selectedYear, selectedMonth, selectedDay )
                selectedDate = sdf.format( selectedDateCalendar.time )

                val txtbirthdayView = view?.findViewById< TextView >( R.id.txtbirthdayView )

                if ( txtbirthdayView != null ) {

                    txtbirthdayView.text = selectedDate

                }
            },
            year, month, day
        )

        datePickerDialog.show()

    }

}

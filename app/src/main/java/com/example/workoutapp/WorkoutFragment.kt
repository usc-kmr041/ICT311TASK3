
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.workoutapp.R
import com.example.workoutapp.Workout

class WorkoutFragment : Fragment() {
    private lateinit var workout: Workout
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var groupCheckbox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workout = Workout()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout, container, false)

        titleField = view.findViewById(R.id.workout_title) as EditText
        dateButton = view.findViewById(R.id.workout_date) as Button
        groupCheckbox = view.findViewById(R.id.workout_group) as CheckBox
            dateButton.apply {
                text = workout.date.toString()
                isEnabled = false
            }
        return view
    }


    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ){
                //blankspace//
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ){
                workout.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //blank space//
            }
        }

        titleField.addTextChangedListener(titleWatcher)

        groupCheckbox.apply {
            setOnCheckedChangeListener {_, isChecked -> workout.isGroup = isChecked
            }
        }
    }
}
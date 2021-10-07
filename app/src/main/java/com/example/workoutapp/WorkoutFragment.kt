
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workoutapp.R
import com.example.workoutapp.Workout
import com.example.workoutapp.WorkoutDetailViewModel
import com.example.workoutapp.WorkoutListViewModel
import java.util.*

private const val TAG = "Workout Fragment"
private const val ARG_WORKOUT_ID = "workout_id"

class WorkoutFragment : Fragment() {
    private lateinit var workout: Workout
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var groupCheckbox: CheckBox

    private val workoutDetailViewModel: WorkoutDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workout = Workout()

        val workoutId: UUID = arguments?.getSerializable(ARG_WORKOUT_ID) as UUID
        workoutDetailViewModel.loadWorkout(workoutId)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        workoutDetailViewModel.workoutLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                workout -> workout?.let { this.workout = workout
                updateUI()}
            }
        )
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

    override fun onStop() {
        super.onStop()
        workoutDetailViewModel.saveWorkout(workout)
    }

    private fun updateUI(){
        titleField.setText(workout.title)
        dateButton.text = workout.date.toString()
        groupCheckbox.apply {
            isChecked = workout.isGroup
            jumpDrawablesToCurrentState()
        }
    }

    companion object {
        fun newInstance(workoutId: UUID): WorkoutFragment{
            val args = Bundle().apply{
                putSerializable(ARG_WORKOUT_ID,workoutId)
            }
            return WorkoutFragment().apply{
                arguments = args
            }
        }
    }
}
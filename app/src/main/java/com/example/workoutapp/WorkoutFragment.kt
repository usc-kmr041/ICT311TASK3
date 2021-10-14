
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workoutapp.*
import java.util.*

private const val TAG = "Workout Fragment"
private const val ARG_WORKOUT_ID = "workout_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0

class WorkoutFragment : Fragment(), DatePickerFragment.Callbacks {
    private lateinit var workout: Workout
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var groupCheckbox: CheckBox
    private lateinit var locationField: EditText
    private lateinit var starttimeField: EditText
    private lateinit var endtimeField: EditText



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
        locationField = view.findViewById(R.id.workout_location) as EditText
        starttimeField = view.findViewById(R.id.start_time) as EditText
        endtimeField = view.findViewById(R.id.end_time) as EditText

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
        val locationWatcher = object : TextWatcher {

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
                workout.location = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //blank space//
            }
        }

        locationField.addTextChangedListener(locationWatcher)

        val starttimeWatcher = object : TextWatcher {

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
                workout.starttime = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //blank space//
            }
        }

        starttimeField.addTextChangedListener(starttimeWatcher)

        val endtimeWatcher = object : TextWatcher {

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
                workout.endttime = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //blank space//
            }
        }

        endtimeField.addTextChangedListener(endtimeWatcher)



        groupCheckbox.apply {
            setOnCheckedChangeListener {_, isChecked -> workout.isGroup = isChecked
            }
        }

        dateButton.setOnClickListener{
            DatePickerFragment.newInstance(workout.date).apply{
                setTargetFragment(this@WorkoutFragment, REQUEST_DATE)
                show(this@WorkoutFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        workoutDetailViewModel.saveWorkout(workout)
    }

    override fun onDateSelected(date:Date){
        workout.date = date
        updateUI()
    }


    private fun updateUI(){
        titleField.setText(workout.title)
        locationField.setText(workout.location)
        starttimeField.setText(workout.starttime)
        endtimeField.setText(workout.endttime)
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
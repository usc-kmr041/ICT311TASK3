package com.example.workoutapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "WorkoutListFragment"

class WorkoutListFragment : Fragment() {

    private lateinit var workoutRecyclerView: RecyclerView
    private var adapter: WorkoutAdapter? = WorkoutAdapter(emptyList())

    private val workoutListViewModel: WorkoutListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout_list, container, false)
        workoutRecyclerView = view.findViewById(R.id.workout_recycler_view) as RecyclerView
        workoutRecyclerView.layoutManager = LinearLayoutManager(context)
        workoutRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        workoutListViewModel.workoutListLiveData.observe(
            viewLifecycleOwner,
            Observer { workouts -> workouts?.let{
                Log.i(TAG, "Got workouts ${workouts.size}")
                updateUI(workouts)
            } }
        )
    }

    private fun updateUI(workouts:List<Workout>) {
        adapter = WorkoutAdapter(workouts)
        workoutRecyclerView.adapter = adapter
    }
    private inner class WorkoutHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var workout: Workout

            private val titleTextView: TextView = itemView.findViewById(R.id.workout_title)
            private val locationTextView: TextView = itemView.findViewById(R.id.workout_location)
            private val dateTextView: TextView = itemView.findViewById(R.id.workout_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(workout: Workout){
            this.workout = workout
            titleTextView.text = this.workout.title
            locationTextView.text = this.workout.location
            dateTextView.text = this.workout.date.toString()
        }

        override fun onClick(v: View){
            Toast.makeText(context, "${workout.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }
    private inner class WorkoutAdapter(var workouts: List<Workout>)
        : RecyclerView.Adapter<WorkoutHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : WorkoutHolder {
                val view = layoutInflater.inflate(R.layout.list_item_workout, parent, false)
                return WorkoutHolder(view)
            }
            override fun getItemCount() = workouts.size
        override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
            val workout = workouts[position]
            holder.bind(workout)
        }
        }

    companion object {
        fun newInstance(): WorkoutListFragment {
            return WorkoutListFragment()
        }
    }
}
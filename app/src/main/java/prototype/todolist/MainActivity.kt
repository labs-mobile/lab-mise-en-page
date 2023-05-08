package prototype.todolist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import prototype.todolist.data.TaskEntry
import prototype.todolist.data.TaskRepository
import prototype.todolist.databinding.ActivityMainBinding
import prototype.todolist.ui.TaskAdapter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter : TaskAdapter
    private lateinit var listener : TaskAdapter.OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // todo : voir la meilleur façon de création des événement avec kotlin
        this.listener = object : TaskAdapter.OnItemClickListener {
            override fun onItemClick(task: TaskEntry) {

                Toast.makeText(applicationContext,"Update $task", Toast.LENGTH_LONG).show()
                task.title = task.title + "+"

                // Todo : supprimer ces deux lignes et voir est ce que RecyclerView continue d'afficher les updates ?
                val repository = TaskRepository()
                repository.save(task)

                taskAdapter.notifyDataSetChanged()

            }
        }


        this.taskAdapter =  TaskAdapter(listener)
        binding.apply {
            // Todo : version 2 : Ajoutez la possibilité de choisir le layoutManager depuis un button sur le menu
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.adapter =  taskAdapter
            floatingActionButton.setOnClickListener{

                val repository = TaskRepository()
                val newTask = repository.newTask();
                newTask.title = "New task" + Random.nextInt()
                repository.save(newTask)

                Toast.makeText(applicationContext,"Ajouter une tâche", Toast.LENGTH_LONG).show()
                taskAdapter.notifyDataSetChanged()
            }

        }

    }

}
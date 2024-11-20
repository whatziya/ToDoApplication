package com.whatziya.todoapplication.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whatziya.todoapplication.ui.screens.task.TaskScreen
import com.whatziya.todoapplication.ui.screens.task.TaskViewModel
import com.whatziya.todoapplication.ui.screens.tasks.TasksScreen
import com.whatziya.todoapplication.ui.screens.tasks.TasksViewModel

const val KEY_TASK_ID = "taskId"
const val DEFAULT_TASK_ID = ""

sealed class Screen(val route: String) {
    data object Tasks : Screen("tasks")
    data object Task : Screen("task/{$KEY_TASK_ID}")
}

@Composable
fun AppNavigation(
) {
    val navController = rememberNavController()
    val tasksViewModel: TasksViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Tasks.route
    ) {

        composable(route = Screen.Tasks.route) {
            TasksScreen(
                viewModel = tasksViewModel,
                onAddNewTaskButtonClick = {
                    navController.navigate(Screen.Task.route)
                },
                onTaskClick = { taskId ->
                    navController.navigate("${Screen.Task.route}/$taskId")
                }
            )
        }

        composable(route = Screen.Task.route) {
            TaskScreen(
                viewModel = hiltViewModel(),
                taskId = DEFAULT_TASK_ID,
                onExit = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Screen.Task.route}/{$KEY_TASK_ID}",
            arguments = listOf(navArgument(KEY_TASK_ID) { defaultValue = DEFAULT_TASK_ID })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString(KEY_TASK_ID) ?: DEFAULT_TASK_ID
            TaskScreen(
                viewModel = hiltViewModel(),
                taskId = taskId,
                onExit = { navController.popBackStack() }
            )
        }
    }
}
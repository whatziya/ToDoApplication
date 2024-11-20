package com.whatziya.todoapplication.utils

object Constants {
    object FileName {
        const val SHARED_PREFS = "todo"
        const val DATABASE_NAME = "todo_database"
    }

    object EntityName {
        const val TASKS = "tasks"
    }

    object TaskEntityName{
        const val ID = "id"
        const val TEXT = "text"
        const val IMPORTANCE = "importance"
        const val DEADLINE = "deadline"
        const val IS_COMPLETED = "is_completed"
        const val CREATED_AT = "created_at"
        const val MODIFIED_AT = "modified_at"
    }

    object Endpoint {
        const val LIST = "list"
    }

    object Header {
        const val TOKEN_TITLE = "Authorization"
        const val ACCEPT_TITLE = "accept"
        const val APPLICATION_JSON_VALUE = "application/json"
        const val TOKEN_TYPE = "Bearer"
        const val ACCESS_TOKEN = "Nimrodel"
    }

}
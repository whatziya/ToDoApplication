package com.whatziya.todoapplication.utils

object Constants {
    object FileName {
        const val SHARED_PREFS = "todo"
        const val DATABASE_NAME = "todo_database"
    }

    object EntityName {
        const val TASKS = "tasks"
        const val DELETED = "deleted"
        const val EDIT_REMOTE = "edit_remote"
    }

    object TaskEntityName {
        const val ID = "id"
        const val TEXT = "text"
        const val IMPORTANCE = "importance"
        const val DEADLINE = "deadline"
        const val IS_COMPLETED = "is_completed"
        const val CREATED_AT = "created_at"
        const val MODIFIED_AT = "modified_at"
    }

    object DeletedEntityName {
        const val ID = "id"
    }

    object EditRemoteEntityName {
        const val ID = "id"
        const val ACTION = "action"
    }

    object EditRemoteAction {
        const val ADD = "add"
        const val UPDATE = "update"
    }

    object Endpoint {
        const val LIST = "list"
    }

    object Header {
        const val TOKEN_TITLE = "Authorization"
        const val TOKEN_TYPE = "Bearer"
        const val ACCESS_TOKEN = "Nimrodel"
    }
}

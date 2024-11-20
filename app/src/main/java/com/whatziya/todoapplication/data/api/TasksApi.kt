package com.whatziya.todoapplication.data.api


import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.dto.response.TasksResDto
import com.whatziya.todoapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TasksApi {
    @GET(Constants.Endpoint.LIST)
    suspend fun getAll(): TasksResDto

    @POST(Constants.Endpoint.LIST)
    suspend fun add(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body data: TaskReqDto
    ): TaskResDto

    @PUT(Constants.Endpoint.LIST + "/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body data: TaskReqDto
    ): TaskResDto

    @DELETE(Constants.Endpoint.LIST + "/{id}")
    suspend fun delete(
        @Path("id") id: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): TaskResDto
}
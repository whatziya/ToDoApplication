package com.whatziya.todoapplication.data.api


import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TasksService {
    @GET(Constants.Endpoint.LIST)
    suspend fun getAll(): Response<TaskResDto.GetAll>
    @POST(Constants.Endpoint.LIST)
    suspend fun add(@Body data : TaskReqDto.AddDto): Response<TaskResDto.Post>

    @PUT(Constants.Endpoint.LIST + "/{id}")
    suspend fun update(@Path("id") id: String, @Body data : TaskReqDto.UpdateDto): Response<TaskResDto.Update>

    @DELETE(Constants.Endpoint.LIST + "/{id}")
    suspend fun delete(@Path("id") id: String): Response<TaskResDto.Delete>
}
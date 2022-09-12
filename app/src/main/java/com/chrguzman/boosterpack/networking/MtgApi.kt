package com.chrguzman.boosterpack.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MtgApi {

    @GET("v1/sets/{setCode}/booster")
    suspend fun getBooster(
        @Path("setCode") setCode: String
    ): Response<BoosterResponse>
}
package com.example.a01_wskpolice.Model.apis.retrofit

import com.example.a01_wskpolice.Model.Data.session3.criminalCase.CriminalCases
import com.example.a01_wskpolice.Model.Data.session2.department.Department
import com.example.a01_wskpolice.Model.Data.session2.department.Departments
import com.example.a01_wskpolice.Model.Data.session2.wanted.Wanteds
import com.example.a01_wskpolice.Model.Data.session3.evidence.Evidences
import com.example.a01_wskpolice.Model.Data.session3.criminalCase.NewCriminalCase
import com.example.a01_wskpolice.Model.Data.session3.criminalCase.NewCriminalCases
import com.example.a01_wskpolice.Model.Data.session3.evidence.NewEvidence
import com.example.a01_wskpolice.Model.Data.session3.evidence.NewEvidences
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService{
    @GET("api/department/{id}")
    suspend fun getDepartmentById(@Path("id") id: String): Department

    @GET("api/department")
    suspend fun getAllDepartments(): Departments

    @GET("api/wanted")
    suspend fun getAllWanted(): Wanteds

    @GET("/api/criminal_case")
    suspend fun getAllCriminalCases(): CriminalCases

    @GET("/api/evidence")
    suspend fun getAllEvidence(): Evidences

    @POST("/api/criminal_case")
    suspend fun postData(@Body requestBody: NewCriminalCase): NewCriminalCases

    @POST("/api/evidence")
    suspend fun postEvidence(@Body requestBody: NewEvidence): NewEvidences



}
package com.example.a22housexam2.Networking.Model

import com.google.gson.annotations.SerializedName

class GitHubResponseModel{
    @SerializedName("total_count")
    val totalCount : Int = 0

    @SerializedName("items")
    val items:List<GitHubRepoModel> = listOf()
}
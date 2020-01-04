package com.example.a22housexam2.Networking.NetworkingInterface

import com.example.a22housexam2.Networking.RetrofitCreator.RetrofitCreator
import com.example.a22housexam2.Networking.Model.GitHubResponseModel
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

class GitHubApi{
    interface GitHubApiImpl{
        @GET("/search/users")
            fun getRepoList(@Query("q") query: String) : Observable<GitHubResponseModel>
        }

        companion object{
            fun getRepoList(query : String) : Observable<GitHubResponseModel> {
            return RetrofitCreator.create(GitHubApiImpl::class.java).getRepoList(query)
        }
    }
}
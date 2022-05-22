package code.hyunwa.githubuserprofile.data.source.remote

import code.hyunwa.githubuserprofile.data.source.remote.network.ApiService


class RemoteDataSource(private val api: ApiService) {
//    suspend fun login(email: String, password: String) = api.login(email, password)
    suspend fun getUsers(page: Int?=null, row: Int?=null) = api.getUsers(page, row)
}
package com.gulfappdeveloper.project3.data.remote

import android.util.Log
import com.gulfappdeveloper.project3.domain.remote.error.Error
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.TableOrder
import com.gulfappdeveloper.project3.domain.remote.get.kot_cancel_privilege_checker.KotCancelPrivilege
import com.gulfappdeveloper.project3.domain.remote.get.kot_list.UserOrder
import com.gulfappdeveloper.project3.domain.remote.get.login.User
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.MultiSizeProduct
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import com.gulfappdeveloper.project3.domain.remote.license.LicenseRequestBody
import com.gulfappdeveloper.project3.domain.remote.license.LicenseResponse
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.domain.remote.put.EditKOTBasic
import com.gulfappdeveloper.project3.domain.services.ApiService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException
import java.util.Date

private const val TAG = "ApiServiceImpl"

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {


    override suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<WelcomeMessage>> {
        Log.d(TAG, "getWelcomeMessage: ${Date()}")
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                Log.e(TAG, "getWelcomeMessage: ${Date()}")
                val statusCode = httpResponse.status.value
                //Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                Log.e(TAG, "getWelcomeMessage: ${Date()}")
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                Log.e(TAG, "getWelcomeMessage: ${Date()}")
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                Log.e(TAG, "getWelcomeMessage: ${Date()}")
                //  Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "Connection problem, Change base url"
                        )
                    )
                )
            } catch (e: JsonConvertException) {
                Log.e(TAG, "getWelcomeMessage: ${Date()}")

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "getWelcomeMessage: ${Date()}")

                //Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun registerUser(url: String): Flow<GetDataFromRemote<User>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                Log.e(TAG, "status code $statusCode ${httpResponse.bodyAsText()}")
                Log.d(TAG, "registerUser: $url")

                when (statusCode) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                //Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    // Get kot cancel privilege
    override suspend fun getKotCancelPrivilege(url: String): Flow<GetDataFromRemote<KotCancelPrivilege>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                //   Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                //  Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                //  Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getCategory(url: String): Flow<GetDataFromRemote<List<Category>>> {
        // Log.i(TAG, "getCategory: ")
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                //   Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    200 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(emptyList())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                //  Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                //  Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getProducts(url: String): Flow<GetDataFromRemote<List<Product>>> {
        //  Log.i(TAG, "getProducts: ")
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                //   Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    200 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(emptyList())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                //  Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //  Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                //  Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun productSearch(url: String): Flow<GetDataFromRemote<List<Product>>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                //   Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    200 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(emptyList())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                //  Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                // Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getMultiSizeProduct(url: String): Flow<GetDataFromRemote<List<MultiSizeProduct>>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                //   Log.i(TAG, "status code $statusCode")

                when (statusCode) {

                    in 200..299 -> {
                        if (statusCode == 204) {
                            emit(
                                GetDataFromRemote.Success(emptyList())
                            )
                        } else {
                            emit(
                                GetDataFromRemote.Success(httpResponse.body())
                            )
                        }
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                //  Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //  Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                //  Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getSectionList(url: String): Flow<GetDataFromRemote<List<Section>>> {
        return flow {
            try {
                Log.d(TAG, "getSectionList: $url")
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                // Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    200 -> {
                        GetDataFromRemote.Success(httpResponse.body<List<Section>>())
                    }

                    in 201..299 -> {
                        emit(
                            GetDataFromRemote.Success(emptyList())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                Log.e(TAG, " ConnectTimeoutException ${e.stackTrace}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {

                Log.e(TAG, " NoTransformationFoundException:-  ")

                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                //  Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }

    }

    override suspend fun getTableList(url: String): Flow<GetDataFromRemote<List<Table>>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    200 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 201..299 -> {
                        emit(
                            GetDataFromRemote.Success(emptyList())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        Log.e(TAG, "getTableList: 400- ${httpResponse.bodyAsText()}")
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        Log.e(TAG, "getTableList: $500 ${httpResponse.bodyAsText()}")
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }


    override suspend fun getTableOrders(url: String): Flow<GetDataFromRemote<List<TableOrder>>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)


                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        if (statusCode == 204) {
                            emit(
                                GetDataFromRemote.Success(emptyList())
                            )
                        } else {
                            emit(
                                GetDataFromRemote.Success(httpResponse.body())
                            )
                        }
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                //  Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //  Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                // Log.e(TAG, " ${e.message}")
                e.printStackTrace()
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }


    override suspend fun generateKOT(
        url: String,
        kot: Kot,
        callBack: suspend (Int, String) -> Unit
    ) {
        Log.d("Test", "generateKOT: $kot")
        Log.e("Test", "generateKOT: $url")
        try {
            val httpResponse = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(body = kot)
            }
            val statusCode = httpResponse.status.value
            val statusMessage = httpResponse.status.description
            callBack(statusCode, statusMessage)
            //  Log.i(TAG, "$kot")
            // Log.d(TAG, "generateKOT: $statusCode $statusMessage")

        } catch (e: Exception) {
            // Log.e(TAG, "generateKOT: ${e.message}")
            callBack(404, e.toString())
        }

    }

    // Put
    override suspend fun editKOTDetails(
        url: String,
        kot: Kot,
        callBack: suspend (Int, String) -> Unit
    ) {
        try {
            val httpResponse = client.put(urlString = url) {
                contentType(ContentType.Application.Json)
                setBody(body = kot)
            }
            val statusCode = httpResponse.status.value
            val statusMessage = httpResponse.status.description
            callBack(statusCode, statusMessage)

        } catch (e: Exception) {
            callBack(404, e.toString() + ", printStrack:-  " + e.printStackTrace())
        }
    }

    override suspend fun editKOTBasics(
        url: String,
        editKOTBasic: EditKOTBasic,
        callBack: suspend (Int, String) -> Unit
    ) {
        try {
            val httpResponse = client.put(urlString = url) {
                contentType(ContentType.Application.Json)
                setBody(body = editKOTBasic)
            }
            val statusCode = httpResponse.status.value
            val statusMessage = httpResponse.status.description
            // val editedOrder = httpResponse.body<TableOrder>()
            //Log.w(TAG, "editKOTBasics: $editKOTBasic $url $editedOrder", )
            callBack(statusCode, statusMessage)
        } catch (e: Exception) {
            callBack(404, e.toString())
        }
    }

    override suspend fun getListOfPendingKOTs(url: String): Flow<GetDataFromRemote<List<UserOrder>>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)


                when (val statusCode = httpResponse.status.value) {
                    in 200..299 -> {
                        if (statusCode == 204) {
                            emit(
                                GetDataFromRemote.Success(emptyList())
                            )
                        } else {
                            emit(
                                GetDataFromRemote.Success(httpResponse.body())
                            )
                        }
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                //  Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //  Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                // Log.e(TAG, " ${e.message}")
                e.printStackTrace()
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }


    override suspend fun getKOTDetails(
        url: String,

        ): Flow<GetDataFromRemote<Kot?>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                // Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    in 200..299 -> {
                        //Log.w(TAG, "getKOTDetails: $statusCode")
                        if (statusCode == 204) {
                            emit(
                                GetDataFromRemote.Success(null)
                            )
                        } else {
                            emit(
                                GetDataFromRemote.Success(httpResponse.body())
                            )
                        }
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //  Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                //Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }


    override suspend fun deleteKOT(
        url: String,
        callBack: suspend (Int, String) -> Unit
    ) {
        try {
            val httpResponse = client.delete(urlString = url)
            val statusCode = httpResponse.status.value
            val statusMessage = httpResponse.status.description
            //Log.d(TAG, "deleteKOT: $statusCode")
            callBack(statusCode, statusMessage)
        } catch (e: Exception) {
            // Log.e(TAG, "deleteKOT: ")
            callBack(404, e.toString())
        }
    }


    override suspend fun uniLicenseActivation(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>> {
        //Log.d(TAG, "request: $licenseRequestBody")
        // Log.w(TAG, "url: $url")
        // Log.e(TAG, "header: $rioLabKey")
        return flow {
            try {
                val httpResponse = client.post(urlString = url) {
                    contentType(ContentType.Application.Json)
                    header("Authorization", rioLabKey)
                    setBody(licenseRequestBody)
                }
                val statusCode = httpResponse.status.value

                when (statusCode) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.body())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        var str = ""
                        try {
                            str = httpResponse.bodyAsText()
                        } catch (e: Exception) {
                            e.message
                        }

                        Log.d(TAG, "uniLicenseActivation: $str")
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description + "-" + str
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                // Log.e(TAG, "${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                //Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getIp4Address(url: String): Flow<GetDataFromRemote<String>> {
        return flow {
            try {
                val httpResponse = client.get(urlString = url)
                val statusCode = httpResponse.status.value
                Log.i(TAG, "status code $statusCode")

                when (statusCode) {
                    in 200..299 -> {
                        emit(
                            GetDataFromRemote.Success(httpResponse.bodyAsText())
                        )
                    }

                    in 300..399 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        Log.d(TAG, "getIp4Address: ${httpResponse.bodyAsText()}")
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }

                    else -> {
                        emit(
                            GetDataFromRemote.Failed(
                                error = Error(
                                    code = statusCode,
                                    message = httpResponse.status.description
                                )
                            )
                        )
                    }
                }

            } catch (e: ConnectTimeoutException) {
                // Log.e(TAG, " ConnectTimeoutException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 600,
                            message = "ConnectTimeoutException Server Down"
                        )
                    )
                )

            } catch (e: NoTransformationFoundException) {
                // Log.e(TAG, " NoTransformationFoundException")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 601,
                            message = "NoTransformationFoundException Server ok. Other problem"
                        )
                    )
                )
            } catch (e: ConnectException) {
                //  Log.e(TAG, " No internet")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 602,
                            message = "No internet in Mobile"
                        )
                    )
                )
            } catch (e: JsonConvertException) {

                // Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 603,
                            message = "Json convert Exception $e"
                        )
                    )
                )
            } catch (e: Exception) {

                //Log.e(TAG, " ${e.message}")
                emit(
                    GetDataFromRemote.Failed(
                        error = Error(
                            code = 604,
                            message = "Other Exception $e"
                        )
                    )
                )
            }
        }
    }


}
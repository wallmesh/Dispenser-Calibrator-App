package com.example.dispensercalibrator.Backend.Ktor

import com.example.dispensercalibrator.Backend.Room.EachCardState
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.FileList
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.cbor.cbor
import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.protobuf.protobuf
import io.ktor.serialization.kotlinx.xml.xml
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.InputStream
import java.io.OutputStream

class KtorMain(){

          @OptIn(ExperimentalSerializationApi::class)
          suspend fun KtorInstance(body: List<EachCardState>, accessToken: String, service:Drive): HttpResponse {
                    val client = HttpClient(OkHttp){
                              install(ContentNegotiation){
                                       // json()
                                        jackson()
                                      //  cbor()
                                      //  xml()
                                      //  protobuf()
                              }
                    }

                    //application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
                    val scope = CoroutineScope(Dispatchers.IO).async {
                              val csvFileID = service.files().list().setQ("name = 'CalibrationDetails.csv' ").execute().files.first().id
                              csvFileID
                    }

                    val response = client.patch("https://www.googleapis.com/upload/drive/v3/files/${scope.await()}"){
                              headers {
                                        append(HttpHeaders.ContentType,  "application/json")
                                        append(HttpHeaders.Accept, "*/*")
                              }
                              bearerAuth(accessToken)
                              setBody(body)
                    }
                    return response
          }
}
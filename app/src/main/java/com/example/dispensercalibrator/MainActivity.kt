package com.example.dispensercalibrator

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.dispensercalibrator.Frontend.UI.Screens.MyHost
import com.example.dispensercalibrator.Frontend.UI.Screens.MyScreensVM
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory

import com.google.api.services.drive.Drive

import com.google.auth.http.HttpCredentialsAdapter

import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
          val vm:MyScreensVM by viewModels()

          @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
          override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    enableEdgeToEdge()
                    setContent {
                              val thisActivity = this
                                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                                  Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(innerPadding)) {

                                                            val controller = rememberNavController()
                                                            MyHost(controller, vm, thisActivity, applicationContext)

                                                  }
                                        }
                    }
          }


          lateinit var startAuthorizationIntent: ActivityResultLauncher<IntentSenderRequest>
          override fun onStart()
          {
                    super.onStart()
                    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                    fun oauthScreen(activity: MainActivity){
                              startAuthorizationIntent =
                                        activity.registerForActivityResult(
                                                  ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                                                  try {               // extract the result
                                                            val authorizationResult = Identity.getAuthorizationClient(activity).getAuthorizationResultFromIntent(activityResult.data)
                                                            // continue with user action
                                                            val token = AccessToken.newBuilder().setTokenValue(authorizationResult.accessToken).build()
                                                            val credentials = GoogleCredentials.create(token)

                                                            val driveInit =
                                                                      Drive.Builder(
                                                                                GoogleNetHttpTransport.newTrustedTransport(),
                                                                                GsonFactory.getDefaultInstance(),
                                                                                HttpCredentialsAdapter(
                                                                                          credentials
                                                                                )
                                                                      ).build()

                                                            println("EXECUTING THE AUTH-SCREEN METHOD")
                                                            val vmInstance: MyScreensVM by viewModels()
                                                            val scope = CoroutineScope(Dispatchers.IO).launch {
                                                                    //  vmInstance.writeToCSV(driveInit, token.toString())
                                                            }
                                                  } catch (e: ApiException) {
                                                  }
                                        }
                    }
                    oauthScreen(this)
          }



          @OptIn(ExperimentalMaterial3Api::class)
          @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
          fun auth(activity: MainActivity){

                    val viewAndManageBigQueryData = Scope("https://www.googleapis.com/auth/bigquery")
                    val seeEditConfigureGcloudData = Scope("https://www.googleapis.com/auth/cloud-platform")

                    val driveScope = Scope("https://www.googleapis.com/auth/drive")
                    val scope2 = Scope("https://www.googleapis.com/auth/drive.file")
                    val scope3  = Scope("https://www.googleapis.com/auth/drive.apps.readonly")
                    val requestedScopes: List<Scope> = listOf(driveScope,scope2,scope3,viewAndManageBigQueryData, seeEditConfigureGcloudData)
                    val authorizationRequest = AuthorizationRequest.builder().setRequestedScopes(requestedScopes).build()

                    Identity.getAuthorizationClient(activity)
                              .authorize(authorizationRequest)
                              .addOnSuccessListener { authorizationResult ->
                                        var tokenString = ""
                                        val rescheck = authorizationResult.accessToken
                                        println(rescheck.toString())
                                        if (authorizationResult.hasResolution()) {
                                                  tokenString = authorizationResult.accessToken.toString()
                                                  println(tokenString.toString())
                                                  val pendingIntent = authorizationResult.pendingIntent
                                                  // Access needs to be granted by the user
                                                  startAuthorizationIntent.launch(IntentSenderRequest.Builder(pendingIntent!!.intentSender).build())
                                        } else {
                                                  // Access was previously granted, continue with user action
                                                  println(tokenString.toString())
                                                  val token = AccessToken.newBuilder().setTokenValue(authorizationResult.accessToken).build()
                                                  val credentials = GoogleCredentials.create( token)

                                                  val driveInit = Drive.Builder(
                                                                      GoogleNetHttpTransport.newTrustedTransport(),
                                                                      GsonFactory.getDefaultInstance(),
                                                                      HttpCredentialsAdapter(
                                                                                credentials
                                                                      )
                                                  ).build()

                                                  println("EXECUTING THE AUTH METHOD")
                                                  val vmInstance: MyScreensVM by viewModels()
                                                  val scope = CoroutineScope(Dispatchers.IO).launch {
                                                         //   vmInstance.writeToCSV(driveInit, rescheck!!)
                                                  }
                                        }
                              }
                              .addOnFailureListener {
                                                  e -> Log.e("authchk", "Failed to authorize", e)
                              }
          }
}

package com.example.dispensercalibrator.Backend.DI

import android.content.Context
import androidx.room.Room
import com.example.dispensercalibrator.Backend.Room.MainDAO
import com.example.dispensercalibrator.Backend.Room.RoomItemsDatabase
import com.example.dispensercalibrator.Frontend.UI.Screens.Stations
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.auth.http.HttpCredentialsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DiModule {

          //  THINGS TO TEST HERE
          //(1) PROVIDE THE APPLICATION CONTEXT and ACTIVITY CONTEXT    (not here but in the class where @Inject is called)
          //(2) PROVIDE A TEST INTERFACE WITH @BINDS

          @Singleton
          @Provides
          fun provideDatabase(@ApplicationContext appContext: Context): RoomItemsDatabase {
                    return Room.databaseBuilder(
                              appContext,
                              RoomItemsDatabase::class.java,
                              "DispenserCalibrator")
                              //  .allowMainThreadQueries()
                              .fallbackToDestructiveMigration(true)
                              .build()
          }

          @Singleton
          @Provides
          fun provideString(): String {
                    return ""
          }

          @Singleton
          @Provides
          fun provideInt(): Int {
                    return 0
          }

          @Singleton
          @Provides
          fun provideVehicleDao(database: RoomItemsDatabase): MainDAO {
                    return database.mainDAO()
          }

          @Singleton
          @Provides
          fun provideContext(@ApplicationContext myContext: Context): Context {
                    return myContext
          }

          /*@Singleton
          @Provides
          fun provideStations(stations: Stations): Stations {
                    return Stations.SWEDRU
          }*/

         /* val driveInit =
                    Drive.Builder(
                              GoogleNetHttpTransport.newTrustedTransport(),
                              GsonFactory.getDefaultInstance(),
                              HttpCredentialsAdapter(
                                        credentials
                              )
                    ).build()*/


}





package com.example.dispensercalibrator.Backend.DI

import android.content.Context
import androidx.room.Room
import com.example.dispensercalibrator.Backend.Room.MainDAO
import com.example.dispensercalibrator.Backend.Room.RoomItemsDatabase
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


          /* fun optionSelector(){
                   stateOfCalibrationDetail = when(Options_obj.currentOption ){
                             MenuOptions.dropDownOption1  -> suppliedMenuOptions.component1()
                             MenuOptions.dropDownOption2 -> suppliedMenuOptions.component2()
                             MenuOptions.dropDownOption3 -> suppliedMenuOptions.component3()
                             else -> "no element"
                   }
         }*/

          /* object Options_obj {
                   var currentOption = MenuOptions.dropDownOption1
         }
         enum class MenuOptions {
                   dropDownOption1,
                   dropDownOption2,
                   dropDownOption3,
                   dropDownOption4
         }*/

          /* DropdownMenuItem(
                                                modifier = Modifier.width(280.dp),
                                                text = { Text(suppliedMenuOptions.component2()) },
                                                onClick = {
                                                          Options_obj.currentOption = MenuOptions.dropDownOption2
                                                          optionSelector()
                                                          expanded = false
                                                          println("THE CURRENT STATION 2 IS: ${Options_obj.currentOption}")
                                                }
                                      )*/

          /*  DropdownMenuItem(
                                                 modifier = Modifier.width(280.dp),
                                                 text = { Text(suppliedMenuOptions.component3()) },
                                                 onClick = {
                                                           Options_obj.currentOption = MenuOptions.dropDownOption3
                                                           optionSelector()
                                                           expanded = false
                                                           println("THE CURRENT STATION 2 IS: ${Options_obj.currentOption}")
                                                 }
                                       )*/


}





package com.example.dispensercalibrator.Backend.Room_2

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MainDAO {

                    //?    INSERT METHODS
                    @Upsert
                    suspend fun Insert_CalibrationDetails(calibrationTestDetails2: CalibrationTest):Long

                    @Query("SELECT * FROM CalibrationTest")
                    suspend fun retrieveCalibrationResult(): EachCardState


}




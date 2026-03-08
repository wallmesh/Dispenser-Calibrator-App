package com.example.dispensercalibrator.Backend.Room_2

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ CalibrationTest::class], version = 8)
//@TypeConverters(Converters::class)
    abstract class RoomItemsDatabase : RoomDatabase() {
        abstract fun mainDAO() : MainDAO
       // abstract fun GoogleSheetsDAO() : GoogleSheetsDAO
    }




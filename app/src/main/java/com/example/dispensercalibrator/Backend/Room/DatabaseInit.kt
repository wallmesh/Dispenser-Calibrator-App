package com.example.dispensercalibrator.Backend.Room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ CalibrationTest::class], version = 3)
//@TypeConverters(Converters::class)
    abstract class RoomItemsDatabase : RoomDatabase() {
        abstract fun mainDAO() : MainDAO
       // abstract fun GoogleSheetsDAO() : GoogleSheetsDAO
    }




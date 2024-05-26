package site.sayaz.ts3client.settings

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

object SettingsDBCallback : RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        // init settings
        super.onCreate(db)
        db.execSQL("""
            INSERT INTO SettingsData (preventSleepDuringConnection, language,appearance,theme) 
            VALUES (0,'def','system','def')
        """)
    }
}
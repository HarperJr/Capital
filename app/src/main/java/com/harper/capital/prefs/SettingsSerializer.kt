package com.harper.capital.prefs

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.harper.capital.SettingsProto
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<SettingsProto> {

    override val defaultValue: SettingsProto = SettingsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingsProto {
        try {
            return SettingsProto.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", ex)
        }
    }

    override suspend fun writeTo(t: SettingsProto, output: OutputStream) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<SettingsProto> by dataStore(
    fileName = "settings.proto",
    serializer = SettingsSerializer
)

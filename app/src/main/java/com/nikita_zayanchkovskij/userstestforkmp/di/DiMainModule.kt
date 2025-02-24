package com.nikita_zayanchkovskij.userstestforkmp.di

import android.app.Application
import androidx.room.Room
import com.nikita_zayanchkovskij.userstestforkmp.common.constants.UsersAppConstants
import com.nikita_zayanchkovskij.userstestforkmp.data.local.UsersDataBase
import com.nikita_zayanchkovskij.userstestforkmp.data.remote.IJsonPlaceHolderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiMainModule {

    @Provides
    @Singleton
    fun provideJsonPlaceHolderApi(): IJsonPlaceHolderApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(UsersAppConstants.JSON_PLACEHOLDER_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolderApi = retrofit.create(IJsonPlaceHolderApi::class.java)
        return jsonPlaceHolderApi
    }

    @Provides
    @Singleton
    fun provideUsersAppRoomDataBase(app: Application): UsersDataBase {
        return Room.databaseBuilder(
            context = app,
            klass = UsersDataBase::class.java,
            name = UsersAppConstants.CACHING_DATABASE_NAME
        ).build()
    }
}
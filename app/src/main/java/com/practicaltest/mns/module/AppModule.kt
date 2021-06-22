package com.practicaltest.mns.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.practicaltest.mns.BuildConfig
import com.practicaltest.mns.api.ApiService
import com.practicaltest.mns.api.Config.baseUrl
import com.practicaltest.mns.api.NetworkConnectionInterceptor
import com.practicaltest.mns.api.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(300, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(300, TimeUnit.SECONDS)
        .addNetworkInterceptor(NetworkConnectionInterceptor())
        .build()

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {

        val levelType: HttpLoggingInterceptor.Level =
            if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val logging = HttpLoggingInterceptor()
        logging.level = levelType

        return logging
    }

    @Provides
    fun provideCharacterService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: ApiService) =
        RemoteDataSource(characterService)

    /*@Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource, localDataSource: OfflineDataDao) =
        CharacterRepository(remoteDataSource, localDataSource)*/

}
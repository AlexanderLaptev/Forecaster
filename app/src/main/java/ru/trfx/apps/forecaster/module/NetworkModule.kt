package ru.trfx.apps.forecaster.module

import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.trfx.apps.forecaster.data.location.LocationRepository
import ru.trfx.apps.forecaster.data.location.OpenMeteoLocationRepository
import ru.trfx.apps.forecaster.data.weather.OpenMeteoWeatherRepository
import ru.trfx.apps.forecaster.data.weather.WeatherRepository
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        Retrofit
            .Builder()
            .baseUrl("http://localhost/")
            .client(
                OkHttpClient
                    .Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }
    single {
        get<Retrofit>().create(OpenMeteoLocationRepository::class.java)
    } bind LocationRepository::class
    single {
        get<Retrofit>().create(OpenMeteoWeatherRepository::class.java)
    } bind WeatherRepository::class
}

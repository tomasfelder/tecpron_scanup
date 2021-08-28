package com.tecpron.tecpronscanning.dagger

import android.app.Application
import com.tecpron.tecpronscanning.TecpronScanningApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                        ActivityBuildersModule::class,
                        ViewModelFactoryModule::class,
                        AppModule::class])
interface AppComponent : AndroidInjector<TecpronScanningApplication>{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application : Application) : Builder

        fun build(): AppComponent
    }
}


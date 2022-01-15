/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.github.igenius.di

import android.app.Application
import android.content.Context
import com.example.github.igenius.githubprojects.ProjectsActivity
import com.example.github.igenius.githubprojects.projectslist.ProjectListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Main component for the application.
 *
 * See the `TestApplicationComponent` used in UI tests.
 */
@Singleton
@Component(modules = [DataModule::class, RoomModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        //fun create(@BindsInstance context: Context): AppComponent
        fun create(@BindsInstance application: Application): AppComponent
    }

    // This tells Dagger that LoginActivity requests injection so the graph needs to
    // satisfy all the dependencies of the fields that LoginActivity is requesting.
    fun inject(fragment: ProjectListFragment)
    fun inject(activity: ProjectsActivity)

}

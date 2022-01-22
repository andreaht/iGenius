package com.example.github.igenius

import com.example.github.igenius.di.AppComponent
import com.example.github.igenius.di.DaggerTestAppComponent

class TestGithubApplication : GithubApplication() {

    override fun initializeComponent(): AppComponent {
        // Creates a new TestAppComponent that injects fakes types
        return DaggerTestAppComponent.factory().create(this)
    }
}
package com.example.github.igenius

import com.example.github.igenius.di.AppComponent

class TestGithubApplication : GithubApplication() {

    override fun initializeComponent(): AppComponent {
        // Creates a new TestAppComponent that injects fakes types
        return DaggerTestAppComponent.create()
    }
}
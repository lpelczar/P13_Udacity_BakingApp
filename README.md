# Udacity Baking App

Project 13: Baking app - Android Baking App that will allow Udacity’s resident baker-in-chief, Miriam, to share her recipes with the world. You will create an app that will allow a user to select a recipe and see video-guided steps for how to complete it.

## Extra features used

- Loading thumbnails from videos using MediaMetadataRetriever
- Heterogeneous RecyclerView

## Used

* [Retrofit](http://square.github.io/retrofit/)
* [GridLayout](https://developer.android.com/reference/android/widget/GridLayout.html)
* [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)
* [Parcelable](https://developer.android.com/reference/android/os/Parcelable.html)
* [Picasso](http://square.github.io/picasso/)
* [GSON](https://github.com/google/gson)
* [JSON](https://en.wikipedia.org/wiki/JSON)
* [Espresso](https://developer.android.com/training/testing/espresso/)
* [HeterogeneousRecycletView](https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView)
* [Fragments](https://developer.android.com/guide/components/fragments)
* [Widget](https://developer.android.com/guide/topics/appwidgets/overview)
* [Exoplayer](https://developer.android.com/guide/topics/media/exoplayer)
* [CardView](https://developer.android.com/guide/topics/ui/layout/cardview)

## Screenshots

<img width="40%" vspace="20" hspace="20" src="http://i67.tinypic.com/dmurz8.png" /> <img width="40%" vspace="20" hspace="20" src="http://i66.tinypic.com/2uffy2u.png" />

<img width="40%" vspace="20" hspace="20" src="http://i64.tinypic.com/1y1hjm.png" /> <img width="40%" vspace="20" hspace="20" src="http://i66.tinypic.com/noh4lt.png" /> 

<img width="80%" hspace="20" vspace="20" src="http://i68.tinypic.com/if3hvk.png" /> 

<img width="80%" hspace="20" vspace="20" src="http://i66.tinypic.com/5l5rb7.png" />

## Project Overview
You will productionize an app, taking it from a functional state to a production-ready state. This will involve finding and handling error cases, adding accessibility features, allowing for localization, adding a widget, and adding a library.

## Why this Project?
As a working Android developer, you often have to create and implement apps where you are responsible for designing and planning the steps you need to take to create a production-ready app. Unlike Popular Movies where we gave you an implementation guide, it will be up to you to figure things out for the Baking App.

## What Will I Learn?
In this project you will:
* Use MediaPlayer/Exoplayer to display videos.
* Handle error cases in Android.
* Add a widget to your app experience.
* Leverage a third-party library in your app.
* Use Fragments to create a responsive design that works on phones and tablets.

## Rubric

### General App Usage
- [x] App should display recipes from provided network resource.
- [x] App should allow navigation between individual recipes and recipe steps.
- [x] App uses RecyclerView and can handle recipe steps that include videos or images.
- [x] App conforms to common standards found in the Android Nanodegree General Project Guidelines.

### Components and Libraries
- [x] Application uses Master Detail Flow to display recipe steps and navigation between them.
- [x] Application uses Exoplayer to display videos.
- [x] Application properly initializes and releases video assets when appropriate.
- [x] Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
- [x] Application makes use of Espresso to test aspects of the UI.
- [x] Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with Content Providers if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

### Homescreen Widget
- [x] Application has a companion homescreen widget.
- [x] Widget displays ingredient list for desired recipe.

# WeatherMVVM
=====================

This app uses the MVVM pattern

These are the APIs that this project includes
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
* [ViewModels](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
* [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Google Map API](https://developers.google.com/maps/documentation/places/android-sdk/autocomplete?hl=es-419#maps_places_autocomplete_country_filter-kotlin)
* [Picasso](http://square.github.io/picasso/)
* [Retrofit](https://square.github.io/retrofit/)
* [Pretty time](https://www.ocpsoft.org/prettytime/)
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
* [Kotlin Flow](https://developer.android.com/kotlin/flow)

### Screenshots
![image](https://user-images.githubusercontent.com/4177453/138528178-846ece6a-30e6-47f1-8032-335cbfc26967.png)

![image](https://user-images.githubusercontent.com/4177453/138528275-b5840465-36a4-4841-a758-2931f5d3e49d.png)

### Implementation

#### Data layer

The database is created using Room and has two entities: `City` and `Forecast`. Room generates the corresponding SQLite table at
runtime.

#### Presentation layer

The app has a Main Activity and two Fragments. 

* The fist fragment displays the a list of cities where the user can add them on the searchBar using the Google API.  
* The second fragment displays a city and the forecast for the next five days.

The Activity works with a ViewModel to do the following:
* subscribe to the emissions of the user name and update the UI every time there is a new City emitted
* notify the ViewModel when the "Update" button is pressed and pass a City with update values.

The ViewModel gets the source from the Repository class

Room guarantees that the observable query will be triggered on a background thread. 

This project shows how to use view bindings in an activity and in fragments. `InflateFragment`
uses the `inflate()` API and `BindFragment` shows the less common `bind()` API.




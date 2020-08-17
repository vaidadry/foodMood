![Logo_small](https://user-images.githubusercontent.com/52376789/90369822-0fda9480-e075-11ea-8c88-20f9de9d4046.png)

**foodMood** is a random meal generator, based on user preferences. 
All you need to do - add fav recipes to virtual recipe book and when needed the app provide a suggestion that you will love. 
Besides, the discover option allows to browse external recipes and save them to the personal folder.

### Features v.1
* add and store recipes (validation, categorization, search)
* browse external recipe DB by title and add to local Recipe Book
* random recipe generator based on user preferances
* linking to URLs and sharing with friends via other apps
* favorite button
* dark mode, RTL, landscape layouts


### Technologies :rocket:
Written in Kotlin, based on latest recommendations by Google.
* MVVM architecture
* Room database - for manually added and cached external data
* Retrofit, Moshi - for external API (http://www.recipepuppy.com/about/api/)
* LiveData - for responsive UI changes
* Coroutines  - for async tasks, responsive app
* DataBinding
* Filterable - for search by title
* Transformations

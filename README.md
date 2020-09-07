![Logo_small](https://user-images.githubusercontent.com/52376789/90369822-0fda9480-e075-11ea-8c88-20f9de9d4046.png)

**foodMood** is a random meal generator, based on user preferences. 
All you need to do - add fav recipes to virtual recipe book and when needed the app provide a suggestion that you will love. 
Besides, the discover option allows to browse external recipes and save them to the personal folder.

### Features v.1
* add and store recipes (validation, categorization, search)
* browse external recipe DB by title and add to local Recipe Book
* random recipe generator based on user preferances
* linking to URLs and sharing with friends via other apps
* favorite button, 
* chip-filtering
* dark mode, RTL, landscape layouts
* paging - for loading data in pieces and search by title
* Dependency injection


<img src="https://user-images.githubusercontent.com/52376789/90375840-2afdd200-e07e-11ea-9c86-69551449938f.jpg" width=150>    <img src="https://user-images.githubusercontent.com/52376789/90377266-72855d80-e080-11ea-9bf7-f717b523731e.jpg" width=150>   <img src="https://user-images.githubusercontent.com/52376789/90377013-086cb880-e080-11ea-8b8e-30fee7c8cddc.jpg" width=150>   <img src="https://user-images.githubusercontent.com/52376789/90377070-1d494c00-e080-11ea-90d0-c8ef2960093d.jpg" width=150>   <img src="https://user-images.githubusercontent.com/52376789/90377551-cbed8c80-e080-11ea-87ed-ab200d7ca506.jpg" width=150>   <img src="https://user-images.githubusercontent.com/52376789/90376575-5af9a500-e07f-11ea-9de6-a5f4db19b16a.jpg" width=150>

### Technologies :rocket:
Written in Kotlin, based on latest recommendations by Google.
* MVVM architecture
* Room database - for manually added and cached external data
* Retrofit - for external API (http://www.recipepuppy.com/about/api/)
* LiveData - for responsive UI changes
* Coroutines  - for async tasks, responsive app
* DataBinding
* Paging library
* Transformations
* Dagger2
 

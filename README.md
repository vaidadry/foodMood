<img src="https://user-images.githubusercontent.com/52376789/106130412-f97b4d80-6169-11eb-9ee6-feeb67ffea26.jpg" width=150>

**foodMood** is a random meal generator, based on user preferences. 
All you need to do - add fav recipes to virtual recipe book and when needed the app provide a suggestion that you will love. 
Besides, the discover option allows to browse external recipes and save them to the personal folder.

### Features v.2
* Base fragment architecture
* Improved cleaner navigation and toolbar+status bar
* Splash screen (right way)

### Features v.1
* add and store recipes (validation, categorization, search)
* browse external recipe DB by title and ingredients and add to local Recipe Book
* random recipe generator based on user preferances
* linking to URLs and sharing with friends via other apps
* favorite button, 
* chip-filtering
* dark mode, RTL, landscape layouts
* paging - for loading data in pieces and search by title
* Dependency injection


<img src="https://user-images.githubusercontent.com/52376789/106130122-abfee080-6169-11eb-9eca-e6da46c0277d.png" width=150>   <img src="https://user-images.githubusercontent.com/52376789/106130117-ab664a00-6169-11eb-9951-1072c82ecfe2.png" width=150>   <img src="https://user-images.githubusercontent.com/52376789/106130114-aacdb380-6169-11eb-9dc9-535d607bf57b.png" width=150>   <img src="https://user-images.githubusercontent.com/52376789/106130111-aa351d00-6169-11eb-8c84-ba95320b51e6.png" width=150>   <img src="https://user-images.githubusercontent.com/52376789/106130104-a73a2c80-6169-11eb-8493-196f7940dd7e.png" width=150>   

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
* Dagger2 // Hilt (different branches)

 

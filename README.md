The **Paging** library helps you load and display chunks of data at a time within your app's RecyclerView.

The library was created for Android backed by Kotlin Coroutines and AndroidX Lifecycle.

## Download
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![](https://jitpack.io/v/vitoksmile/Pager.svg)](https://jitpack.io/#vitoksmile/Pager)

The first step, add JitPack repository to your root build.gradle file (not module build.gradle file):
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
The second step, add the library to your module build.gradle:
```
dependencies {
  implementation 'com.github.vitoksmile:Pager:1.0.0'
}
```

## Usage
Check out the sample app, to see more details.

**1) Create adapter to display items.**

Extend your Adapter from **SimplePagerAdapter** or **AnimatedPagerAdapter** (supports DiffUtils) instead of RecyclerView.Adapter.
```kotlin
class UsersAdapter : SimplePagedAdapter<User, UsersAdapter.ViewHolder>() { ... }
```
```kotlin
class UsersAdapter : AnimatedPagerAdater<User, UsersAdapter.ViewHolder(diffCallback) {

  companion object {

    val diffCallback = object : DiffUtils.ItemCallback<User> {
      override fun areItemsTheSame(oldItem : User, newItem : User) = oldItem.id == newItem.id
      override fun areContentTheSame(oldItem : User, newItem : User) = oldItem == newItem
    }
  }

  ...
}
```

Retrieve the current item:
```kotlin
override onBindViewHolder(holder : ViewHolder, position : Int) {
  val item = getItem(position)
  holder.bind(item)
}

```
**2) Create data source to load items:**
```kotlin
class MyDataSource: DataSource<User>() { ... }
```
Override method **load**. The method will be called when need to load new items by page.

You need to invoke method **onLoaded** in **callback** and pass loaded data and if it's the last page (there are no data more).
```kotlin
override suspend fun load(page : Int, callback : DataSourceCallback<User>) {
  val response = api.getUsers()
  val users = response.data
  val isLastPage = response.currentPage == response.totalPages
  callback.onLoaded(users, isLastPage)
}
```

*To observe loaded data you need to convert DataSource to LiveData and observe the LiveData by LifecycleOwner.*

**3) Create PagerConfig:**
```kotlin
val config = PagerConfig.Builder()
  .setPrefetchDistance(12)
  .build()
```
Pager uses prefetch distance to prefetch new items.

*Example,*

*if distance == 0, Pager loads new page only when the last item will be binded;*

*if distance == 5, Pages loads new page when the last 5-th element from end will be binded.*

**4) Create instance of your DataSource and invoke method toLiveData.**
```kotlin
fun getUsersData(onNetworkReadyData : OnNetworkReadyData) =
  UsersDataSource().toLiveData(config, onNetworkReadyData)
```
Now you can observe the LiveData and receive your loaded items.

Use **OnNetworkReadyData** to receive LiveData with the current NetworkState (loading, success, error).
```kotlin
lateinit var networkData : LiveData<NetworkState>
  private set
val usersData = getUsersData { networkData = it }
```

***5) Observe LiveData and set items to Adapter.***
```kotlin
usersData.observe(viewLifecycleOwner, Observer { adapter.setData(it) })
networkData.observe(viewLifecycleOwner, Observer { state ->
  showLoading(state.isLoading)
}
```

## Requirements

- AndroidX
- Min SDK 14+
- Compile SDK: 29+
- Java 8+

## R8 / Proguard

Coil is fully compatible with R8 out of the box and doesn't require adding any extra rules.

If you use Proguard, you may need to add rules for [Coroutines](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/resources/META-INF/proguard/coroutines.pro).
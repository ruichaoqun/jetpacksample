package com.example.studyjetpack.viewmodel

import androidx.lifecycle.*
import com.example.studyjetpack.data.Word
import com.example.studyjetpack.data.dao.WordRepository
import kotlinx.coroutines.launch

//创建了一个名为 WordViewModel 的类，该类可获取 WordRepository 作为参数并扩展 ViewModel。
// 存储库是 ViewModel 需要的唯一依赖项。如果需要其他类，系统也会在构造函数中传递相应的类。
class WordViewModel(private val repository: WordRepository):ViewModel() {

    //添加了一个公开的 LiveData 成员变量以缓存字词列表。
    //使用存储库中的 allWords Flow 初始化了 LiveData。然后，您通过调用 asLiveData(). 将该 Flow 转换成了 LiveData。
    val addWords:LiveData<List<Word>> = repository.allWords.asLiveData()

    //创建了一个可调用存储库的 insert() 方法的封装容器 insert() 方法。这样一来，便可从界面封装 insert() 的实现。
    // 我们将启动新协程并调用存储库的挂起函数 insert。
    // 如上所述，ViewModel 的协程作用域基于它的名为 viewModelScope 的生命周期（您将在这里使用）。
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

//创建了 ViewModel，并实现了 ViewModelProvider.Factory，后者可获取创建 WordViewModel 所需的依赖项作为参数：WordRepository。
class WordViewModelFactory(private val repository: WordRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
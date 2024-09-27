package `in`.blackant.study_chapter_5.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
fun doAsync(block: CoroutineScope.() -> Unit) =
    GlobalScope.launch(Dispatchers.Default, block = block)

@DelicateCoroutinesApi
fun uiThread(block: CoroutineScope.() -> Unit) = GlobalScope.launch(Dispatchers.Main, block = block)

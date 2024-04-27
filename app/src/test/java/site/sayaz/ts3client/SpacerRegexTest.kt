package site.sayaz.ts3client

import org.junit.Test

class SpacerRegexTest {
    private val regex = Regex("\\[(.*?)spacer(.*?)](.*?)")
    @Test
    fun test1(){
        val matchResult = regex.matchEntire("[spacer1]test")
        assert(matchResult != null)
        val alignment = matchResult?.groups?.get(1)?.value
        val channelName = matchResult?.groups?.get(2)?.value
        val text = matchResult?.groups?.get(3)?.value
        assert(alignment == "")
        assert(channelName == "1")
        assert(text == "test")
    }
    @Test
    fun test2(){
        val matchResult = regex.matchEntire("[*lspacera]-")
        assert(matchResult != null)
        val alignment = matchResult?.groups?.get(1)?.value
        val channelName = matchResult?.groups?.get(2)?.value
        val text = matchResult?.groups?.get(3)?.value
        assert(alignment == "*l")
        assert(channelName == "a")
        assert(text == "-")
    }
    @Test
    fun test3(){
        val matchResult = regex.matchEntire("[*cspacerasdasd]---...")
        assert(matchResult != null)
        val alignment = matchResult?.groups?.get(1)?.value
        val channelName = matchResult?.groups?.get(2)?.value
        val text = matchResult?.groups?.get(3)?.value
        assert(alignment == "*c")
        assert(channelName == "asdasd")
        assert(text == "---...")
    }

}
package tools.website

import com.chimbori.crux.articles.ArticleExtractor
import org.jsoup.Jsoup
import org.openrndr.draw.ColorBuffer
import java.net.URL

fun scrapeWebsite(url:String, maxImages:Int = 4, excludeImages:List<String> = emptyList()) : WebsiteContents {
    val rawHTML = URLFetcher.fetch(URL(url), 4000, 4000)

    val article = ArticleExtractor.with(url, rawHTML)
            .extractMetadata()
            .extractContent()
            .article()

    println(article.title)
    val imageElements = Jsoup.parse(rawHTML).getElementsByTag("img")
    //val imageElements = article.document.getElementsByTag("img")
    val images = mutableListOf<ColorBuffer>()

    // -- download all images
    imgs@for (it in imageElements) {
        val imageUrl = it.attr("src")
        if (imageUrl.endsWith(".png") || imageUrl.endsWith(".jpg") || imageUrl.endsWith(".gif")) {

            for (x in excludeImages) {
                if (imageUrl.contains(x)) {
                    continue@imgs
                }
            }

            var fixedUrl = imageUrl
            if (imageUrl.startsWith("//")) {
                fixedUrl = "https:$imageUrl"
            } else if (imageUrl.startsWith("/")) {
                fixedUrl ="$url$imageUrl"
            }

            println("downloading image from $fixedUrl")
            images.add(ColorBuffer.fromUrl(fixedUrl))
            if (images.size == maxImages) {
                break
            }
        }
    }
    return WebsiteContents(article.title, article.document.text(), images)
}
class WebsiteContents(val title:String, val body:String, val images: List<ColorBuffer>)
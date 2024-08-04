package com.krisna.diva.mynews.core.utils

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View

object StringUtils {
    fun getTruncatedContentWithReadMore(content: String?, url: String?): SpannableString {
        val truncatedContent = content?.substringBefore("…") ?: content
        val readMoreText = "… read more"
        val fullText = "$truncatedContent$readMoreText"

        return SpannableString(fullText).apply {
            truncatedContent?.let {
                setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        widget.context.startActivity(browserIntent)
                    }
                }, it.length, fullText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}
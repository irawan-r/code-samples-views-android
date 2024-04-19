package com.amora.stickylayout.data

open class StickyModel {
    open val header: String? = null
}

class Header(title: String): StickyModel() {
    override val header = title
}

class Content(desc: String, title: String): StickyModel() {
    val description = desc
    override val header = title
}
# Floating Action Button Expandable

[![](https://jitpack.io/v/imtuann/FloatingActionButtonExpandable.svg)](https://jitpack.io/#imtuann/FloatingActionButtonExpandable)

An android library that brings the float action button expandable. You can include optional contents and use everywhere.

Note: The Extended FAB is one of #MaterialDesign’s 🔥🔥🔥 components. You can get the code for implementing it on [Android](https://twitter.com/Android) in the 1.1.0-alpha04 release, out now! http://bit.ly/ex_fab

## Preview

![FloatingActionButtonExpandable][FloatingActionButtonExpandable]

## Usage

### Code

```kotlin
val fab = findViewById<FloatActionButtonExpandable>(R.id.fab)
recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            fab.collapse()
        } else {
            fab.expand()
        }
    }
})
// toggle expand, collapse
fab.toggle()
// expand
fab.expand()
// collapse
fab.collapse()

fab.setExpanded(true)
fab.setDuration(100L)
fab.setContent("Start Chat")
// drawable, resId, bitmap
fab.setIconActionButton(R.drawable.ic_message_white_24dp)
fab.setTextColor(ContextCompat.getColor(this, android.R.color.white))
fab.setBackgroundButtonColor(ContextCompat.getColor(this, R.color.bg_float_action))
// padding between the icon and text
fab.setPaddingTextIcon(resources.getDimensionPixelSize(R.dimen.padding_text_icon))
// padding inside the button
fab.setPaddingInsideButton(resources.getDimensionPixelSize(R.dimen.padding_fab))
// pixel
fab.setTextSize(resources.getDimensionPixelSize(R.dimen.text_size_fab).toFloat())
// or
fab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
// text style
fab.setTypeface(Typeface.BOLD)
// font
val typeface = Typeface.createFromAsset(assets, "fonts/RobotoSlab-Regular.ttf")
fab.setTypeface(typeface)
```

### Layout xml

add `xmlns:app="http://schemas.android.com/apk/res-auto"`

```xml
<com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable
    android:id="@+id/fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:fab_content="@string/label_start_chat"
    app:fab_padding_text_icon="@dimen/padding_text_icon"
    app:fab_text_color="@android:color/white"
    app:fab_bg_color="@color/bg_float_action"
    app:fab_icon="@drawable/ic_message_white_24dp"
    app:fab_duration="100"
    app:fab_text_size="@dimen/text_size_action_button"
    app:fab_typeface="fonts/RobotoSlab-Regular.ttf"
    app:fab_padding="@dimen/padding_fab"
    app:fab_expanded="true"/>
```

## Attributes

|attribute name|description|
|----------|----------|
|fab_content|The content of the button|
|fab_padding_text_icon|The padding between the text and icon|
|fab_text_color|The color of the text|
|fab_bg_color|The background color of the button|
|fab_icon|The icon of the button|
|fab_duration|The length of the expand or collapse animation|
|fab_text_size|The text size of the button|
|fab_typeface|The font path in assets folder|
|fab_padding|The padding inside the button|
|fab_expanded|The button is expanded if you set true|

## Setup

Step 1. Add the JitPack repository in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```
implementation 'com.github.imtuann:FloatingActionButtonExpandable:1.1.2'
```

## Compatibility

Minimum Android SDK: API level 21

## License

```
Copyright (C) 2018 Tuan Nguyen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[FloatingActionButtonExpandable]: /art/FloatingActionButtonExpandable.gif

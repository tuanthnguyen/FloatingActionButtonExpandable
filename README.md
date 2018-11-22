# Float Action Button Expandable

An android library that brings the float action button expandable. You can include optional contents and use everywhere.

## Preview

![FloatActionButtonExpandable][FloatActionButtonExpandable]

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
fab.setBackgroundButtonColor(ContextCompat.getColor(this, R.color.bg_float_action_default))
// padding between the icon and text
fab.setPaddingTextIcon(resources.getDimensionPixelSize(R.dimen.padding_text_icon_default))
// pixel
fab.setTextSize(resources.getDimensionPixelSize(R.dimen.text_size_action_button_default).toFloat())
// or
fab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14)
```

### Layout xml

add `xmlns:app="http://schemas.android.com/apk/res-auto"`

```xml
<com.tuann.floatactionbuttonexpandable.FloatActionButtonExpandable
    android:id="@+id/fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:fab_content_action_button="@string/label_start_chat"
    app:fab_padding_text_icon="@dimen/padding_text_icon_default"
    app:fab_text_color_action_button="@android:color/white"
    app:fab_bg_color="@color/bg_float_action_default"
    app:fab_icon_action_button="@drawable/ic_message_white_24dp"
    app:fab_duration="100"
    app:fab_expanded="true"/>
```

## Attributes

|attribute name|description|
|:-:|:-:|
|fab_content_action_button|The content of the button|
|fab_padding_text_icon|The padding between the text and icon|
|fab_text_color_action_button|The color of the text|
|fab_bg_color|The background color of the button|
|fab_icon_action_button|The icon of the button|
|fab_duration|The length of the expand or collapse animation|
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
implementation 'com.github.imtuann:FloatActionButtonExpandable:1.0.0'
```

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

[FloatActionButtonExpandable]: /art/FloatActionButtonExpandable.gif
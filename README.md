#### [【Kotlin】Kotlin 中使用 ButterKnife ( 仅用于适配 Kotlin 语言 | 不推荐新项目使用 )](https://hanshuliang.blog.csdn.net/article/details/105518881)



<br>
<br>

#### I . 特别注意 : ButterKnife 已停止维护 ( 新项目禁止使用该框架 ) 

---

<br>

**1 . 情况说明 :** <font color=blue>ButterKnife 已经停止维护 , 新项目直接使用 <font color=red>**视图绑定 , 数据绑定** <font color=blue>进行开发 , 本篇博客只是为了适配老版本项目 ; 

<br>


**2 . 当前需求 :** <font color=green>目前的需求是保证之前的 Java 代码能平稳运行 , 基本框架不变 , 在 Kotlin 中使用 ButterKnife 进行视图绑定操作 ; 




<br>
<br>

#### II . Android Studio 中配置 Kotlin 和 ButterKnife 步骤

---

<br>


**1 . Kotlin 配置 :** <font color=blue>不再详细说明 , 创建项目时 , 选择支持 Kotlin 即可 ; 

<br>

**2 . ButterKnife  配置 :** <font color=red>ButterKnife 只需要在 Module 下的 build.gradle 构建脚本中配置 , 

<br>

**① 配置依赖库 :** <font color=green>在 Module 下的 build.gradle 的 dependencies 中进行如下配置 ; 

```java
/** androidx 依赖与老版本的 butterknife 冲突 */
implementation 'com.jakewharton:butterknife:10.0.0'
kapt 'com.jakewharton:butterknife-compiler:10.0.0'
```

**② 应用插件 :** <font color=purple>在 Module 下的 build.gradle 顶部添加如下配置 ; 

```java
apply plugin: 'kotlin-kapt'
```

<br>


**3 . 注解使用 :** 

<br>

**① 组件定义 :** <font color=blue>这里注意 , 只能使用下面的两种方式 , 其它方式会报错 ; 

```kotlin
@BindView(R.id.tv_1)
lateinit var tv_1 : TextView

@JvmField
@BindView(R.id.tv_2)
var tv_2 : TextView? = null
```

**② 视图绑定 :** <font color=blue>使用 `ButterKnife.bind(this)` 绑定定义的组件 , 与 Java 中操作一样 ; 

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    ButterKnife.bind(this)
}
```





<br>
<br>

#### III . Android Studio 中配置 Kotlin 和 ButterKnife 示例 

---

<br>

**1 . 工程下的 build.gradle 构建脚本 :** 

```java
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    ext.kotlin_version = '1.3.71'
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.1'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

<br>

**2 . Module 下的 build.gradle 脚本 :** 

```java
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"

    defaultConfig {
        applicationId "kim.hsl.kb"
        minSdkVersion 24
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    /** androidx 依赖与老版本的 butterknife 冲突 */
    implementation 'com.jakewharton:butterknife:10.0.0'
    kapt 'com.jakewharton:butterknife-compiler:10.0.0'
}
```

<br>


**3 . Kotlin 代码的 Activity 中使用 ButterKnife 注解 :** 注意只能使用下面的两种方式 ; 

```kotlin
package kim.hsl.kb

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : Activity() {

    @BindView(R.id.tv_1)
    lateinit var tv_1 : TextView

    @JvmField
    @BindView(R.id.tv_2)
    var tv_2 : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        Log.i("TAG", tv_1?.text.toString())
        Log.i("TAG", tv_2?.text.toString())
    }
}
```





<br>
<br>

#### IV . Kotlin 注解错误使用 

---

<br>


**1 . 报错内容 :** 

```shell
@BindView fields must not be private or static. (kim.hsl.kb.MainActivity.tv_2)
    private android.widget.TextView tv_2;
```

<br>

**2 . 报错原因 :** <font color=blue>使用了如下声明方式 ,  ~~`@BindView(R.id.tv_2) var tv_2 : TextView? = null`~~  ; 

<br>

**3 . 只能使用下面两种声明方式 :** <font color=blue>① 使用 lateinit 修饰变量 , <font color=red>② 使用 @JvmField 注解 ; 

```kotlin
@BindView(R.id.tv_1)
lateinit var tv_1 : TextView

@JvmField
@BindView(R.id.tv_2)
var tv_2 : TextView? = null
```




<br>
<br>

#### V . 错误处理 导入库冲突 ( 与 androidx 冲突 )

---

<br>


**1 . 报错信息如下 :** 

```shell
The given artifact contains a string literal with a package reference 'android.support.v4.content' 
that cannot be safely rewritten. Libraries using reflection such as annotation processors need to 
be updated manually to add support for androidx.
```

<br>

**2 . 报错原因 :** <font color=blue>引入了 androidx 包依赖 , 与低版本的 butterknife 产生了冲突 , 二者不能同时使用 ; 

```shell
Static interface methods are only supported starting with Android N (--min-api 24): 
void butterknife.Unbinder.lambda$static$0()
```

<br>

**3 . 总结 :** <font color=red>坑有点多 , 新应用能不用 ButterKnife 就不用 , 10.0.0 版本的 butterknife 必须要求 android-24 以上最低兼容版本 , 对于商业项目来说 , 这是不可接受的 ; 

<br>

**4 . 推荐用法 :** <font color=green>老版本应用 ( 没有使用 androidx ) 继续使用老版本的 ButterKnife , 新版本的应用就别用这个框架了 , 使用 JetPack 中的 视图 / 数据 绑定 ; 

<br>

**① 老项目 :** <font color=blue>没有使用 androidx 依赖 , 可以使用低版本的 ButterKnife , 这也是唯一的途径了 ; 

```java
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    testImplementation 'junit:junit:4.12'

    /** androidx 依赖与老版本的 butterknife 冲突 */
    implementation 'com.jakewharton:butterknife:8.8.1'
    kapt 'com.jakewharton:butterknife-compiler:8.8.1'
}
```

<br>

**② 新项目 :** <font color=red>如果使用了 androidx 依赖 , 必须使用高版本的 ButterKnife , 只能兼容 24 以上的最小版本 ; ( 商业项目用了就废了 )

```java
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    /** androidx 依赖与老版本的 butterknife 冲突 */
    implementation 'com.jakewharton:butterknife:10.0.0'
    kapt 'com.jakewharton:butterknife-compiler:10.0.0'
}
```

<br>


><font color=purple>目前使用了 ButterKnife 的应用 , 无法迁移到 JetPack ; 





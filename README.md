# generateLayoutClassPlugin

これは "layout.xml" からインジェクションクラスを自動生成するサンプルプロジェクトです。

プラグインの本体は "app/generateLayoutClass.gradle" のみです。


## 使用例

"app/generateLayoutClass.gradle" を apply するだけです。

##### app/build.gradle:
~~~
apply plugin: 'com.android.application'
apply from: './generateLayoutClass.gradle'   // <-- insert this line

android {
    :
~~~

## 生成コード

"app/build/generated/layout/src/main/java/" 配下に Java クラスが生成されます。

  * パッケージ: アプリケーションID+".layout"
  * クラス名: Layout定義名をキャメル変換してサフィックスに "Views"

##### レイアウト定義：res/layout/activity_main.xml
~~~xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
             xmlns:tools="http://schemas.android.com/tools"
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             tools:context=".MainActivity">
    <ListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</FrameLayout>
~~~

##### 生成コード：.../layout/ActivityMainViews.java
~~~java
package your.app.package.layout;
import your.app.package.R;
import android.view.*;
import android.widget.*;
public class ActivityMainViews {
    public final ListView mListView;
    public ActivityMainViews(android.app.Activity root) {
        this.mListView = (ListView) root.findViewById(R.id.listView);
    }
}
~~~

## 生成クラスの使い方

##### MainActivity.java
~~~java
import your.app.package.layout.ActivityMainViews;
public class MainActivity extends AppCompatActivity {
    private ActivityMainViews mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViews = new ActivityMainViews(this);
        mViews.mListView.setAdapter(new PkgListAdapter());
    }
~~~

さらなる例はコードを参照してください。

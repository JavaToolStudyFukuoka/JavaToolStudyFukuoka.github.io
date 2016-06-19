### VS Code ではじめる Java 開発環境構築 入門

[<img style="border-radius: 50% !important;" src="img/twitter.jpg" width="200px" alt="Keisuke KATO">](https://twitter.com/k_kato) 

#### [Javaツール勉強会＠福岡 2016/06 ～フレームワークとかいろいろ（？）～](http://javatoolstudy-fukuoka.connpass.com/event/33377/)@[AIP カフェ](http://www.npo-aip.or.jp/aipcafe)

2016 年 6 月 17 日[@k_kato](https://twitter.com/k_kato)



## Keisuke Kato @ [CAM](https://www.cam-net.co.jp)

<!-- .slide: data-background="rgb(0, 43, 54)" -->
```js
const profile = {timeline: [
    {year: 2010, lang: "Java", job: "SI" },
    {year: 2011, lang: "C#"  , job: "SI" },
    {year: 2012, lang: "C#"  , job: "SI" },
    {year: 2013, lang: "Java", job: "SI" },
    {year: 2014, lang: "C#"  , job: "SI" },
    {year: 2015, lang: "Java", job: "Web"}
]};
```
<!-- .element: class="fragment" data-fragment-index="1" -->
```js
const addTimeline2016 = (profile) => {
    profile.timeline.push({
        year: 2016,
        lang: "Scala",
        job: "Web",
        do: "『C# モダン Web 開発』執筆中 （進捗 0.1%）",
        try: "Microsoft MVP 2016 年 7 月 1 日 (太平洋標準時)"
    });
}
```
<!-- .element: class="fragment" data-fragment-index="2" -->



# Java IDE
## 使っていますか？



## Java IDE
### [Visual J++](https://msdn.microsoft.com/en-us/library/mt130543.aspx)
<!-- .element: class="fragment" data-fragment-index="1" -->
### [Visual Studio](https://visualstudiogallery.msdn.microsoft.com/bc561769-36ff-4a40-9504-e266e8706f93) 
<!-- .element: class="fragment" data-fragment-index="2" -->
### [Eclipse](https://eclipse.org/)
<!-- .element: class="fragment" data-fragment-index="3" -->
### [NetBeans](http://www.oracle.com/technetwork/developer-tools/netbeans/)
<!-- .element: class="fragment" data-fragment-index="4" -->
### [IntelliJ IDEA](https://www.jetbrains.com/idea/)
<!-- .element: class="fragment" data-fragment-index="5" -->



## IDEって……
1. 遅くないですか？
<!-- .element: class="fragment" data-fragment-index="1" -->
1. その機能は必要ですか？
<!-- .element: class="fragment" data-fragment-index="2" -->
1. 特定言語や OS にロックインされていませんか？
<!-- .element: class="fragment" data-fragment-index="3" -->
1. マウス操作が多くないですか？
<!-- .element: class="fragment" data-fragment-index="4" -->
1. バッテリーは足りてますか？
<!-- .element: class="fragment" data-fragment-index="5" -->
1. 言語のビルド スクリプトを書けますか？
<!-- .element: class="fragment" data-fragment-index="6" -->



## テキスト エディターなら
## 高速！



## テキスト エディター
### [Emacs](https://www.gnu.org/software/emacs/)
<!-- .element: class="fragment" data-fragment-index="1" -->
### [Vim](http://www.vim.org/)
<!-- .element: class="fragment" data-fragment-index="2" -->
### [Atom](https://atom.io/)
<!-- .element: class="fragment" data-fragment-index="3" -->
### [Sublime Text](https://sublimetext.com/)
<!-- .element: class="fragment" data-fragment-index="4" -->
### [Visual Studio Code](https://code.visualstudio.com/)
<!-- .element: class="fragment" data-fragment-index="5" -->



## VS Code
1. ネイティブ感がある（Atom より高速に感じる）
<!-- .element: class="fragment" data-fragment-index="1" -->
1. Java の言語サポートもある!
<!-- .element: class="fragment" data-fragment-index="2" -->
1. Java のデバッガーもある!
<!-- .element: class="fragment" data-fragment-index="3" -->
1. VS Code は エリック・ガンマ（GoF，JUnit，Eclipse）が開発している
<!-- .element: class="fragment" data-fragment-index="4" -->



僕もエリック・ガンマにチケットを捌いてもらった！

![](img/egamma.png)



## 本題
## VS Code Java でできること



## VS Code Java チートシート

機能                   | macOS          | Windows       | サポート
-----------------------|---------------|---------------|------------
IntelliSense (自動補完) | Ctrl+SPACE     | Ctrl+SPACE    | ☑
Lint                   | Cmd+k Cmd+i   | Ctrl+k Ctrl+i | ☑
定義へ移動              | F12           | F12           | ☑
シンボルを開く            | Cmd+T         | Ctrl+T        | ☑
型情報を表示            | Cmd+k Cmd+i   | Ctrl+k Ctrl+i  | ☑



## VS Code Java チートシート

機能                   | macOS          | Windows       | サポート
-----------------------|---------------|---------------|------------
import 自動補完         | -             | -              | □
引数ヒント               | -             | -              | □
リファクタリング           | -             | -              | □
型変換                 | -             | -              | □
findbugs               | -             | -              | □



## 言語サポートの仕組み

![](img/architecture.png)

(Scala ENSIME)



>This extension consists of an external java process, which communicates with vscode using the language server protocol. 

>The java service process uses the implementation of the Java compiler in tools.jar, which is a part of the JDK. When VS Code needs to lint a file, perform autocomplete, or some other task that requires Java code insight, the java service process invokes the Java compiler programatically, then intercepts the data structures the Java compiler uses to represent source trees and types.



## VS Code Java システム要件

役割               | ツール
-------------------|----------------------------------------------------------------------------------------------------------------------|
Java コンパイラ      | [Java SE Development Kit 8u91](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
Java エディター      | [Visual Studio Code Version 1.2.1](https://code.visualstudio.com/)
Java 言語サポート    | [Java Language Support Preview 0.0.15](https://marketplace.visualstudio.com/items?itemName=georgewfraser.vscode-javac)
Java デバッガー     | [Java Debugger 0.0.2](https://marketplace.visualstudio.com/items?itemName=donjayamanne.javadebugger)
Java パッケージ管理  | [Apache Maven 3.3.9](https://maven.apache.org/download.cgi)



### Javascript エコシステム並にシンプル！

* WebStorm
* React
* JSX
* Babel/ES2015
* Browserify/Webpack
* npm 



## VS Code Java 構成例

```bash
├── javaconfig.json /* 【設定ファイル】ビルド情報を記載する */
├── classpath.txt   /* 【設定ファイル】クラス パスを記載する */
├── pom.xml         /* 【設定ファイル】依存関係を記載する */
├── src             /* ソース コード */
│    └── Main.java
├── test            /* テスト コード */
│    └── MainTest.java
└── target          /* クラス ファイル */
    └── classes
         ├── Main.class
         └── MainTest.class /* ※テスト コードも target/classes に出力 */
```


### javaconfig.json

```json
{
    "sourcePath": ["src","test"],
    "classPathFile": "classpath.txt",
    "outputDirectory": "target/classes"
}
```
設定名           | 設定内容
----------------|--------
sourcePath      | ソースコード (.java) のフォルダを指定する
classPathFile   | クラス パス (.jar) が記載されたファイルを指定する
outputDirectory | クラス ファイル (.class) が保存されるフォルダを指定する



### classpath.txt, pom.xml

プロジェクトで使用する Javaライブラリ (.jar) を classpath.txt に手書きするのは現実的ではないため，Maven に自動出力させる

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>2.9</version>
    <executions>
        <execution>
            <id>build-classpath</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>build-classpath</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <outputFile>classpath.txt</outputFile>
    </configuration>
</plugin>
```



### 拡張機能インストール & ビルド

* VS Code Quick Open を開く (Cmd+P)
* 拡張機能をインストールする
```bash
ext install javac
ext install javadebugger
```

* VS Code で Terminal を開く (Ctrl+~)
* Maven ビルドを実行する
```bash
mvn package
```



## デモ



## IntelliSense
![](img/autocomplete.gif)



## Lint
![](img/lint.gif)



## デバッガー
![](img/debug.gif)



## まとめ

* VS Code で Java 開発環境の構築は結構難しい
* Javascript の環境構築に似ているので，慣れていると簡単
* Spring Boot も書けます
* 高速
* 軽量
* バッテリーにやさしい
* Write once, debug everywhere
<!-- .element: class="fragment" data-fragment-index="1" -->



# Thanks!



## VS Code Java
## サンプル プロジェクト

https://github.com/k--kato/vscode-javadebug 



## 参考ノート

1. Don Jayamanne, Java Debugger, https://marketplace.visualstudio.com/items?itemName=donjayamanne.javaDebugger
1. George Fraser, Java Language Support, https://marketplace.visualstudio.com/items?itemName=georgewfraser.vscode-javac
1. Visual Studio Code, Integrate with External Tools via Tasks, https://code.visualstudio.com/Docs/editor/tasks
1. Visual Studio Code, Schema for tasks.json, https://code.visualstudio.com/docs/editor/tasks_appendix

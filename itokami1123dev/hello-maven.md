name: inverse
layout: true
class: center, middle, inverse
---
# こんちはMaven

---
layout: false
.mvn-logo[]

## Apache Maven - ウィキペディア  

> Apache Maven（アパッチ メイヴン／メイヴェン）は、Java用プロジェクト管理ツールである。
> Apache Antに代わるものとして作られた。Apacheライセンスにて配布されているオープンソースソフトウェアである。  
> https://ja.wikipedia.org/wiki/Apache_Maven

### 便利な機能が色々

- プロジェクトのディレクトリ構成をお勧めで作成
- 依存関係を考えて必要なjarをいい感じで取得
- お勧めの決まった順でソースコードやリソースをビルド  
  (コンパイル→テスト→jar)
- さまざまなプラグインで拡張でできる

---
template: inverse

## ざっくり環境構築
---

### ざっくり環境構築

- JDKをインストール
- JAVA_HOME設定
- Apache Mavenをダウンロード＆解凍  
  https://maven.apache.org/download.cgi
- ダウンロードしたMavenのbinにパスを通す
- M2_HOME設定

### mvn --version で確認

```sh
➜  ~  mvn --version
Apache Maven 3.3.3 (7994120775791599e205a5524ec3e0dfe41d4a06; 2015-04-22T20:57:37+09:00)
Maven home: /Users/xxx/Tools/apache-maven-3.3.3
Java version: 1.8.0_66, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home/jre
Default locale: ja_JP, platform encoding: UTF-8
OS name: "mac os x", version: "10.10.5", arch: "x86_64", family: "mac"
```

---

template: inverse

## hello world してみる

---

### hello world してみる

ターミナルで mvn archetype:generate を実行

```sh
* $ mvn archetype:generate \
   -DarchetypeArtifactId=maven-archetype-quickstart \
   -DinteractiveMode=false \
   -DgroupId=com.example.mvn \
   -DartifactId=helloworld
```

### コマンドのパラメータについて

- .green[-D]archetypeArtifactId=maven-archetype-quickstart → シンプル形式を選択  
  https://maven.apache.org/guides/introduction/introduction-to-archetypes.html

- .green[-D]interactiveMode=false → 対話形式パラメーターをfalse選択

- .green[-D]groupId→ 開発している組織やグループ名  
(package名と同じで逆ドメインルール)

- .green[-D]artifactId → 生成するプロジェクト名
(成果物のID)

---

```sh
* $ mvn archetype:generate \
   -DarchetypeArtifactId=maven-archetype-quickstart \
   -DinteractiveMode=false \
   -DgroupId=com.example.mvn \
   -DartifactId=helloworld

[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] >>> maven-archetype-plugin:2.2:generate (default-cli) > generate-sources @ standalone-pom >>>
[INFO]
[INFO] <<< maven-archetype-plugin:2.2:generate (default-cli) < generate-sources @ standalone-pom <<<
[INFO]
[INFO] --- maven-archetype-plugin:2.2:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Batch mode
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Old (1.x) Archetype:  maven-archetype-quickstart:1.0
[INFO] ----------------------------------------------------------------------------
*[INFO] Parameter: basedir, Value: /workdir
*[INFO] Parameter: package, Value: com.example.mvn
*[INFO] Parameter: groupId, Value: com.example.mvn
*[INFO] Parameter: artifactId, Value: helloworld
*[INFO] Parameter: packageName, Value: com.example.mvn
*[INFO] Parameter: version, Value: 1.0-SNAPSHOT
*[INFO] project created from Old (1.x) Archetype in dir: /workdir/helloworld
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 20.544 s
[INFO] Finished at: 2015-11-17T13:15:03+09:00
[INFO] Final Memory: 15M/115M
[INFO] ------------------------------------------------------------------------
```

---
### mvn archetype:generateで生成されるもの

pom.xmlが生成されます。

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
* <groupId>com.example.mvn</groupId>
* <artifactId>helloworld</artifactId>
  <packaging>jar</packaging>
* <version>1.0-SNAPSHOT</version>
  <name>helloworld</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

Mavenは ↓ 下記"3つ" で アプリを一意に識別してます。
- groupId : グループ又は組織名
- artifactId : プロジェクト名
- version : バージョン　　※ ”SNAPSHOT”は「まだよ」という意味

---
### 雛形ディレクトリ構成と雛形Javaコード
```sh
└── helloworld
    ├── pom.xml
    └── src
        ├── main
        │   └── java
        │       └── com
        │           └── example
        │               └── mvn
        │                   └── App.java
        └── test
            └── java
                └── com
                    └── example
                        └── mvn
                            └── AppTest.java
```

```java
package com.example.mvn;

public class App {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
    }
}
```

---
### さっそくビルドして jarを作ります

```sh
$ cd helloworld

$ mvn package

[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building helloworld 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
*[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ helloworld ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /workdir/helloworld/src/main/resources
[INFO]
*[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ helloworld ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 1 source file to /workdir/helloworld/target/classes
[INFO]
*[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ helloworld ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /workdir/helloworld/src/test/resources
[INFO]
*[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ helloworld ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 1 source file to /workdir/helloworld/target/test-classes
[INFO]
*[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ helloworld ---
[INFO] Surefire report directory: /workdir/helloworld/target/surefire-reports

```

---

```sh
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.example.mvn.AppTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.006 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO]
*[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ helloworld ---
[INFO] Building jar: /workdir/helloworld/target/helloworld-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.795 s
[INFO] Finished at: 2015-11-17T13:26:49+09:00
[INFO] Final Memory: 16M/142M
[INFO] ------------------------------------------------------------------------
```

### 実行します

```sh
$ cd /workdir/helloworld

$ java -cp target/helloworld-1.0-SNAPSHOT.jar com.example.mvn.App
Hello World!
```

※ ビルド結果は、targetに入ります。VCS(gitなど)の対象に入れないようにします

---

### mvn に フェーズ(phase) を依頼する

mavenにpackageフェーズまでってお願いすると  
6つのmaven-○○○-pluginのゴールが動きました

```
maven-resources-plugin:resources
↓
maven-compiler-plugin:compile
↓
maven-resources-plugin:testResources
↓
maven-compiler-plugin:testCompile
↓
maven-surefire-plugin:test
↓
maven-jar-plugin:jar
```

Mavenは 標準で３つのLifecycleを持っています。

- default Lifecycle
- clean Lifecycle
- site Lifecycle

ビルドはdefault Lifecycle のフェーズ順に従って動くようです。

---

### [default Lifecycle](https://maven.apache.org/ref/3.3.3/maven-core/lifecycles.html) 23個

```xml
<phases>
  <phase>validate</phase>
  <phase>initialize</phase>
  <phase>generate-sources</phase>
  <phase>process-sources</phase>
  <phase>generate-resources</phase>
  <phase>process-resources</phase>
  <phase>compile</phase>
  <phase>process-classes</phase>
  <phase>generate-test-sources</phase>
  <phase>process-test-sources</phase>
  <phase>generate-test-resources</phase>
  <phase>process-test-resources</phase>
  <phase>test-compile</phase>
  <phase>process-test-classes</phase>
  <phase>test</phase>
  <phase>prepare-package</phase>
* <phase>package</phase>
  <phase>pre-integration-test</phase>
  <phase>integration-test</phase>
  <phase>post-integration-test</phase>
  <phase>verify</phase>
  <phase>install</phase>
  <phase>deploy</phase>
</phases>
```

前の例ではpackage前のvalidateからprepare-packageまで16phase動きました

---

### あれれ.. validateフェーズとか動いてないけど...

前ページはphaseの順番のみで具体的なプラグインのgoal指定がありません...  
成果物のパッケージ形式(pomとかjarとか)で各phaseの処理(プラグインgoal)を決めているようです。

[Plugin bindings for jar packaging](https://maven.apache.org/ref/3.3.3/maven-core/default-bindings.html)

```xml
<phases>
  <process-resources>
    org.apache.maven.plugins:maven-resources-plugin:2.6:resources
  </process-resources>
  <compile>
    org.apache.maven.plugins:maven-compiler-plugin:3.1:compile
  </compile>
  <process-test-resources>
    org.apache.maven.plugins:maven-resources-plugin:2.6:testResources
  </process-test-resources>
  <test-compile>
    org.apache.maven.plugins:maven-compiler-plugin:3.1:testCompile
  </test-compile>
  <test>
    org.apache.maven.plugins:maven-surefire-plugin:2.12.4:test
  </test>
* <package>
*   org.apache.maven.plugins:maven-jar-plugin:2.4:jar
* </package>
  <install>
    org.apache.maven.plugins:maven-install-plugin:2.4:install
  </install>
  <deploy>
    org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy
  </deploy>
</phases>
```

---

### goalを指定すると?

```sh
$ mvn org.apache.maven.plugins:maven-compiler-plugin:3.1:compile
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building helloworld 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-compiler-plugin:3.1:compile (default-cli) @ helloworld ---
[INFO] Nothing to compile - all classes are up to date
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.848 s
[INFO] Finished at: 2015-11-18T23:51:57+09:00
[INFO] Final Memory: 7M/113M
[INFO] ------------------------------------------------------------------------
```

ゴールの場合はその処理のみを実行します。  
(フェーズは、指定された位置までのフェーズを実行します)

---
### ビルド結果(target)を消したい時は mvn clean

```
├── src
│   ├── main
│   │   └── java
│   └── test
│       └── java
└── target
    ├── classes
    └── test-classes

$ mvn clean
[INFO] --- maven-clean-plugin:2.4.1:clean (default-clean) @ helloworld ---
*[INFO] Deleting /workdir/helloworld/target

└── src
    ├── main
    │   └── java
    └── test
        └── java

```
targetディレクトリが消えました  
[clean lifecycle](https://maven.apache.org/ref/3.3.3/maven-core/lifecycles.html#clean_Lifecycle)

---

### ゴール指示だと長いなぁ...短く書けないの？

名前ルールで短くかけます。

<code>
mvn org.apache.maven.plugins:maven-.red[compiler]-plugin:3.1:.red[compile]  
↓  
mvn .red[compiler]:.red[compile]  
</code>

#### 名前ルール

[Specifying a Plugin's Prefix](https://maven.apache.org/guides/introduction/introduction-to-plugin-prefix-mapping.html)

```sh
maven-${prefix}-plugin
${prefix}-maven-plugin
↓
mvn prefix:goal
```

#### グループ組織名の省略

[Settings Reference - Plugin Groups](https://maven.apache.org/settings.html#Plugin_Groups)

> This list automatically contains .red[org.apache.maven.plugins] and org.codehaus.mojo.

---

template: inverse

### 外部ライブラリを使う

---

### 使うライブラリをdependenciesに記述

```xml
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <version>1.4.190</version>
</dependency>
```

### mvnコマンドを実行すると自動でダウンロード

セントラルからローカルリポジトリにダウンロードされます

```
$ mvn package
...
Downloading: http://repo.maven.apache.org/maven2/com/h2database/h2/1.4.190/h2-1.4.190.pom
Downloaded: http://repo.maven.apache.org/maven2/com/h2database/h2/1.4.190/h2-1.4.190.pom (962 B at 0.2 KB/sec)
Downloading: http://repo.maven.apache.org/maven2/com/h2database/h2/1.4.190/h2-1.4.190.jar
Downloaded: http://repo.maven.apache.org/maven2/com/h2database/h2/1.4.190/h2-1.4.190.jar (1669 KB at 980.1 KB/sec)
...
[INFO] --- maven-jar-plugin:2.3.2:jar (default-jar) @ helloworld ---
[INFO] Building jar: /home/vagrant/cam_study/mvn_study/helloworld/target/helloworld-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 8.536s
[INFO] Finished at: Thu Nov 19 11:19:42 JST 2015
[INFO] Final Memory: 8M/160M
[INFO] ------------------------------------------------------------------------
```

---

### ローカルリポジトリを確認してみましょう。

```sh
$ ll ~/.m2/repository/com/h2database/h2/1.4.190/
total 3376
-rw-r--r--  1 hoge  fuga   181B 11 19 20:44 _remote.repositories
-rw-r--r--  1 hoge  fuga   1.6M 11 19 20:44 h2-1.4.190.jar
-rw-r--r--  1 hoge  fuga    40B 11 19 20:44 h2-1.4.190.jar.sha1
-rw-r--r--  1 hoge  fuga   962B 11 19 20:43 h2-1.4.190.pom
-rw-r--r--  1 hoge  fuga    40B 11 19 20:43 h2-1.4.190.pom.sha1
```


<i class="fa fa-cloud"></i> Maven Central

<i class="fa fa-arrow-down"></i>

<i class="fa fa-download"></i> Local PC (~/.m2/repository) <i class="fa fa-child"></i>


---

### App.javaを修正して

```java
package com.example.mvn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main( String[] args ) throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select 'hello world h2' from dual");
        rs.first();
        System.out.println(rs.getString(1));
    }
}
```

ビルドして実行

```
$ mvn package

$ export CLASSPATH=target/helloworld-1.0-SNAPSHOT.jar:~/.m2/repository/com/h2database/h2/1.4.190/h2-1.4.190.jar

$ java com.example.mvn.App

hello world h2
```

---
template: inverse

## おしまい

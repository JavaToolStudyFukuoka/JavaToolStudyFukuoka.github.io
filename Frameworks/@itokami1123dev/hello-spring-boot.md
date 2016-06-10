# Spring Boot
### Controller関連のLT
Javaツール勉強会＠福岡 2016/06

_未発表　参加できずごめんなさい。。。_

____
## 自己紹介

<img class="logo" src="img/twitter_itokami_logo.png" style="border-radius:2em;height:2em;"/>
<br />
- twitter: @itoKami1123

- 福岡の中堅企業向け統合基幹業務システムをSaaSで提供している会社で働いてます。
子供たちが大きくなった時に福岡で仕事が困らない様に
福岡ITを盛り上げたいと思っています。

____
## Spring boot って

いっぱいある Spring Framework を
おすすめの設定と組み合わせで
サクッと合わせて使えます

2016/06/14 version:1.3.5

_- - -_
### とっても参考になる資料

- [はじめてのSpring Boot―「Spring Framework」で簡単Javaアプリ開発 (I・O BOOKS)](https://www.amazon.co.jp/%E3%81%AF%E3%81%98%E3%82%81%E3%81%A6%E3%81%AESpring-Boot%E2%80%95%E3%80%8CSpring-Framework%E3%80%8D%E3%81%A7%E7%B0%A1%E5%8D%98Java%E3%82%A2%E3%83%97%E3%83%AA%E9%96%8B%E7%99%BA-I%E3%83%BB-BOOKS/dp/4777518655)

- [Grails 3.0先取り!? Spring Boot入門ハンズオン](http://www.slideshare.net/makingx/grails-30-spring-boot)
____
# Quick Start

_- - -_
### 空のMaven Projectを用意

```
$ mvn archetype:generate \
 -DarchetypeArtifactId=maven-archetype-quickstart \
 -DinteractiveMode=false \
 -DgroupId=com.example.mvn \
 -DartifactId=helloworld
```

_- - -_
### Mavenの設定(pom.xml)にSpringを追加

http://projects.spring.io/spring-boot/#quick-start

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.3.5.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

_- - -_
### App.java にSpring Boot追加

```
@Controller                // --> このクラスはコントローラでっせ
@EnableAutoConfiguration   // --> 設定をよしなにやってね
public class App
{
    @RequestMapping("/")   // --> コントローラのURLは "/" だよー
    @ResponseBody          // --> 戻り値で直接レスポンスのコンテンツを返すyo
    String home() {
        return "Hello World!"; // --> ブラウザに返却する値
    }
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args); // --> こっから起動するぞー  
    }
}
```

_- - -_
### Spring Bootを起動

```
$ mvn spring-boot:run
```

http://localhost:8080/


_- - -_
### ついでに画面表示

JSPでなくて Thymeleaf を使う

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

> Thymeleaf  
> http://www.thymeleaf.org/index.html

_- - -_
### 配置

```
src
│─ main
│  └── java
│      └── ...
└─ resources
   └── templates
       └── index.html
```

```
@Controller
public class BaseController {
    @RequestMapping("/view")
    String view() { return "index"; }
}
```

____
## うちのController使いかた

_- - -_
### \* アスタリスクの使用

```
@RequestMapping("/*Compute")
```
同じ結果

- [http://localhost:8080/hogeCompute](http://localhost:8080/hogeCompute)
- [http://localhost:8080/fugaCompute](http://localhost:8080/fugaCompute)

_- - -_
### 継承の使用

```
@Controller
public class BaseController {
    @RequestMapping("**/menu")
    @ResponseBody
    List<String> menu() {
        return this.getMenu();
    }
    protected List<String> getMenu(){
        return new ArrayList<String>();
    }
}
```

```
@Controller
@RequestMapping("/age")
public class AgeController extends BaseController {
    @Override
    protected List<String> getMenu(){
        return Arrays.asList("karaAge","atuAge");
    }
}
```

_- - -_

起動ログに登録URLが出力

```
s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/age/**/menu]}" onto java.util.List<java.lang.String> com.example.mvn.controller.BaseController.menu()
s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/**/menu]}" onto java.util.List<java.lang.String> com.example.mvn.controller.BaseController.menu()
```

- [http://localhost:8080/hoge/menu](http://localhost:8080/hoge/menu) -> []
- [http://localhost:8080/age/menu](http://localhost:8080/age/menu)   -> ["karaAge","atuAge"]　

_- - -_

### URLのルールまとめて設定

```
@Controller
public class BaseController {
    @RequestMapping("**/api/__{proc}__/{action}")
    @ResponseBody
    List<String> proc(
            @PathVariable(value = "proc") String proc,
            @PathVariable(value = "action") String action

    ) {
        return this.getProc(proc, action);
    }

    protected List<String> getProc(String proc, String action) {
        return new ArrayList<String>();
    }

}
```

_- - -_
起動ログ

```
"{[/sage/**/api/__{proc}__/{action}]}" onto java.util.List<java.lang.String> com.example.mvn.controller.BaseController.proc(java.lang.String,java.lang.String)
"{[/age/**/api/__{proc}__/{action}]}" onto java.util.List<java.lang.String> com.example.mvn.controller.BaseController.proc(java.lang.String,java.lang.String)
```
- [http://localhost:8080/sage/api/__orderProc__/init](http://localhost:8080/sage/api/__orderProc__/init  )
- [http://localhost:8080/age/api/__orderProc__/init](http://localhost:8080/age/api/__orderProc__/init  )

____
## おしまい

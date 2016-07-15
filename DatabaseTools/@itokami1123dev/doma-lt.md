# Doma LT
### Java のDBアクセスフレームワークだよLT
Javaツール勉強会＠福岡 2016/08  
データベースツールとか（？）編

____
## 自己紹介

<img class="logo" src="img/twitter_itokami_logo.png" style="border-radius:2em;height:2em;"/>
<br />
- twitter: @itoKami1123

- 福岡の中堅企業向け統合基幹業務システムをSaaSで提供している会社で働いてます。
子供たちが大きくなった時に福岡で仕事が困らない様に
福岡ITを盛り上げたいと思っています。

____
## Doma って

Java のDBアクセスフレームワーク
http://doma.readthedocs.io/ja/stable/

- コンパイル時 にコードの生成やコードの検証  
  Pluggable Annotation Processing API

- Java オブジェクトにマッピング

- 2-way SQL な SQLテンプレート  
  SQLのテンプレートがそのままSQL実行できる

- Java 8 の java.time.LocalDate や java.util.Optional や java.util.stream.Stream を利用できる

- JRE 以外のライブラリ依存なし

_- - -_
### 資料

- Domaの紹介
  http://backpaper0.github.io/ghosts/doma-intro.html#1

- Domaの開発で大切にしている10のこと
  http://qiita.com/nakamura-to/items/099cf72f5465d0323521

- ドメインクラスの話
  http://backpaper0.github.io/ghosts/doma-domainclass.html#1

- とあるDoma2の使い方
  http://gakuzzzz.github.io/slides/doma_practice/#1

____
# Maven で Doma2

_- - -_
### 空のMaven Projectを用意

```
$ mvn archetype:generate \
 -DarchetypeGroupId=pl.org.miki \
 -DarchetypeArtifactId=java8-quickstart-archetype \
 -DinteractiveMode=false \
 -DgroupId=com.example.domalt \
 -DartifactId=helloworld
```

_- - -_
### Mavenの設定(pom.xml)にDoma追加

```
<!-- https://mvnrepository.com/artifact/org.seasar.doma/doma -->
<dependency>
    <groupId>org.seasar.doma</groupId>
    <artifactId>doma</artifactId>
    <version>2.12.1</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.h2database/h2 -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.192</version>
</dependency>
```

DBとしてh2databaseもついでに追加してます

_- - -_
### まずは、 App.java でh2databaseの動作確認

```
public class App {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select 'hello world h2 ooo' from dual");
        rs.first();
        System.out.println(rs.getString(1));
    }
}
```

_- - -_
### 起動

```
$ mvn package
$ export CLASSPATH=target/helloworld-1.0-SNAPSHOT.jar:~/.m2/repository/com/h2database/h2/1.4.192/h2-1.4.192.jar
$ java com.example.domalt.App
hello world h2 ooo
```


_- - -_

DOMA2でDB接続設定

```
@SingletonConfig // ★しんぐるとん
public class DomaConfig implements Config{
    private static final DomaConfig CONFIG = new DomaConfig();
    private final Dialect dialect;
    private final LocalTransactionDataSource dataSource;
    private final TransactionManager transactionManager;

    // シングルトンなのでprivateなコンストラクタ
    private DomaConfig(){
        dialect = new H2Dialect(); // ★ H2DataBaseの方言を設定
        // ローカルトランザクションを設定するデータソース
        dataSource = new LocalTransactionDataSource(
                "jdbc:h2:~/test", null, null);
        // ローカルトランザクションを管理する
        transactionManager = new LocalTransactionManager(
                dataSource.getLocalTransaction(getJdbcLogger()));
    }

    @Override
    public Dialect getDialect(){
        return dialect;
    }

    @Override
    public DataSource getDataSource(){
        return dataSource;
    }

    @Override
    public TransactionManager getTransactionManager(){
        return transactionManager;
    }

    public static DomaConfig singleton(){
        return CONFIG;
    }
}
```

_- - -_

テストデータ

```sql
CREATE SEQUENCE customer_seq START WITH 100 INCREMENT BY 1;

CREATE TABLE customer (
    id      INTEGER NOT  NULL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    version INTEGER      NOT NULL
);

INSERT INTO customer VALUES (1, 'KAMO',  0);
INSERT INTO customer VALUES (2, 'NEGI',  0);
```


_- - -_

# DOMA2でselect

_- - -_

### Entity つくって

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "CUSTOMER_SEQ")
    public Integer id;

    public String name;

    @Version
    public Integer version;

    @Override
    public String toString(){

        return String.format(
                "Customer [id=%s, version=%s, name=%s]",
                id,
                version,
                name
        );
    }
}
```
_- - -_
### SQLファイル作って

```sql
-- META-INF/com/example/domalt/dao/CustomerDao/selectAll.sql
SELECT
       /*%expand*/*
  FROM customer

```

%expand は エンティティクラス の定義で自動でカラム展開

_- - -_
### Dao作って


```java

@Dao(config= DomaConfig.class)
public interface CustomerDao {

    @Select
    List<Customer> selectAll();
}
```
_- - -_
### 実行

```java
public class App {
    public static void main(String[] args) {
        domaH2Sample();
    }

    private static void domaH2Sample() {
        TransactionManager tm = DomaConfig
                .singleton()
                .getTransactionManager();

        CustomerDao customerDao = new CustomerDaoImpl();
        tm.required(() -> {
            List<Customer> customers =
                    customerDao.selectAll();

            customers.
                    stream().
                    forEach(System.out::println);
        });
    }
}
```

_- - -_
```sh
$ mvn package
$ export CLASSPATH=target/helloworld-1.0-SNAPSHOT.jar:\
~/.m2/repository/com/h2database/h2/1.4.192/h2-1.4.192.jar:\
~/.m2/repository/org/seasar/doma/doma/2.12.1/doma-2.12.1.jar
$ java com.example.domalt.App
..
Customer [id=1, version=0, name=KAMO]
Customer [id=2, version=0, name=NEGI]
..
```

_- - -_
### 検索条件
```sql
SELECT /*%expand*/
  FROM customer
 WHERE id = /* id */0
```

```java
public interface EmployeeDao {
    @Select
    Employee selectById(Integer id);
}
```

```java
private static void domaH2Sample() {
    TransactionManager tm = DomaConfig
            .singleton()
            .getTransactionManager();
    tm.required(() -> {
                customerDao
                        .selectById(1)
                        .ifPresent(System.out::println);

    });
}
```

```
Customer [id=1, version=0, name=KAMO]
```

_- - -_
### 挿入
```java
public interface CustomerDao {
    @Insert
    int insert(Customer customer);
}
```

```java
private static void domaH2Sample() {
    TransactionManager tm = DomaConfig
            .singleton()
            .getTransactionManager();

    tm.required(() -> {
        Customer customer = new Customer();
        customer.name = "おれおれ";
        int resultCnt = customerDao.insert(customer);

        System.out.format("登録件数=%d \n", resultCnt);

        customerDao
                .selectAll()
                .stream()
                .forEach(System.out::println);
    });
}
```

_- - -_
```
登録件数=1
Customer [id=1, version=0, name=KAMO]
Customer [id=2, version=0, name=NEGI]
Customer [id=100, version=1, name=おれおれ]
```

_- - -_
## 更新

```java
public interface CustomerDao {

    @Update
    int update(Employee employee);

}
```

```java
private static void domaH2Sample() {

    TransactionManager tm = DomaConfig
            .singleton()
            .getTransactionManager();

    tm.required(() -> {
        customerDao
                .selectById(1)
                .ifPresent((customer) -> {
                    customer.name = "hoge";
                    int resultCnt = customerDao.update(customer);
                    System.out.format("UPDATE cnt=%d \n", resultCnt);
                });

        customerDao
                .selectById(1)
                .ifPresent(System.out::println);
    });
}
```

_- - -_

```
UPDATE cnt=1
Customer [id=1, version=1, name=hoge]
```

http://doma.readthedocs.io/ja/stable/query/update/

> SQL自動生成におけるバージョン番号と楽観的排他制御
> 次の条件を満たす場合に、楽観的排他制御が行われます。

- パラメータのエンティティクラスに@Versionが注釈されたプロパティがある
- @UpdateのignoreVersion要素がfalseである


____

## おまけ
pom.xmlにSpring BootでDomaを使うため

```xml
<dependency>
    <groupId>org.seasar.doma.boot</groupId>
    <artifactId>doma-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```
_- - -_

Springで使うとDomaの返却するDataSourceはSpringの管理外...
実行時例外発生時にロールバックされない..
TransactionAwareDataSourceProxyでらっぷ
```java
@Override
public DataSource getDataSource() {
  return new TransactionAwareDataSourceProxy(dataSource());
}
```
____
## おしまい

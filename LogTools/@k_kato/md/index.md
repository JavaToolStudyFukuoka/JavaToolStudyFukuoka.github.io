## ログ パーティショニング
### PostgreSQL x Java

[<img style="border-radius: 50% !important;" src="img/twitter.jpg" width="200px" alt="Keisuke KATO">](https://twitter.com/k_kato) 

[Javaツール勉強会 ～ログライブラリとかいろいろ（？）～](http://javatoolstudy-fukuoka.connpass.com/event/28967/)

2016年 4月 22日 [@k_kato](https://twitter.com/k_kato)




## 自己紹介

<!-- .slide: data-background="rgb(0, 43, 54)" -->
```js
const profile = {timeline: [
    {year: 2010, lang: "Java"},
    {year: 2011, lang: "C#"  },
    {year: 2012, lang: "C#"  },
    {year: 2013, lang: "Java"},
    {year: 2014, lang: "C#"  },
    {year: 2015, lang: "Java"}
]};
```
```js
const addTimeline2016 = (profile) => {
    profile.timeline.push({
        year: 2016,
        lang: "🍣",
        do: "『C# モダン Web 開発』を執筆中",
        try: "Microsoft MVP"
    });
}
```
<!-- .element: class="fragment" data-fragment-index="1" -->



# Q.
# ログ <!-- .element: class="fragment" data-fragment-index="1" -->
## 保存していますか？ <!-- .element: class="fragment" data-fragment-index="1" -->



# A.
1. 保存していない <!-- .element: class="fragment" data-fragment-index="1" -->
1. アラート <!-- .element: class="fragment" data-fragment-index="2" -->
1. ファイル保存 <!-- .element: class="fragment" data-fragment-index="3" -->
1. AWS, GCP, Azure <!-- .element: class="fragment" data-fragment-index="4" -->
1. Elasticsearch <!-- .element: class="fragment" data-fragment-index="5" -->
1. Hadoop <!-- .element: class="fragment" data-fragment-index="5" -->
1. Treasure Data <!-- .element: class="fragment" data-fragment-index="5" -->
1. DB保存 <!-- .element: class="fragment" data-fragment-index="6" -->
 1. NoSQL <!-- .element: class="fragment" data-fragment-index="7" -->
 1. RDB <!-- .element: class="fragment" data-fragment-index="8" -->



<!-- .slide: data-background="rgb(255, 255, 255)" -->
![fluentd architecture](img/fluentd-architecture.png)
<span style="height: 50px; width: 220px; background: transparent; border: 6px dotted green; border-radius: 50px; position:absolute; top:310px; left:30px"/><!-- .element: class="fragment" data-fragment-index="1" -->
<span style="height: 50px; width: 220px; background: transparent; border: 6px dotted yellow; border-radius: 50px; position:absolute; top:70px; left:330px"/><!-- .element: class="fragment" data-fragment-index="2" -->
<span style="height: 50px; width: 220px; background: transparent; border: 6px dotted red; border-radius: 50px; position:absolute; top:150px; left:-350px"/><!-- .element: class="fragment" data-fragment-index="3" -->



## RDB
## PostgreSQL
## 大量ログを投入して
## 問い合わせてみた
## ※ Java で<!-- .element: class="fragment" data-fragment-index="1" -->



## シナリオ

ユーザーがアカウントを作成したときの OS 情報を 過去 3 年分 集計して，多い順に画面に表示する



## ユーザー アカウント作成ログ

Table                        | Tuples Inserted | Table Size | Year
-----------------------------|-----------------|------------|----------
bigdata\_app\_log_<br>no_index | 12,171,665      | 4,231 MB   | 2004-2016




## 3 年分だけ取得してみる

```sql
SELECT *
FROM bigdata_app_log_no_index
WHERE reg_dt BETWEEN '2013-04-01' AND '2016-03-31';
```



## Execution time: 11,546 ms

<img style="border-radius: 3% !important;" src="img/000_noIndex_noPartition.png" />

```bash
QUERY PLAN
Seq Scan on bigdata_app_log_no_index  (cost=0.00..724122.97 rows=3776627 width=4993) (actual time=2.655..11500.295 rows=3605926 loops=1)
  Filter: ((reg_dt >= '2013-04-01'::date) AND (reg_dt <= '2016-03-31'::date))
  Rows Removed by Filter: 8565739
Planning time: 0.356 ms
Execution time: 11546.416 ms
```

### Seq Scan = Full Scan <!-- .element: class="fragment" data-fragment-index="1" -->
### インデックスが足りない <!-- .element: class="fragment" data-fragment-index="2" -->



## インデックス追加

```sql
CREATE INDEX "IX1_bigdata_log"
   ON public.bigdata_log (reg_dt ASC NULLS LAST);
```



## Execution time: 6,269 ms

<img style="border-radius: 3% !important;" src="img/005_Index_noPartition.png" />

```bash
QUERY PLAN
Bitmap Heap Scan on bigdata_app_log_index  (cost=76689.39..672452.52 rows=3614142 width=4993) (actual time=263.783..6217.492 rows=3605926 loops=1)
  Recheck Cond: ((reg_dt >= '2013-04-01'::date) AND (reg_dt <= '2016-03-31'::date))
  Rows Removed by Index Recheck: 820
  Heap Blocks: exact=29866 lossy=131743
  ->  Bitmap Index Scan on IX1_bigdata_app_log_index  (cost=0.00..75785.85 rows=3614142 width=0) (actual time=259.159..259.159 rows=3605926 loops=1)
        Index Cond: ((reg_dt >= '2013-04-01'::date) AND (reg_dt <= '2016-03-31'::date))
Planning time: 0.156 ms
Execution time: 6269.275 ms
```



### インデックス追加 1.8 倍高速化

Table                          | Tuples Inserted | Table Size | Year                    | Execution time
-------------------------------|-----------------|------------|-------------------------|---------------
bigdata\_app\_log_<br>no_index | 12,171,665      | 4,231 MB   | 2013-2016/<br>2004-2016 | 11,546.416 ms
bigdata\_app\_log_<br>index    | 12,171,665      | 4,231 MB   | 2013-2016/<br>2004-2016 | <span style="color: yellow">**6,269.275 ms**</span>



## 画面表示に 6 秒も待てない
## もっといい方法があるはずだ<!-- .element: class="fragment" data-fragment-index="1" -->



## データの分布を調査

Table            | Tuples Inserted | Table Size | Year
-----------------|-----------------|------------|------------------------
bigdata_app_log  | 42              | 0.00 MB    | 2004
bigdata_app_log  | 1,176,599       | 405 MB     | 2005
bigdata_app_log  | 1,124,206       | 387 MB     | 2006
bigdata_app_log  | 1,063,139       | 367 MB     | 2007
bigdata_app_log  | 972,165         | 337 MB     | 2008
bigdata_app_log  | 939,173         | 326 MB     | 2009
bigdata_app_log  | 1,036,272       | 360 MB     | 2010
bigdata_app_log  | 1,081,212       | 376 MB     | 2011


Table            | Tuples Inserted | Table Size | Year
-----------------|-----------------|------------|------------------------
bigdata_app_log  | 1,172,931       | 409 MB     | 2012
bigdata_app_log  | 1,199,508       | 419 MB     | 2013
bigdata_app_log  | 1,207,115       | 423 MB     | 2014
bigdata_app_log  | 1,199,303       | 420 MB     | 2015

1 年単位でテーブルを分割（パーティショニング）し，散乱したアクセスを減らす <!-- .element: class="fragment" data-fragment-index="1" -->



## パーティショニング

```sql
SELECT *
FROM bigdata_app_log
WHERE reg_dt BETWEEN '2013-04-01' AND '2016-03-31';
```

<img style="border-radius: 3% !important;" src="img/010_Index_Partition.png" /><!-- .element: class="fragment" data-fragment-index="1" -->



### PostgreSQL パーティショニング 概要

1. マスター テーブル（親）を作成する <!-- .element: class="fragment" data-fragment-index="1" -->
1. 親を継承したパーティション テーブル（子）を作成する（テーブル制約） <!-- .element: class="fragment" data-fragment-index="2" -->
1. 子に保持するデータの範囲を設定する <!-- .element: class="fragment" data-fragment-index="3" -->
1. 親への Insert をトリガーに，適切な子を判定する Function を作成する <!-- .element: class="fragment" data-fragment-index="4" -->
1. 親への Insert で Function を実行するトリガーを設定する <!-- .element: class="fragment" data-fragment-index="5" -->



## INHERITS & CHECK 

* 親を継承 Inherits して子を作成する
* 子にテーブル制約 Check を追加する

```sql
CREATE TABLE bigdata_app_log_2015 (
    CHECK (reg_dt BETWEEN DATE '2015-04-01' AND DATE '2016-03-31'),
    CONSTRAINT pk_bigdata_app_log_2015 PRIMARY KEY (rec_id, line_no)
    USING INDEX TABLESPACE pg_default
) INHERITS(bigdata_app_log)
```



## Function

* 親への Insert（NEW） データからキー値を取得する
* キー値から Insert する子を決定する
* 子が存在しなければ，子を自動生成する

```sql
CREATE OR REPLACE FUNCTION trg_bigdata_app_log()
  RETURNS TRIGGER
AS
$$
DECLARE
  partition_key      DATE := CASE WHEN NEW.reg_dt IS NOT NULL THEN NEW.reg_dt ELSE NOW() END;
  this_year           INT := to_char(partition_key, 'yyyy');
  this_month          INT := to_char(partition_key, 'mm');
  partition_year      INT := CASE WHEN this_month BETWEEN 1 AND 3 THEN this_year-1 ELSE this_year END;

  master_table    VARCHAR := 'bigdata_app_log';
  partition_table VARCHAR := master_table || '_' || partition_year;
  check_min       VARCHAR := partition_year   || '-04-01';
  check_max       VARCHAR := partition_year+1 || '-03-31';

  create_table    VARCHAR := FORMAT(
      'CREATE TABLE %s (
           CHECK (reg_dt BETWEEN DATE %L AND DATE %L),
           CONSTRAINT pk_%s PRIMARY KEY (rec_id, line_no) USING INDEX TABLESPACE pg_default
       ) INHERITS(%s)
       TABLESPACE pg_default',
      partition_table,
      check_min,
      check_max,
      partition_table,
      master_table);

  create_index    VARCHAR := FORMAT(
      'CREATE INDEX ix1_%s 
           ON %s
           (reg_dt)
           TABLESPACE pg_default;
      ',
      partition_table,
      partition_table);

   insert_table    VARCHAR := FORMAT(
       'INSERT INTO %s VALUES ($1.*)',
       partition_table);
BEGIN

  IF TG_OP = 'INSERT' THEN

    PERFORM (partition_table)::regclass;
    EXECUTE insert_table USING NEW;

  END IF;

  RETURN NULL;

  EXCEPTION WHEN undefined_table THEN
    EXECUTE create_table;
    EXECUTE create_index;
    EXECUTE insert_table USING NEW;
    RETURN NULL;
END;
$$
LANGUAGE plpgsql
;
```



## Trigger

Insert で Function を実行するトリガーをマスター テーブル（親）に設定する

```sql
DROP TRIGGER IF EXISTS trg_bigdata_app_log_ts ON bigdata_app_log;
CREATE TRIGGER trg_bigdata_app_log_ts
BEFORE INSERT OR UPDATE
ON bigdata_app_log
FOR EACH ROW
EXECUTE PROCEDURE trg_bigdata_app_log();
```



## データ移行

* 親に親自身を Insert する
* 親を Truncate する (ONLY を忘れると子も全消去される)

```sql
INSERT INTO bigdata_app_log SELECT * FROM bigdata_app_log;

TRUNCATE TABLE ONLY bigdata_app_log;
```



## パーティション テーブル

Table                 | Tuples Inserted | Table Size 
----------------------|-----------------|------------
bigdata_app_log       | 0               | 0.00 MB
bigdata_app_log_2004  | 42              | 0.00 MB
bigdata_app_log_2005  | 1,176,599       | 405 MB
bigdata_app_log_2006  | 1,124,206       | 387 MB
bigdata_app_log_2007  | 1,063,139       | 367 MB
bigdata_app_log_2008  | 972,165         | 337 MB
bigdata_app_log_2009  | 939,173         | 326 MB
bigdata_app_log_2010  | 1,036,272       | 360 MB


Table                 | Tuples Inserted | Table Size
----------------------|-----------------|------------
bigdata_app_log_2011  | 1,081,212       | 376 MB
bigdata_app_log_2012  | 1,172,931       | 409 MB
bigdata_app_log_2013  | 1,199,508       | 419 MB
bigdata_app_log_2014  | 1,207,115       | 423 MB
bigdata_app_log_2015  | 1,199,303       | 420 MB



## パーティション テーブル測定




## Execution time: 3,001 ms

<img style="border-radius: 3% !important;" src="img/010_Index_Partition.png" height="230px"/>

```bash
QUERY PLAN
Append  (cost=0.00..215639.89 rows=3605927 width=4995) (actual time=0.011..2957.890 rows=3605926 loops=1)
  ->  Seq Scan on bigdata_app_log  (cost=0.00..0.00 rows=1 width=4994) (actual time=0.001..0.001 rows=0 loops=1)
        Filter: ((reg_dt >= '2013-04-01'::date) AND (reg_dt <= '2016-03-31'::date))
  ->  Seq Scan on bigdata_app_log_2015  (cost=0.00..71719.54 rows=1199303 width=4995) (actual time=0.009..868.766 rows=1199303 loops=1)
        Filter: ((reg_dt >= '2013-04-01'::date) AND (reg_dt <= '2016-03-31'::date))
  ->  Seq Scan on bigdata_app_log_2014  (cost=0.00..72308.73 rows=1207115 width=4996) (actual time=2.492..973.347 rows=1207115 loops=1)
        Filter: ((reg_dt >= '2013-04-01'::date) AND (reg_dt <= '2016-03-31'::date))
  ->  Seq Scan on bigdata_app_log_2013  (cost=0.00..71611.62 rows=1199508 width=4994) (actual time=0.186..1042.658 rows=1199508 loops=1)
        Filter: ((reg_dt >= '2013-04-01'::date) AND (reg_dt <= '2016-03-31'::date))
Planning time: 7.191 ms
Execution time: 3001.293 ms
```



Table                          | Tuples Inserted | Table Size [MB] | Year                    | Execution time [ms]
-------------------------------|-----------------|-----------------|-------------------------|---------------
bigdata\_app\_log_<br>no_index | 12,171,665      | 4,231           | 2013-2016/<br>2004-2016 | 11,546.416
bigdata\_app\_log_<br>index    | 12,171,665      | 4,231           | 2013-2016/<br>2004-2016 | 6,269.275
bigdata\_app\_log              | 0               | 0               | -                       | <span style="color: yellow">**3,001.293**</span>
bigdata\_app\_log_2015         | 1,199,303       | 420             | 2015/2015               | -
bigdata\_app\_log_2014         | 1,207,115       | 423             | 2014/2014               | -
bigdata\_app\_log_2013         | 1,199,508       | 419             | 2013/2013               | -



## まとめ

* RDB における大量データの高速化手法がわかった <!-- .element: class="fragment" data-fragment-index="1" -->
* パーティショニングの概要がわかった <!-- .element: class="fragment" data-fragment-index="1" -->
* キー値から自動でパーティション テーブルを作成できた <!-- .element: class="fragment" data-fragment-index="1" -->
* パーティショニングで素朴な SQL でも約 4 倍高速化できた <!-- .element: class="fragment" data-fragment-index="1" -->
* まったく Java と関係がなかった <!-- .element: class="fragment" data-fragment-index="5" -->



## 参考ノート

1. fluentd, http://docs.fluentd.org/articles/quickstart
1. PostgreSQL 9.5.1文書, 5.10. パーティショニング, https://www.postgresql.jp/document/9.5/html/ddl-partitioning.html
1. ZABBIX.org, Higher performant partitioning in PostgreSQL, https://www.zabbix.org/wiki/Higher_performant_partitioning_in_PostgreSQL

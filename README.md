# P2HACKS2022 アピールシート

**プロダクト名**

EinStealth. (EinsteinとStealthから)

**コンセプト**

未来へ行くことが攻略の鍵！　相対性理論×かくれんぼ　


**対象ユーザ**

全年齢対象
その中でもかくれんぼしたい人、体を動かしたい人、頭脳戦がしたい人


**利用の流れ**

1.鬼と隠れる人間を決める

2.かくれんぼ開始

3.鬼サイド→勝利条件：制限時間内に全員を確保、敗北条件：制限時間内に一人でも捕まえてない人がいれば負け

  隠れる人間サイド→勝利条件：制限時間内に一度も鬼に発見されない。敗北条件：制限時間内に鬼に発見されてしまう。

4.鬼が全員捕まえた or 隠れる側の人間が一人でも勝ち抜けた時ゲーム終了

**特殊効果**

妨害スキル→隠れる人間が、過去にいる鬼に対して罠を設置することが出来る。60秒間画面がブラックアウト。



**推しポイント**

1. 絶妙なゲームバランス。
    両陣営共に過去に行っても未来に行ってもメリット・デメリットがあり、物理空間と時間の概念を上手く活かすことが出来ている
2. 未来を疑似体験できるアプリでありながら、今までにない未来的なかくれんぼであり、ダブルミーニングになっている

**スクリーンショット**



**EinStealth紹介用webサイト**


![Untitled](https://user-images.githubusercontent.com/64563041/208280429-19a77467-f067-44f8-b134-aa3ccc6556c4.png)
![Untitled (1)](https://user-images.githubusercontent.com/64563041/208280433-d759a7e3-cc8e-4a4b-a153-1e077862a74a.png)
![Untitled (2)](https://user-images.githubusercontent.com/64563041/208280434-276d7900-6fa5-4ddb-9adc-79f241091ca4.png)
![Untitled (3)](https://user-images.githubusercontent.com/64563041/208280435-47572430-1300-48cd-a4d7-7273046d86c8.png)
![Untitled (4)](https://user-images.githubusercontent.com/64563041/208280437-5b1ff057-f58e-4f3a-9f87-12adb141bc8e.png)



## 開発体制

**役割分担**

モバイル：出口、野村

Web：前田、野村

バックエンド：藤井

デザイン：前田

**開発における工夫した点**

- 継続的デリバリの導入によって、デプロイ関連の負荷を軽減した。
- multi-staged buildを行いDocker Imageを軽量化することで、ビルド時間の軽減、レジストリへのpush/pull時間の削減、セキュリティの向上につながった。
    
    [10 Docker Security Best Practices | Docker Best Practices](https://snyk.io/blog/10-docker-image-security-best-practices/)
    
- Issueドリブン開発を行ったことで、メンバー間での進捗の共有が容易になり快適に開発を行うことができた。
    - [https://gist.github.com/Enchan1207/0ea2c7a7d6a3c16aea5683435d1972f8](https://gist.github.com/Enchan1207/0ea2c7a7d6a3c16aea5683435d1972f8)
- 以上に加え、積極的にメンバーの進捗をヒアリングしたり、自分から発信するようにした。
- 積極的に会話のメモを残すように徹底し、認識の齟齬がなくなるように努めた。
- Notionで期日やドキュメントなどをまとめ管理した。
![Untitled (5)](https://user-images.githubusercontent.com/64563041/208280460-617568fd-6f3a-4015-b944-4ad4c3221822.png)
![Untitled (6)](https://user-images.githubusercontent.com/64563041/208280462-4a07ee47-5d37-45ca-8e39-d6dc4971ccdd.png)


## 開発技術

**利用したプログラミング言語**

モバイル：Kotlin

Web（紹介用サイト）：JS

バックエンド：Go

インフラ: GCP, Kubernetes, Docker, Argo CD

**利用したフレームワーク・ライブラリ**
モバイル：Google Play Service, Room, Coroutine, Lifecycle component

Web（紹介用サイト）：Vue.js, Liquid JS (forkしたものを使用)

バックエンド：Gin, Swaggo


**その他開発に使用したツール・サービス**

Figma, Android Studio, Notion, After Effects, GitHub,  VScode, GitHub Actions, Vim

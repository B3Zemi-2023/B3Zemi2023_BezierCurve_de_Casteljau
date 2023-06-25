# B3Zemi2023_BezierCurve_de_Casteljau

1. プロジェクトのクローン
2. branchの作成からpushまで
3. プログラムの実装

## 1. プロジェクトのクローン
右上に"Code"と書かれている緑色のボタンがあるのでここをクリックして、SSHを選択し、コピーします. プロジェクトを置きたい任意のディレクトリにGitBushで移動して、以下のコマンドを実行します。
```
git clone {コピーしたURL}
# 今回の場合は git clone git@github.com:B3Zemi-2023/B3Zemi2023_BezierCurve_de_Casteljau.git
```

## 2. branchの作成からpushまで
クローンした、B3Zemi2023_BezierCurve_de_Casteljauのファイルの中で、新しくブランチを作りプッシュしましょう.

今後の作業は各々のブランチで行ってください.
```
1. git branch 名前 : 自分の名前のブランチの作成.
2. git checkout 名前 : 新しく作成したブランチに移動.
3. 新しく作成したブランチに、メモ帳などで作った新しいファイルを追加する.
4. git add . : 変更をaddする.
5. git commit -m "コメント" : 変更をcommitする.
6. git push origin 名前 : 変更をpushする
7. Github上に自分のブランチがあり、その中に追加したファイルがあればOK.
```

## 3. プログラムの実装
BezierCurve.java内のinternal()とevaluate()に処理を書き込んでください。追加する処理に関してはwikiの資料を参照してください。追加する箇所にはコメントでヒントを書き込んであります。

どうしても分からない場合は一番下にテンプレートのようなものを用意したので利用してください。ただし、利用する場合はできるだけプログラムを理解するよう努めてください。
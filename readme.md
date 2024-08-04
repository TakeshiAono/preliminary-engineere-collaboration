# README の目的

初めてプロジェクトにアサインされた人が迅速に開発環境構築ができること

# リポジトリのクローン

以下のコマンドをターミナルで実行

- git clone origin git@github.com:gaia0683/preliminary-engineere-collaboration.git

# docker の準備

## 1. docker Hub のアカウントを作成する

- `https://hub.docker.com/`でアカウントを作成してください。
  - このアカウントがないと docker image をダウンロード出来ず、コンテナを起動できない。

## 2. docker desktop のインストール

- `https://www.docker.com/products/docker-desktop/` から docker desktop アプリをインストールしてください。
- インストール後 docker desktop を立ち上げた状態で以下の手順を実行してください。

# コンテナの作成

- docker-compose.yml と同じ階層に cd で移動して、docker-compose up を実行
- db コンテナ,api コンテナが作成完了できる。(docker desktop にもコンテナが表示されているはず)

# コンテナへのログイン

- docker ps コマンドでコンテナの名前を確認する。
- docker exec -it `コンテナ名` bash コマンドを実行
  > https://zenn.dev/sickleaf/articles/99884a12b0489cf21d45#docker-ps%E3%81%AE%E3%82%B5%E3%83%B3%E3%83%97%E3%83%AB の names カラムのところに書いてあるのがコンテナ名

# Web サーバーの立ち上げ方

    1. apiサーバーコンテナへログインする
    2. /app ディレクトリ上で ./gradlew bootRunコマンドを実行
    3. 手順2とは別のshellを立ち上げ、/app ディレクトリ上で ./gradlew build --continuousコマンドを実行(コード変更を検知して自動ビルドをしてくれる)
    3. ブラウザでlocalhost:8080に入れればOK(2023/10/28時点ではエラーページが出力されればOK)

# 推奨の拡張機能(vscode)

- Spring Boot Tools
  https://marketplace.visualstudio.com/items?itemName=vmware.vscode-spring-boot

  これでインテリセンスが効くようになる。

# テスト実行

コンテナに入ってテストを実行

```
  docker exec -it <コンテナ名> ./gradlew test
```

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

# Web サーバーの立ち上げ方

### 1. api サーバーコンテナに入る

```bash
docker exec -it <コンテナ名> bash
```

- docker ps コマンドでコンテナの名前を確認する。
- docker exec -it `コンテナ名` bash コマンドを実行
  > https://zenn.dev/sickleaf/articles/99884a12b0489cf21d45#docker-ps%E3%81%AE%E3%82%B5%E3%83%B3%E3%83%97%E3%83%AB の names カラムのところに書いてあるのがコンテナ名

### 2. /app ディレクトリ上で 以下のコマンドを実行

```bash
./gradlew bootRun &
./gradlew build --continuous
```

# 推奨の拡張機能(vscode)

- Spring Boot Tools
  https://marketplace.visualstudio.com/items?itemName=vmware.vscode-spring-boot

  これでインテリセンスが効くようになる。

# テスト実行

コンテナに入ってテストを実行

```
  docker exec -it <コンテナ名> ./gradlew test
```

# 本番環境のインスタンスの構築方法
以下は最初の立ち上げ時に作業するので、新コードのアップデート時は必要ない
1. 開発環境にすでにec2インスタンスがcloudFormationテンプレートでビルドされているため、開発環境のtemplate.yamlをコピー。
2. 本番環境でinfrastructure composerにてテンプレートを元に作成できるため、開発環境でコピーしたコードを貼り付けてインスタンス類を作成する。(2025/01/13 デプロイに使用したテンプレートファイル: template-1736696199282.yaml (S3に保存されている))
3. Route53でドメイン取得
4. ACMで証明書取得(ドメインと紐付け)
5. Route53で作成したドメインのトラフィックのルーティング先をcloudFormationで作成したELBに紐づける
6. ELBのターゲットグループの登録済みターゲットにcloudFormationで作成したインスタンスを登録する

# 本番環境へコードデプロイの方法
1. AWSコンソール上でEC2インスタンスにssh接続する。
2. フロントエンド、バックエンドともにgitからpullする。
3. フロントエンドは自動で更新されるが、バックエンドは手動でビルドし直す。

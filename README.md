# 日記管理API (Diary Management API)

Spring Boot を使用した日記管理アプリケーションのREST APIです。

## 📋 概要

ユーザーが日記を作成、閲覧、編集、削除できるWebアプリケーションのバックエンドAPIです。

### 主な機能
- ✅ JWT認証によるユーザー管理
- ✅ 日記のCRUD操作
- ✅ タグ機能
- ✅ 気分・天気の記録
- ✅ 統計情報の取得
- ✅ ページネーション対応
- ✅ フィルタリング機能

## 🛠️ 技術スタック

- **Java**: OpenJDK 17
- **Framework**: Spring Boot 3.3.0
- **Security**: Spring Security 6.x + JWT
- **Database**: H2 (開発用) / PostgreSQL (本番用)
- **ORM**: Spring Data JPA + Hibernate
- **Migration**: Flyway
- **Build Tool**: Maven 3.9.x
- **Documentation**: OpenAPI 3.0 (Swagger)

## 🚀 クイックスタート

### 1. 前提条件

- Java 17以上
- Maven 3.6以上

### 2. アプリケーション起動

```bash
# リポジトリをクローン
git clone <repository-url>
cd java-diary-api

# アプリケーション起動
./mvnw spring-boot:run
```

起動が成功すると以下のメッセージが表示されます：
```
Tomcat started on port 8080 (http) with context path '/api'
Started DiaryApiApplication in 14.154 seconds
```

### 3. 基本的な動作確認

```bash
# ヘルスチェック
curl -X GET http://localhost:8080/api/health

# レスポンス例
{
  "success": true,
  "message": "Health check",
  "data": {
    "status": "UP",
    "timestamp": "2025-07-03T23:07:49",
    "application": "diary-api"
  }
}
```

## 📖 API使用方法

### 認証

#### ユーザー登録
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "myuser",
    "email": "myuser@example.com",
    "password": "password123",
    "displayName": "My User"
  }'
```

#### ログイン
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "myuser",
    "password": "password123"
  }'
```

ログインが成功すると、JWTトークンが返されます：
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "user": {
      "id": 1,
      "username": "myuser",
      "displayName": "My User"
    }
  }
}
```

### 日記操作

認証が必要なAPIには、ヘッダーにJWTトークンを含めてください：

```bash
# トークン取得
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "password123"}' \
  | jq -r '.data.token')
```

#### 日記一覧取得
```bash
curl -X GET http://localhost:8080/api/diaries \
  -H "Authorization: Bearer $TOKEN"
```

#### 日記作成
```bash
curl -X POST http://localhost:8080/api/diaries \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "今日の出来事",
    "content": "今日はAPIの実装が完了しました！",
    "date": "2025-07-03",
    "mood": "HAPPY",
    "weather": "SUNNY",
    "tags": ["work", "programming"],
    "isPublic": false
  }'
```

#### 特定の日記取得
```bash
curl -X GET http://localhost:8080/api/diaries/1 \
  -H "Authorization: Bearer $TOKEN"
```

#### 日記更新
```bash
curl -X PUT http://localhost:8080/api/diaries/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "更新されたタイトル",
    "content": "更新された内容です。",
    "date": "2025-07-03",
    "mood": "EXCITED",
    "weather": "CLOUDY",
    "tags": ["updated"],
    "isPublic": true
  }'
```

#### 日記削除
```bash
curl -X DELETE http://localhost:8080/api/diaries/1 \
  -H "Authorization: Bearer $TOKEN"
```

## 🎯 APIエンドポイント一覧

### 認証
- `POST /api/auth/register` - ユーザー登録
- `POST /api/auth/login` - ログイン

### ユーザー管理
- `GET /api/users/profile` - プロフィール取得
- `PUT /api/users/profile` - プロフィール更新

### 日記管理
- `GET /api/diaries` - 日記一覧取得（ページネーション対応）
- `GET /api/diaries/{id}` - 特定日記取得
- `POST /api/diaries` - 日記作成
- `PUT /api/diaries/{id}` - 日記更新
- `DELETE /api/diaries/{id}` - 日記削除

### タグ管理
- `GET /api/tags` - タグ一覧取得
- `POST /api/tags` - タグ作成
- `DELETE /api/tags/{id}` - タグ削除

### 統計
- `GET /api/statistics/summary` - 統計情報取得

### その他
- `GET /api/health` - ヘルスチェック
- `GET /api/actuator/*` - Spring Boot Actuator

## 🔍 フィルタリング・検索

日記一覧取得時に以下のクエリパラメータが使用できます：

```bash
# ページネーション
curl -X GET "http://localhost:8080/api/diaries?page=0&size=5" \
  -H "Authorization: Bearer $TOKEN"

# 日付フィルター
curl -X GET "http://localhost:8080/api/diaries?date=2025-07-03" \
  -H "Authorization: Bearer $TOKEN"

# 気分フィルター
curl -X GET "http://localhost:8080/api/diaries?mood=HAPPY" \
  -H "Authorization: Bearer $TOKEN"

# タグフィルター
curl -X GET "http://localhost:8080/api/diaries?tag=work" \
  -H "Authorization: Bearer $TOKEN"
```

## 🗄️ データベース管理

### H2コンソール（開発環境）

ブラウザで以下にアクセス：
```
http://localhost:8080/api/h2-console
```

接続情報：
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User Name**: `sa`
- **Password**: （空）

### サンプルデータ

アプリケーション起動時に以下のサンプルデータが投入されます：

**サンプルユーザー**:
- Username: `testuser` / Password: `password123`
- Username: `johndoe` / Password: `password123`

**サンプル日記**: 5件の日記エントリー
**サンプルタグ**: work, personal, travel, health, hobby, family, exercise

## 🔧 設定

### 環境別設定

#### 開発環境（デフォルト）
- データベース: H2 インメモリDB
- ログレベル: DEBUG
- H2コンソール: 有効

#### 本番環境
```bash
# 本番プロファイルで起動
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod

# または環境変数で設定
export SPRING_PROFILES_ACTIVE=prod
./mvnw spring-boot:run
```

### ポート変更
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### JWT設定

環境変数で設定可能：
```bash
export JWT_SECRET=your-secret-key
export JWT_EXPIRATION=86400000
```

## 🧪 テスト

```bash
# 単体テスト実行
./mvnw test

# 統合テスト実行
./mvnw integration-test

# 全テスト実行
./mvnw verify
```

## 📋 データ型・制約

### 気分（Mood）
- `HAPPY` - 幸せ
- `SAD` - 悲しい
- `NORMAL` - 普通
- `EXCITED` - 興奮
- `TIRED` - 疲れた

### 天気（Weather）
- `SUNNY` - 晴れ
- `CLOUDY` - 曇り
- `RAINY` - 雨
- `SNOWY` - 雪

### バリデーション
- ユーザー名: 3-50文字
- パスワード: 8文字以上
- タイトル: 200文字以下
- タグ名: 1-50文字

## 🚨 トラブルシューティング

### よくある問題

1. **ポート8080が使用中**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
   ```

2. **JWT認証エラー**
   - トークンの有効期限（24時間）を確認
   - `Authorization: Bearer <token>` ヘッダーの形式を確認

3. **データベース接続エラー**
   - H2コンソールでデータベース状態を確認
   - アプリケーション再起動

4. **デバッグモード**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.arguments=--logging.level.com.diary.api=DEBUG
   ```

## 📚 開発情報

### ビルド
```bash
# コンパイル
./mvnw compile

# パッケージ作成
./mvnw package

# 依存関係更新
./mvnw dependency:resolve
```

### コード品質
```bash
# コードフォーマット確認
./mvnw spotless:check

# コードフォーマット適用
./mvnw spotless:apply
```

## 📄 ライセンス

MIT License

## 🤝 貢献

1. このリポジトリをフォーク
2. フィーチャーブランチを作成 (`git checkout -b feature/amazing-feature`)
3. 変更をコミット (`git commit -m 'Add amazing feature'`)
4. ブランチをプッシュ (`git push origin feature/amazing-feature`)
5. プルリクエストを作成

## 📞 サポート

問題や質問がある場合は、Issueを作成してください。
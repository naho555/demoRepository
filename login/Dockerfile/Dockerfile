# ベースイメージとして Maven を使用
FROM maven:3.8.3-openjdk-17-slim AS build

# 作業ディレクトリを設定
WORKDIR /app

# プロジェクトの全ファイルをコンテナ内にコピー
COPY . .

# Maven を使用してプロジェクトをビルド（テストはスキップ）
RUN mvn clean package -DskipTests

# 実行環境として OpenJDK を使用
FROM openjdk:17-jdk-slim

# ビルドされた JAR ファイルをコピー
COPY --from=build /app/target/*.jar /app/app.jar

# アプリケーションがリッスンするポートを指定
EXPOSE 10000

# アプリケーションを起動
CMD ["java", "-jar", "/app/app.jar"]

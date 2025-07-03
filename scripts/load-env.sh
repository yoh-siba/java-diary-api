#!/bin/bash

# .envファイルから環境変数を読み込むスクリプト

# .envファイルが存在するかチェック
if [ -f .env ]; then
    echo "Loading environment variables from .env file..."
    
    # .envファイルを読み込み、環境変数として設定
    export $(grep -v '^#' .env | xargs)
    
    echo "Environment variables loaded successfully!"
    echo "You can now run: ./mvnw spring-boot:run"
else
    echo "Warning: .env file not found!"
    echo "Please copy .env.example to .env and configure your settings:"
    echo "  cp .env.example .env"
    echo "  # Edit .env file with your configuration"
fi

# 設定された主要な環境変数を表示
echo ""
echo "Current configuration:"
echo "  APP_NAME: ${APP_NAME:-diary-api}"
echo "  SERVER_PORT: ${SERVER_PORT:-8080}"
echo "  SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE:-dev}"
echo "  JWT_SECRET: ${JWT_SECRET:-(using default)}"
echo ""
@echo off

REM .envファイルから環境変数を読み込むWindows用スクリプト

if exist .env (
    echo Loading environment variables from .env file...
    
    REM .envファイルを読み込み
    for /f "usebackq delims=" %%a in (".env") do (
        set "line=%%a"
        if not "!line!"=="" (
            if not "!line:~0,1!"=="#" (
                set "%%a"
            )
        )
    )
    
    echo Environment variables loaded successfully!
    echo You can now run: mvnw.cmd spring-boot:run
) else (
    echo Warning: .env file not found!
    echo Please copy .env.example to .env and configure your settings:
    echo   copy .env.example .env
    echo   REM Edit .env file with your configuration
)

echo.
echo Current configuration:
echo   APP_NAME: %APP_NAME%
echo   SERVER_PORT: %SERVER_PORT%
echo   SPRING_PROFILES_ACTIVE: %SPRING_PROFILES_ACTIVE%
echo.
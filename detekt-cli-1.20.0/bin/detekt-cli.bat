@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  detekt-cli startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and DETEKT_CLI_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\detekt-cli-1.20.0.jar;%APP_HOME%\lib\jcommander-1.82.jar;%APP_HOME%\lib\detekt-core-1.20.0.jar;%APP_HOME%\lib\detekt-rules-1.20.0.jar;%APP_HOME%\lib\detekt-rules-errorprone-1.20.0.jar;%APP_HOME%\lib\detekt-tooling-1.20.0.jar;%APP_HOME%\lib\detekt-parser-1.20.0.jar;%APP_HOME%\lib\detekt-metrics-1.20.0.jar;%APP_HOME%\lib\detekt-report-txt-1.20.0.jar;%APP_HOME%\lib\detekt-report-xml-1.20.0.jar;%APP_HOME%\lib\detekt-api-1.20.0.jar;%APP_HOME%\lib\detekt-psi-utils-1.20.0.jar;%APP_HOME%\lib\kotlin-compiler-embeddable-1.6.20.jar;%APP_HOME%\lib\snakeyaml-1.30.jar;%APP_HOME%\lib\detekt-report-html-1.20.0.jar;%APP_HOME%\lib\detekt-report-sarif-1.20.0.jar;%APP_HOME%\lib\detekt-utils-1.20.0.jar;%APP_HOME%\lib\detekt-rules-complexity-1.20.0.jar;%APP_HOME%\lib\detekt-rules-coroutines-1.20.0.jar;%APP_HOME%\lib\detekt-rules-documentation-1.20.0.jar;%APP_HOME%\lib\detekt-rules-empty-1.20.0.jar;%APP_HOME%\lib\detekt-rules-exceptions-1.20.0.jar;%APP_HOME%\lib\detekt-rules-naming-1.20.0.jar;%APP_HOME%\lib\detekt-rules-performance-1.20.0.jar;%APP_HOME%\lib\detekt-rules-style-1.20.0.jar;%APP_HOME%\lib\kotlin-reflect-1.6.20.jar;%APP_HOME%\lib\sarif4k-0.0.1.jar;%APP_HOME%\lib\kotlinx-serialization-json-jvm-1.1.0.jar;%APP_HOME%\lib\kotlinx-serialization-core-jvm-1.1.0.jar;%APP_HOME%\lib\kotlin-stdlib-1.6.20.jar;%APP_HOME%\lib\kotlin-script-runtime-1.6.20.jar;%APP_HOME%\lib\kotlin-daemon-embeddable-1.6.20.jar;%APP_HOME%\lib\trove4j-1.0.20200330.jar;%APP_HOME%\lib\jna-5.6.0.jar;%APP_HOME%\lib\kotlinx-html-jvm-0.7.5.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.6.20.jar;%APP_HOME%\lib\annotations-13.0.jar


@rem Execute detekt-cli
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %DETEKT_CLI_OPTS%  -classpath "%CLASSPATH%" io.gitlab.arturbosch.detekt.cli.Main %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable DETEKT_CLI_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%DETEKT_CLI_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

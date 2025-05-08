#!/usr/bin/env bash

is_debug="$1"

mvn clean package && \
_JAVA_OPTIONS="$([[ "$is_debug" == "y" ]] && echo '-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=0.0.0.0:8000')" docker compose up -d --build --remove-orphans
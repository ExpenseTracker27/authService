FROM ubuntu:latest
LABEL authors="tanishq"

ENTRYPOINT ["top", "-b"]
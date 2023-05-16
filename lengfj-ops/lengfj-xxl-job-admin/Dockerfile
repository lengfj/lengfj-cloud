FROM openjdk:8u252-jdk
ENV JHIPSTER_SLEEP=0 \
    JAVA_OPTS="" \
    LOGFILE="" \
    ACTIVE=""
EXPOSE 5003
WORKDIR /data/app

ADD entrypoint.sh entrypoint.sh

RUN mkdir -p /data/logs/lengfj-xxl-job-admin

RUN chmod 755 entrypoint.sh

ADD ./target/lengfj-xxl-job-admin.jar ./xxl-job-admin.jar

ENTRYPOINT ["./entrypoint.sh"]
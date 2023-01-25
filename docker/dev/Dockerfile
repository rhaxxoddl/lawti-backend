FROM amazoncorretto:17-alpine3.14-full
ARG ARG_PORT
ARG ARG_PROFILE
ENV PORT ${ARG_PORT}
ENV PROFILE ${ARG_PROFILE}
COPY project.jar /project.jar
EXPOSE ${ARG_PORT}
CMD [ "sh", "-c", "java -jar -Dserver.port=${PORT} -Dspring.profiles.active=${PROFILE} /project.jar" ]
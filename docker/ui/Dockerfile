## Build the app
FROM node as build
WORKDIR /app/ui
COPY src/ui/ ./
RUN npm install
RUN npm install -g @angular/cli
RUN ng build --configuration production

## Webserver config
FROM nginx
COPY --from=build /app/ui/dist/movie-manager-ui /usr/share/nginx/html
COPY docker/ui/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]
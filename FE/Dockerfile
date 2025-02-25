FROM node:23 AS builder

WORKDIR /app

# package.json이랑 package-lock.json 복사해서 의존성 설치(필요한거 가져옴)
COPY package.json package-lock.json ./
RUN npm install

# 소스 복사
COPY . .
RUN npm run build

# nginx 통해서 정적 파일
FROM nginx:latest
COPY --from=builder /app/dist /usr/share/nginx/html

COPY nginx/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
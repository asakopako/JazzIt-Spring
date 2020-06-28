#!/usr/bin/env bash

yum -y update
yum -y install epel-release git nmap mc java-1.8.0-openjdk java-1.8.0-openjdk-devel
curl -fsSL https://get.docker.com | sh
systemctl start docker
systemctl enable docker

docker network create jazzit
docker run --name jazzitdb -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=fK6wwAs2kjh82Dat -e MYSQL_DATABASE=jazzitdb --restart always --network jazzit -v /root/jazzit/mysql:/var/lib/mysql mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
docker run --name jazzitadmin --network jazzit -d -p 6060:80 --restart always -e PMA_HOST=jazzitdb phpmyadmin/phpmyadmin


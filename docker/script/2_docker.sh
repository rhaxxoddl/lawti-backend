#!/bin/bash

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository "deb [arch=arm64] https://download.docker.com/linux/ubuntu $(lsb_release -cs)  stable"

sudo apt-get update

sudo apt-get install -y docker.io

sudo chmod 666 /var/run/docker.sock

sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

sudo chmod +x /usr/local/bin/docker-compose

sudo su -c 'echo 1 > /proc/sys/net/ipv4/ip_forward'

sudo su -c 'echo DOCKER_OPTS=\"--iptables=false\" > /etc/default/docker'

sudo su -c 'printf "{\n\t\"live-restore\": true\n}" > /etc/docker/docker.json'

sudo systemctl restart docker
